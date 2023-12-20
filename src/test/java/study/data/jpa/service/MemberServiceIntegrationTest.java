package study.data.jpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.data.jpa.domain.Members;
import study.data.jpa.repository.MembersRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@Transactional은 기존에 짯었던 afterEach의 기능을 제공한다. 즉 테스트 한건이 완료 될 때마다
//데이터를 롤백 시켜준다. 즉 db에 데이터가 남지 않아서 테스트를 원활하게 동작하도록 도와준다.
//Test에서 해당 어노테이션을 사용할때만 동작하는 기능이다.
@Transactional
public class MemberServiceIntegrationTest {

  @Autowired
  MembersService membersService;
  @Autowired
  MembersRepository memberRepository;

  @Test
  void 회원가입() {
    //given
    Members members = new Members();
    members.setName("spring");

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

    IllegalStateException e = assertThrows(IllegalStateException.class, () -> membersService.join(members2));

    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    //then
  }
}
