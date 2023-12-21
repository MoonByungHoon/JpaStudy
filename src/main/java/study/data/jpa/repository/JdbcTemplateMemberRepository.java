package study.data.jpa.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import study.data.jpa.domain.Members;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MembersRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateMemberRepository(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Members save(Members members) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("members").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", members.getName());

    Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    members.setId(key.longValue());
    return members;
  }

  @Override
  public Optional<Members> findById(Long id) {
    List<Members> result = jdbcTemplate.query("select * from members where id = ?", membersRowMapper(), id);
    return result.stream().findAny();
  }

  @Override
  public Optional<Members> findByName(String name) {
    List<Members> result = jdbcTemplate.query("select * from members where name = ?", membersRowMapper(), name);
    return result.stream().findAny();
  }

  @Override
  public List<Members> findAll() {

    return jdbcTemplate.query("select * from members", membersRowMapper());
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
