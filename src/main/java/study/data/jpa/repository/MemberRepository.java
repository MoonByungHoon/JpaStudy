package study.data.jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data.jpa.dto.MemberDto;
import study.data.jpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  //  Entity에서 정의되어 있는 NamedQuery를 찾는 동작이다. 하지만 실무에서 사용하지 않는 편이다.
//  이유는 Entity내에서 쿼리를 관리하고 유지보수나 직관적인 측면에서 안좋기 때문이다.
  @Query(name = "Member.findByUsername")
  List<Member> findByUsername(@Param("username") String username);

  @Query("select m from Member m where m.username = :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select m.username from Member m")
  List<String> findUsernameList();

  //즉시 DTO로 변환해서 반환하는 방법
  @Query("select new study.data.jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();

  @Query("select m from Member m where m.username in :names")
  List<Member> findByNames(@Param("names") List<String> names);

  List<Member> findListByUsername(String username);

  Member findMemberByUsername(String username);

  Optional<Member> findOptionalByUsername(String username);

  //  Page는 자동으로 카운트 쿼리를 짜주기는 하지만
//    아래 처럼 쿼리를 짜면 Count 쿼리를 돌릴때에 left join까지 같이 하다보니 성능 이슈가 발생한다.
//  @Query(value = "select m from Member m left join m.team t")
//  쿼리가 복잡해지면 카운트 쿼리를 별도로 두어야 성능 이슈가 줄어든다.
//  @Query(value = "select m from Member m left join m.team t", countQuery = "select count (m.username) from Member m")
  @Query(value = "select m from Member m left join m.team t")
  Page<Member> findByAge(int age, Pageable pageable);

  //  변경하게 된다는 것을 jpa에 미리 알려주지 않으면 에러가 난다. 그래서 @Modifying을 꼭 넣어주어야한다.
  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.age = m.age +1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);

  @Query("select m from Member m left join fetch m.team")
  List<Member> findMemberFetchJoin();

  @Override
  @EntityGraph(attributePaths = {"team"})
  List<Member> findAll();

  @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
  Member findReadOnlyByUsername(String username);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<Member> findLockByUsername(String username);

//  @Override
//  @Query(value = "select m from Member m left join m.team t", countQuery = "select count (m) from Member m where m.age >= 50")
//  Page<Member> findAll(Pageable pageable);
}
