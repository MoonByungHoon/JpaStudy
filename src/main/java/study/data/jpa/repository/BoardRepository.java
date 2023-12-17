package study.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data.jpa.dto.BoardDto;
import study.data.jpa.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

  @Query("select new study.data.jpa.dto.BoardDto(b.id, b.content, u.username, u.age) from Board b join b.users u where u.id = :user_id")
  List<BoardDto> findWriterById(@Param("user_id") final Long id);
}
