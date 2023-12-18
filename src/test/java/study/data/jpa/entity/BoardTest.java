package study.data.jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import study.data.jpa.dto.BoardDto;
import study.data.jpa.repository.BoardRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class BoardTest {

  @PersistenceContext
  EntityManager em;

  @Autowired
  BoardRepository boardRepository;

  @Test
  @DisplayName("체인지 유저 테스트")
  public void ChangeUserTest() {
    //given
    Users users1 = new Users("aa", 10);
    Users users2 = new Users("bb", 10);
    Users users3 = new Users("cc", 10);

    em.persist(users1);
    em.persist(users2);
    em.persist(users3);

    Board board1 = new Board("1111", users1);
    Board board2 = new Board("2222", users1);
    Board board3 = new Board("3333", users1);
    Board board4 = new Board("4444", users2);
    Board board5 = new Board("5555", users2);
    Board board6 = new Board("6666", users2);

    board1.changeUser(users2);

    em.persist(board1);
    em.persist(board2);
    em.persist(board3);
    em.persist(board4);
    em.persist(board5);
    em.persist(board6);

    em.flush();
    em.clear();

    //when

    List<Users> users = em.createQuery("select u from Users u where u.id = 2", Users.class)
            .getResultList();

    List<Board> boards = em.createQuery("select b from Board b", Board.class)
            .getResultList();

    //then

    assertThat(users.size()).isEqualTo(1);
    assertThat(boards.size()).isEqualTo(6);

    for (Users user : users) {
      System.out.println("user.getBoards() = " + user.getBoards());
    }

    for (Board board : boards) {
      System.out.println("board.getContent() = " + board.getContent());
    }
  }

  @Test
  public void changeDtoTest() {
    //given
    Users users1 = new Users("aa", 10);
    Users users2 = new Users("bb", 10);
    Users users3 = new Users("cc", 10);

    em.persist(users1);
    em.persist(users2);
    em.persist(users3);

    Board board1 = new Board("1111", users1);
    Board board2 = new Board("2222", users1);
    Board board3 = new Board("3333", users1);
    Board board4 = new Board("4444", users2);
    Board board5 = new Board("5555", users2);
    Board board6 = new Board("6666", users2);

    board1.changeUser(users2);

    em.persist(board1);
    em.persist(board2);
    em.persist(board3);
    em.persist(board4);
    em.persist(board5);
    em.persist(board6);

    em.flush();
    em.clear();

    //when
    List<BoardDto> result = boardRepository.findWriterById(users2.getUserId());

    //then
    for (BoardDto boardDto : result) {
      System.out.println("boardDto.getId() = " + boardDto.getId());
      System.out.println("boardDto.getUsername() = " + boardDto.getUsername());
      System.out.println("boardDto.getAge() = " + boardDto.getAge());
      System.out.println("boardDto.getContent() = " + boardDto.getContent());
    }

    assertThat(result.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("JPA 페이징 처리 테스트")
  public void jpaPageTypeTest() {
    //given
    Users users1 = new Users("User.1", 10);
    Users users2 = new Users("User.2", 10);

    em.persist(users1);
    em.persist(users2);

    for (int i = 0; i < 50; i++) {
      boardRepository.save(new Board("Content : " + i, users1));
    }

    for (int i = 0; i < 50; i++) {
      boardRepository.save(new Board("Content : " + i, users2));
    }

    em.flush();
    em.clear();

    PageRequest pageRequest = PageRequest.of(0, 3);

    //when
//    Page<Board> list = boardRepository.findTestAll(users1, pageRequest);

    //then
//    assertThat(list.getSize()).isEqualTo(3);
//    assertThat(list.getTotalElements()).isEqualTo(100);
  }
}
