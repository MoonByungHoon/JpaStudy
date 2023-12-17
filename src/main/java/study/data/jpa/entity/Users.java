package study.data.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
//객체가 가진 값을 문자열로 전환시켜 리턴해주는 메소드를 자동으로 생성해준다.
//of 안에 각 필드의 변수명을 적어주게 되면 적지 않은 필드에 대해서는 toString을 생성해주지 않는다.
//현재 클래스에서는 Board라는 연관관계를 필드가 있어서 제외하였다.
@ToString(of = {"id", "username", "age"})
//필드를 받는 파라미터가 없는 기본 생성자를 자동으로 생성해준다.
//Protected 설정으로 인해서 같은 패키지 내에서만 접근이 가능하게 해준다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//모든 필드를 파라미터로 받는 생성자를 자동으로 만들어준다.
//@AllArgsConstructor
public class Users {

  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long id;
  private String username;
  private int age;

  //  포렌키가 생성되지 않는 곳에 mappedBy를 적용하는 것이 옳다.
  @OneToMany(mappedBy = "users")
  private List<Board> boards = new ArrayList<>();

  public Users(String username, int age) {
    this.username = username;
    this.age = age;
  }

  public List<Board> getBoards() {
    return boards;
  }

  public Long getUserId() {
    return id;
  }
}
