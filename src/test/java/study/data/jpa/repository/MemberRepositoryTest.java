package study.data.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import study.data.jpa.dto.MemberDto;
import study.data.jpa.entity.Member;
import study.data.jpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  TeamRepository teamRepository;

  //  같은 트렌젝션이면 같은 em이 불러와진다.
  @PersistenceContext
  EntityManager em;

  @Test
  public void testMember() {

    Member member = new Member("mamberA");
    Member saveMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(member.getId()).get();

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void basicCRUD() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");

    memberRepository.save(member1);
    memberRepository.save(member2);

    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();

    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);

    List<Member> all = memberRepository.findAll();

    assertThat(all.size()).isEqualTo(2);

    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);

    memberRepository.delete(member1);
    memberRepository.delete(member2);

    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThen() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("aaa", 20);

    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15);

    assertThat(result.get(0).getUsername()).isEqualTo("aaa");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  public void testNamedQuery() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("aaa", 20);

    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findByUsername("aaa");
    Member findMember = result.get(0);
    assertThat(findMember).isEqualTo(m1);
  }


  @Test
  public void testQuery() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("aaa", 20);

    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findUser("aaa", 10);
    assertThat(result.get(0)).isEqualTo(m1);
  }

  @Test
  public void findUsernameList() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("bbb", 20);

    memberRepository.save(m1);
    memberRepository.save(m2);

    List<String> result = memberRepository.findUsernameList();
    for (String s : result) {
      System.out.println("s = " + s);

    }
  }

  @Test
  public void findMemberDto() {
    Team team = new Team("teamA");
    teamRepository.save(team);

    Member m1 = new Member("aaa", 10);
    m1.setTeam(team);
    memberRepository.save(m1);

    List<MemberDto> memberDto = memberRepository.findMemberDto();
    for (MemberDto dto : memberDto) {
      System.out.println("dto = " + dto);
    }
  }

  @Test
  public void findByNames() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("bbb", 20);

    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findByNames(Arrays.asList("aaa", "bbb", "ccc"));
    for (Member member : result) {
      System.out.println("member = " + member);
    }
  }

  @Test
  public void returnType() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("bbb", 20);

    memberRepository.save(m1);
    memberRepository.save(m2);

    //기본적으로 null에 대해서 보장을 해줘서 null 익셉션이 터지지 않음.
    List<Member> aaa = memberRepository.findListByUsername("aaa");

    //noResult익셉션이 발생할 수 있으나 jpa는 null로 반환해줌.
    Member aaa1 = memberRepository.findMemberByUsername("aaa");

    //널에 대해서 클라이언트에게 처리를 떠넘겨서 익셉션이 터지지 않음.
    //단 결과 값이 2개 이상이 될 경우에 익셉션이 터짐.
    Optional<Member> opMember = memberRepository.findOptionalByUsername("asdfasfd");
  }

  @Test
  public void paging() {
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));
    memberRepository.save(new Member("member6", 10));

    int age = 10;
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

//    Page는 기본적으로 count쿼리도 같이 날려주지만 로직이 복잡해지면
//    해당 쿼리에 구성된 조인까지 전부 참여하게 되어서 성능 이슈가 발생한다.
//    그러한 이유로 인해서 쿼리가 복잡해지면 별도로 countQuery를 만들어서 관리하는 것이 좋다.
    Page<Member> page = memberRepository.findByAge(age, pageRequest);

    //Page를 유지하면서 Entity를 DTO로 변환하는 방법이다. 실무에서도 사용하는 방법이다.
    Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

//    Slice는 count 쿼리를 자동으로 처리 해주지 않고 내부적으로 limit + 1 조회 해준다.
//    어플에서 보통 더보기 누르면 다음 리스트가 펼쳐지는 기능을 만들때에 사용한다.
//    Slice<Member> page = memberRepository.findByAge(age, pageRequest);

    List<Member> content = page.getContent();
    long totalElements = page.getTotalElements();

    for (Member member : content) {
      System.out.println("member = " + member);
    }
    System.out.println("totalElements = " + totalElements);

    assertThat(content.size()).isEqualTo(3);
    assertThat(page.getTotalElements()).isEqualTo(6);
    assertThat(page.getNumber()).isEqualTo(0);
    assertThat(page.getTotalPages()).isEqualTo(2);
    assertThat(page.isFirst()).isTrue();
    assertThat(page.hasNext()).isTrue();
  }

  @Test
  public void bulkUpdate() {
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 19));
    memberRepository.save(new Member("member3", 20));
    memberRepository.save(new Member("member4", 21));
    memberRepository.save(new Member("member5", 40));
    memberRepository.save(new Member("member6", 50));

    int resultCount = memberRepository.bulkAgePlus(20);

    em.flush();
    em.clear();

    List<Member> result = memberRepository.findByUsername("member5");

    Member member5 = result.get(0);

