package study.data.jpa.dto;

public class EnumMapperValue {
  private String code;
  private String title;

  public EnumMapperValue(EnumMapperType enumMapperType) {
    code = enumMapperType.getCode();
    System.out.println("이넘 맵퍼 벨류 생성자 코드 : " + code);

    title = enumMapperType.getTitle();
    System.out.println("이넘 맵퍼 벨류 생성자 타이틀 : " + title);
  }

  public String getCode() {
    System.out.println("이넘 맵퍼 벨류 겟코드 : " + code);
    return code;
  }

  @Override
  public String toString() {
    return "{" +
            "code='" + code + '\'' +
            "}";
  }
}
