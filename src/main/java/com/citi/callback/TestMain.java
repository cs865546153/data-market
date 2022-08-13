package com.citi.callback;

public class TestMain {
  public static void main(String[] args) {
/*    WeChat weChat = new WeChat("LittleLawson", 100);
    weChat.pay();*/

    AliPay ap = new AliPay("LittleLawson", 100);
    ap.pay();

  }
}
