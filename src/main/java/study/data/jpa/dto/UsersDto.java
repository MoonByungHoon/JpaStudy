package study.data.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDto {

  private Long id;
  private String username;
  private int age;

  public UsersDto(Long id, String username, int age) {
    this.id = id;
    this.username = username;
    this.age = age;
  }
}
