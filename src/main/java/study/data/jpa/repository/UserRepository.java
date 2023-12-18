package study.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data.jpa.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

  Users findByUsername(final String Username);
}
