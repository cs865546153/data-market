package com.citi.callback;

public class Server {
  public void payToTelecom(WeChat weChatUser){//给中国移动支付
    System.out.println("02. 向中国电信为用户："+weChatUser.name+"支付"+weChatUser.money+"元");
    System.out.println("03. 收取客户："+weChatUser.name+" 0.1%的手续费");
    this.notifyToWeChat(weChatUser);
  }

  public void payToTelecomByAliPay(AliPay aliUser){//给中国移动支付
    System.out.println("02. 向中国电信为用户："+aliUser.name+"支付"+aliUser.money+"元");
    System.out.println("03. 收取客户："+aliUser.name+" 0.1%的手续费");
    this.notifyToAliPay(aliUser);
  }

  public void notifyToWeChat(WeChat weChatUser){
    weChatUser.notifyToUser();
  }

  public void notifyToAliPay(AliPay AliUser){
    AliUser.notifyToUser();
  }
}
