package study.data.jpa.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PayGroup {
  CASH("현금", Arrays.asList("PAYCO", "TOSS")),
  CARD("카드", Arrays.asList("POINT")),
  EMPTY("없음", Collections.EMPTY_LIST);

  private String title;
  private List<String> payList;

  PayGroup(String title, List<String> payList) {
    this.title = title;
    this.payList = payList;
  }

  public static PayGroup findByPayCode(String code) {
    return Arrays.stream(PayGroup.values())
            .filter(payGroup -> payGroup.hasPayCode(code))
            .findAny()
            .orElse(EMPTY);
  }

  public boolean hasPayCode(String code) {
    return payList.stream()
            .anyMatch(pay -> pay.equals(code));
  }

  public String getTitle() {
    return title;
  }
}
