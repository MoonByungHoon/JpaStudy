package study.data.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import study.data.jpa.domain.Members;
import study.data.jpa.repository.MembersRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class MembersService {

  private final MembersRepository membersRepository;

  @Autowired
  public MembersService(MembersRepository membersRepository) {
    this.membersRepository = membersRepository;
  }

  public Long join(Members members) {

    long start = System.currentTimeMillis();

    try {
      validateDuplicateMember(members);
      membersRepository.save(members);
      return members.getId();
    } finally {
      long finish = System.currentTimeMillis();
      long timeMs = finish - start;
      System.out.println("timeMs = " + timeMs + "ms");
    }
  }

  private void validateDuplicateMember(Members members) {
    membersRepository.findByName(members.getName()).ifPresent(members1 -> {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    });
  }

  public List<Members> findMembers() {
    return membersRepository.findAll();
  }

  public Optional<Members> findOne(Long memberId) {
    return membersRepository.findById(memberId);
  }

}
