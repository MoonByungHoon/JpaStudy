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

    //then
    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }
}
