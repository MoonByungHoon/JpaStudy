package study.data.jpa.repository;

import jakarta.persistence.EntityManager;
import study.data.jpa.domain.Members;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MembersRepository {
  private final EntityManager em;

  public JpaMemberRepository(EntityManager em) {
    this.em = em;
  }

  @Override
  public Members save(Members members) {
    em.persist(members);

    return members;
  }

  @Override
  public Optional<Members> findById(Long id) {
    return Optional.ofNullable(em.find(Members.class, id));
  }

  @Override
  public Optional<Members> findByName(String name) {
    return em.createQuery("select m from Members m where m.name = :name", Members.class)
            .setParameter("name", name)
            .getResultList()
            .stream().findAny();
  }

  @Override
  public List<Members> findAll() {

    return em.createQuery("select m from Members m", Members.class).getResultList();
  }
}
