package study.data.jpa.dto;

public enum PayType {

  TOSS("토스"),
  PAYCO("페이코"),
  POINT("포인트");


  private String title;

  PayType(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
