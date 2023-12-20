package study.data.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.data.jpa.domain.Members;
import study.data.jpa.service.MembersService;

import java.util.List;

@Controller
public class MembersController {

  private final MembersService membersService;

  @Autowired
  public MembersController(MembersService membersService) {
    this.membersService = membersService;
  }

  @GetMapping("/members/new")
  public String createForm() {
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(MemberForm form) {
    Members member = new Members();
    member.setName(form.getName());

    membersService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    List<Members> members = membersService.findMembers();
    model.addAttribute("members", members);

    return "members/memberList";
  }
}
