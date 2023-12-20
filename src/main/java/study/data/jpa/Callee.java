package study.data.jpa;

import java.util.Scanner;

public class Callee {
  private String msg;
  private CallBack callback;

  @FunctionalInterface
  public interface CallBack {
    public void onGetMessage(Callee callee);
  }

  public Callee() {
    System.out.println("5번");
    this.msg = "";
    this.callback = null;
    System.out.println("6번");
  }

  public String getMsg() {
    System.out.println("7번");
    return msg;
  }

  public void setCallback(CallBack callback) {
    System.out.println("8번");
    this.callback = callback;
  }

  public void onInputMessage() {
    System.out.println("9번");
    Scanner scanner = new Scanner(System.in);
    System.out.println("10번");
    this.msg = ""; //초기화
    System.out.println("11번");
    System.out.print("메시지를 입력하세요 : ");
    System.out.println("12번");
    this.msg = scanner.nextLine();
    System.out.println("13번");

    if (this.callback != null) { //callback처리
      System.out.println("14번");
      this.callback.onGetMessage(Callee.this);
      System.out.println("15번");
    }
  }
}
