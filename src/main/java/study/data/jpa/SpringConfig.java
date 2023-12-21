package study.data.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.data.jpa.aop.TimeTraceAop;
import study.data.jpa.repository.MembersRepository;
import study.data.jpa.service.MembersService;

@Configuration
public class SpringConfig {

  //  DataSource dataSource;

//  @Autowired
//  public SpringConfig(DataSource dataSource) {
//    this.dataSource = dataSource;
//  }

//  private EntityManager em;
//
//  public SpringConfig(EntityManager em) {
//    this.em = em;
//  }

  private final MembersRepository membersRepository;

  @Autowired
  public SpringConfig(MembersRepository membersRepository) {
    this.membersRepository = membersRepository;
  }

  @Bean
  public MembersService membersService() {
    return new MembersService(membersRepository);
  }

  @Bean
  public static TimeTraceAop timeTraceAop() {
    return new TimeTraceAop();
  }

//  @Bean
//  public MembersRepository membersRepository() {
//    return new MemoryMemberRepository();
//    return new JdbcMemberRepository(dataSource);
//    return new JdbcTemplateMemberRepository(dataSource);
//    return new JpaMemberRepository(em);
//  }
}
