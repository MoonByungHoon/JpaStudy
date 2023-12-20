package study.data.jpa.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import study.data.jpa.domain.Members;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MembersRepository {
  private final DataSource dataSource;

  public JdbcMemberRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Members save(Members members) {
    String sql = "insert into members(name) values(?)";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, members.getName());
      pstmt.executeUpdate();
      rs = pstmt.getGeneratedKeys();

      if (rs.next()) {
        members.setId(rs.getLong(1));
      } else {
        throw new SQLException("id 조회 실패");
      }

      return members;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
//      반드시 릴리즈 해주어야한다. 안그러면 문제가 발생할 수 있다.
      close(conn, pstmt, rs);
    }
  }

  @Override
  public Optional<Members> findById(Long id) {
    String sql = "select * from members where id = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, id);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        Members members = new Members();
        members.setId(rs.getLong("id"));
        members.setName(rs.getString("name"));

        return Optional.of(members);
      } else {
        return Optional.empty();
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  @Override
  public List<Members> findAll() {
    String sql = "select * from members";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      List<Members> members = new ArrayList<>();

      while (rs.next()) {
        Members member = new Members();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        members.add(member);
      }
      return members;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  @Override
  public Optional<Members> findByName(String name) {
    String sql = "select * from members where name = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, name);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        Members members = new Members();
        members.setId(rs.getLong("id"));
        members.setName(rs.getString("name"));
        return Optional.of(members);
      }
      return Optional.empty();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  private Connection getConnection() {
//    스프링 프레임워크를 통해서 DB를 사용하려면 꼭 해당 클래스를 통해서 이용해야한다.
    return DataSourceUtils.getConnection(dataSource);
  }

  private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (pstmt != null) {
        pstmt.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (conn != null) {
        close(conn);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void close(Connection conn) throws SQLException {
    DataSourceUtils.releaseConnection(conn, dataSource);
  }
}