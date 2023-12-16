package study.data.jpa.dto;

public enum FeeType2 implements EnumMapperType {
  SSSS("정정"),
  AHAH("아아");

  private String title;

  FeeType2(String title) {
    this.title = title;
    System.out.println("피 타입2 타이틀 : " + title);
  }

  @Override
  public String getCode() {
    System.out.println("피 타입2 겟 코드 : " + name());
    return name();
  }

  @Override
  public String getTitle() {
    System.out.println("피 타입2 겟 타이틀 : " + title);
    return title;
  }
}
