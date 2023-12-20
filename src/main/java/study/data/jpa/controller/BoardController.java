package study.data.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.data.jpa.dto.BoardDto;
import study.data.jpa.entity.Users;
import study.data.jpa.repository.BoardRepository;
import study.data.jpa.repository.UserRepository;

@RestController
@RequiredArgsConstructor
public class BoardController {

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  UserRepository userRepository;

  @GetMapping("/boards")
  public Page<BoardDto> boardList(final Pageable pageable) {
    final Users user = userRepository.findByUsername("User1");

    return boardRepository.findTestAll(user, pageable);
  }

//  @PostConstruct
//  public void init() {
//    Users user = userRepository.save(new Users("User1", 20));
//
//    for (int i = 0; i < 3; i++) {
//      boardRepository.save(new Board("Content : " + i, user));
//    }
//  }
}
