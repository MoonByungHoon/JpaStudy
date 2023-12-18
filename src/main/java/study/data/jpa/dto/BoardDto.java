package study.data.jpa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {

  private Long id;
  private String content;
  private String username;
  private int age;

  public BoardDto(Long id, String content, String username, int age) {
    this.id = id;
    this.content = content;
    this.username = username;
    this.age = age;
  }

  public BoardDto(Long id, String content, String username) {
    this.id = id;
    this.content = content;
    this.username = username;
  }
}
