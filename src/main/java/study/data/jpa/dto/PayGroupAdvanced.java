package study.data.jpa.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PayGroupAdvanced {

  CASH("현금", Arrays.asList(PayType.PAYCO, PayType.TOSS)),
  CARD("카드", Arrays.asList(PayType.POINT)),
  EMTPY("없음", Collections.EMPTY_LIST);

  private String title;

  private List<PayType> payList;

  PayGroupAdvanced(String title, List<PayType> payList) {
    this.title = title;
    this.payList = payList;
  }

  public static PayGroupAdvanced findByPayType(PayType payType) {
    return Arrays.stream(PayGroupAdvanced.values())
            .filter(payGroup -> payGroup.hasPayCode(payType))
            .findAny()
            .orElse(EMTPY);
  }

  public boolean hasPayCode(PayType payType) {
    return payList.stream()
            .anyMatch(pay -> pay == payType);
  }

  public String getTitle() {
    return title;
  }
}
