package study.data.jpa.dto;

public enum FeeType implements EnumMapperType {
  PERCENT("정율"),
  MONEY("정액");

  private String title;

  FeeType(String title) {
    this.title = title;
    System.out.println("피 타입 타이틀 : " + title);
  }

  @Override
  public String getCode() {
    System.out.println("피 타입 겟 코드 : " + name());
    return name();
  }

  @Override
  public String getTitle() {
    System.out.println("피 타입 겟 타이틀 : " + title);
    return title;
  }
}
