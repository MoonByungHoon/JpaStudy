package study.data.jpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.data.jpa.entity.Member;

import java.util.List;

//사용할 곳의 Repository의 이름과 동일한 이름 + Impl로 클래스명을 지어야한다.
//Custom명은 변경되어도 상관없다.
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {

    return em.createQuery("select m from Member m left join fetch m.team")
            .getResultList();
  }
}
