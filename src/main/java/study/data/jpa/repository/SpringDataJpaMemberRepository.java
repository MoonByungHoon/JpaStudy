package study.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data.jpa.domain.Members;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Members, Long>, MembersRepository {

  @Override
  Optional<Members> findByName(String name);
}
