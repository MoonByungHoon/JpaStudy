package study.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data.jpa.dto.BoardDto;
import study.data.jpa.entity.Board;
import study.data.jpa.entity.Users;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

  @Query("select new study.data.jpa.dto.BoardDto(b.id, b.content, u.username, u.age) from Board b join b.users u where u.id = :user_id")
  List<BoardDto> findWriterById(@Param("user_id") final Long id);

  @Query(value = "select new study.data.jpa.dto.BoardDto(b.id, b.content, u.username) from Board b left join b.users u where u = :user", countQuery = "select count(b) from Board b where b.users is not null ")
  Page<BoardDto> findTestAll(@Param("user") Users users, Pageable pageable);
}