//    jpa에서는 entity와 db를 직접 관리해주는데 이로 인한 장점이 데이터의 변경이 있으면 이를 jpa가 감지하고
//    변경된 내용을 영속성 컨텍스트에서 감지하고 이를 사전에 수정하여 캐쉬로 가지고 있게 된다.
//    하지만 벌크성 수정 쿼리의 경우는 영속선 컨텍스트에 저장이 되지 않게 되며,
//    이로 인해서 아직 값이 적용되지 않은 상태의 db의 값을 가져오는 것이다.
//    jpa에서 데이터를 가져올때에는 순서가 있다.
//    1. 영속성 컨텍스트에 조회하고자 하는 entity가 있는가.
//    2. 없다면 DB에 있는가.
//    즉 영속성 컨텍스트에 감지 되지 않았으며, 또한 변경된 데이터가 아직 DB에 적용되지 않았기 때문에
//    기존에 가지고 있는 40살이 나오는 것이다.

//    이를 해결하기 위해서는 반드시 벌크성 수정 쿼리를 동작한 후에는 꼭 Entity Manager를 통해서
//    영속성 컨텍스트에 남아있는 데이터를 Flush해주고 Clear해주어야한다.
//    혹은 스프링 jpa에서 지원하는 repository에서 @Modifying(clearAutomatically = true)를 해주면 된다.
    System.out.println("member = " + member5);

    assertThat(resultCount).isEqualTo(4);
  }

  @Test
  public void findMemberLazy() {
    //given
    //member1 -> teamA
    //member2 -> teamB

    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");

    teamRepository.save(teamA);
    teamRepository.save(teamB);
    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 10, teamB);

    memberRepository.save(member1);
    memberRepository.save(member2);

    em.flush();
    em.clear();

    //when
    //select Member 1
    //N + 1 문제라고 부른다. 상당히 중요한 부분이다.
    List<Member> members = memberRepository.findAll();

//    아래와 같은 방법으로 Repository에서 오버라이딩 해서도 해결이 가능하다.
//    아래처럼 해결 할 경우는 jpql이 아닌 jpa로 해결하는 방법이다. EntityGraph이라고 한다.
//    @Override
//    @EntityGraph(attributePaths = {"team"})
//    List<Member> findAll();

//    아래와 같은 방법으로도 해결이 또한 가능하다. EntityGraph은 한마디로 Fetch 동작을 할 수 있게 한다고 보면된다.
//    @EntityGraph(attributePaths = {"team"})
//    @Query("select m from Member m")
//    List<Member> findMemberEntityGraph();

//    아래 해당 동작의 경우 MemberRepository에 가보게 되면 left join에 Fetch를 부여하게 된다.
//    가짜 프록시가 아닌 진짜 데이터까지 끌어오게 된다.
//    List<Member> members = memberRepository.findMemberFetchJoin();


    for (Member member : members) {
//      Member까지만 쿼리를 날린다.
      System.out.println("member.getUsername() = " + member.getUsername());
//      프록시 생성을 조회한다. 텅 빈 값이 들어가 있다.
      System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
//      이때에 Team에 대한 데이터가 필요해짐으로 Team에 대한 쿼리를 날리게 된다. 즉 일을 2번하게 된다.
      System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
    }


    //then
  }

  @Test
  public void queryHint() {
    //given
    Member member1 = new Member("member1", 10);
    memberRepository.save(member1);
    em.flush();
    em.clear();

    //when
//    Member findMember = memberRepository.findById(member1.getId()).get();
//    findMember.setUsername("member2");
    Member findMember = memberRepository.findReadOnlyByUsername("member1");
    findMember.setUsername("member2");

//    flush를 날리지 않아도 일단은 jpa는 변경을 감지 하기 때문에 변경해버리는 순간 이를 감지하고 해당 메소드를 벗어날 때에 쿼리를 날리게 된다.
//    em.flush();
  }

  @Test
  public void lock() {
    //given
    Member member1 = new Member("member1", 10);
    memberRepository.save(member1);
    em.flush();
    em.clear();

    //when
    List<Member> result = memberRepository.findLockByUsername("member1");
  }

  @Test
  public void callCustom() {
    List<Member> memberCustom = memberRepository.findMemberCustom();
  }
}
