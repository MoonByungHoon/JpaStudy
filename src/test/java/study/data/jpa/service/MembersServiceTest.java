package study.data.jpa.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.data.jpa.domain.Members;
import study.data.jpa.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MembersServiceTest {

  MembersService membersService;
  MemoryMemberRepository memberRepository;

  @BeforeEach
  public void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    membersService = new MembersService(memberRepository);
  }

  @AfterEach
  public void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  void 회원가입() {
    //given
    Members members = new Members();
    members.setName("hello");

    //when
    Long saveId = membersService.join(members);

    //then
    Members findMember = membersService.findOne(saveId).get();
    assertThat(members.getName()).isEqualTo(findMember.getName());
  }

  @Test
  public void 중복_회원_예외() {
    //given
    Members members1 = new Members();
    members1.setName("spring");
    Members members2 = new Members();
    members2.setName("spring");

    //when
    membersService.join(members1);

    System.out.println("가져와 : " + memberRepository.findByName(members1.getName()));

    IllegalStateException e = assertThrows(IllegalStateException.class, () -> membersService.join(members2));

    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//    try {
//      membersService.join(members2);
//
//      fail("에러가 발생해야 합니다.");
//    } catch (IllegalStateException e) {
//      assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//    }
    //then
  }

  @Test
  void findMembers() {

  }

  @Test
  void findOne() {
  }

  @Test
  public void callTest() {
    class callcall {

      public String onecall(int a, String twocall) {
        System.out.println("4");
        return "1";
      }

      public String twocall() {
        System.out.println("3");
        return "2";
      }

      public void allcall() {
        System.out.println(onecall(11, twocall()));
      }
    }

    callcall callcall = new callcall();

    callcall.allcall();
  }
}