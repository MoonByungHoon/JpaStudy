package study.data.jpa;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallBack {
  public static void main(String[] args) {
    System.out.println("1번");
    Callee callee = new Callee();

    System.out.println("2번");
    callee.setCallback(new Callee.CallBack() {

      @Override
      public void onGetMessage(Callee callee) {
        System.out.println("3번");
        //callback
        System.out.println("입력받은 메시지 >" + callee.getMsg());
        System.out.println("4번");
      }
    });

    for (int i = 0; i < 5; i++) { //메시지 발송을 5번까지 보낸다
      System.out.println("반복위에");
      callee.onInputMessage();
      System.out.println("반복아래");
    }
  }
}
