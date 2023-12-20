package study.data.jpa.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import study.data.jpa.domain.Members;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MembersRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateMemberRepository(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Members save(Members members) {
    return null;
  }

  @Override
  public Optional<Members> findById(Long id) {
    List<Members> result = jdbcTemplate.query("select * from member where id = ?", membersRowMapper());
    return result.stream().findAny();
  }

  @Override
  public Optional<Members> findByName(String name) {
    return Optional.empty();
  }

  @Override
  public List<Members> findAll() {
    return null;
  }

  private RowMapper<Members> membersRowMapper() {
    return (rs, rowNum) -> {
      Members members = new Members();
      members.setId(rs.getLong("id"));
      members.setName(rs.getString("name"));
      return members;
    };
  }
}
