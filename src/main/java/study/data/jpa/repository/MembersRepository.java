package study.data.jpa.repository;

import study.data.jpa.domain.Members;

import java.util.List;
import java.util.Optional;

public interface MembersRepository {

  Members save(Members members);

  Optional<Members> findById(Long id);

  Optional<Members> findByName(String name);

  List<Members> findAll();

}
