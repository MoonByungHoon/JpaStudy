package study.data.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.data.jpa.repository.JdbcMemberRepository;
import study.data.jpa.repository.MembersRepository;
import study.data.jpa.service.MembersService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

  DataSource dataSource;

  @Autowired
  public SpringConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MembersService membersService() {
    return new MembersService(membersRepository());
  }

  @Bean
  public MembersRepository membersRepository() {
//    return new MemoryMemberRepository();
    return new JdbcMemberRepository(dataSource);
  }
}
