package study.data.jpa.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import study.data.jpa.domain.Members;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

  MemoryMemberRepository memberRepository = new MemoryMemberRepository();

  @AfterEach
  public void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  public void save() {
    Members members = new Members();
    members.setName("spring");

    memberRepository.save(members);

    Members result = memberRepository.findById(members.getId()).get();

    assertThat(members).isEqualTo(result);
  }

  @Test
  public void findByName() {
    Members members = new Members();
    members.setName("spring1");
    memberRepository.save(members);

    Members members1 = new Members();
    members1.setName("spring2");
    memberRepository.save(members1);

    Members result = memberRepository.findByName("spring1").get();

    assertThat(result).isEqualTo(members);
  }

  @Test
  public void findAll() {
    Members members = new Members();
    members.setName("spring1");
    memberRepository.save(members);

    Members members1 = new Members();
    members1.setName("spring2");
    memberRepository.save(members1);

    List<Members> all = memberRepository.findAll();

    assertThat(all.size()).isEqualTo(2);
  }
}
