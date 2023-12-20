package study.data.jpa.repository;

import study.data.jpa.domain.Members;

import java.util.*;

public class MemoryMemberRepository implements MembersRepository {

  private static Map<Long, Members> store = new HashMap<>();
  private static long sequence = 0L;

  @Override
  public Members save(Members members) {
    members.setId(++sequence);
    store.put(members.getId(), members);
    return members;
  }

  @Override
  public Optional<Members> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Optional<Members> findByName(String name) {
    return store.values().stream()
            .filter(members -> members.getName().equals(name))
            .findAny();//하나를 찾을때까지 계속 동작을 진행함.
  }

  @Override
  public List<Members> findAll() {
    return new ArrayList<>(store.values());
  }

  public void clearStore() {
    store.clear();
  }
}
