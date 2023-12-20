package study.data.jpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.data.jpa.dto.MemberDto;
import study.data.jpa.entity.Member;
import study.data.jpa.entity.Team;
import study.data.jpa.repository.MemberRepository;
import study.data.jpa.repository.TeamRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  private final TeamRepository teamRepository;

  @GetMapping("/members/{id}")
  public Member findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();

    return member;
  }

  //  엔티티 형태로 받았으나 조회 용으로 사용해야한다.
//  변경을 한다 하여도 엔티티 내에서 조회해서 가져 온 더미(?) 데이터로
//  변경을 하여도 EntityManager가 감지를 하지 못한다.
  @GetMapping("/members2/{username}")
  public String findMember(@PathVariable("username") String username) {

    return username;
  }

  //  @GetMapping("/members")
  public Page<MemberDto> list(@PageableDefault(size = 3) Pageable pageable) {

    return memberRepository.findAll(pageable).map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));
  }

  //  생성자가 동작하고 이후에 바로 동작하게 하는 어노테이션이다.
//  Bean에 대한 초기화를 하는 용도로 사용한다.
  @PostConstruct
  public void init() {

    Team team = new Team("teamname");
    teamRepository.save(team);
    for (int i = 0; i < 100; i++) {
      memberRepository.save(new Member("user" + i, i, team));

    }
  }
}
