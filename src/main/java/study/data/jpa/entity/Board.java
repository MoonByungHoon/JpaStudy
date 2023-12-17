package study.data.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@ToString(of = {"id", "content"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

  @Id
  @GeneratedValue
  @Column(name = "board_id")
  private Long id;
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users users;

  public Board(final String content) {
    this.content = content;
  }

  public Board(String content, Users users) {
    this.content = content;
    this.users = users;
  }

  public void changeUser(Users users) {
    this.users = users;
    users.getBoards().add(this);
  }

  public String getContent() {
    return content;
  }
}
