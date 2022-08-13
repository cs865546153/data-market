package com.citi.callback;

public class WeChat implements CallBack{
  public String weChatId;//wechat id
  public String name;//实际用户名
  public double money;//充值金额
  public Server server = new Server();//维持一个Server的引用

  public WeChat(String name,double money)
  {
    this.name = name;
    this.money = money;
  }

  public void pay(){//充值
    System.out.println("01. 微信客户端接到用户"+this.name+"充值的请求，准备调用方法payToTelecom，进行充值:");
    server.payToTelecom(WeChat.this);//将自己【WeChat这个对象传入参数中】
    System.out.println("05. 充值成功消息已通知用户，payToTelecom()调用完毕");
  }

  //实现Callback中通知用户的方法
  @Override
  public void notifyToUser() {
    System.out.println("04. 尊敬的客户，您已成功充值100元。" +
      "查询余额请直接回复102、登录欢go网站（www.189.cn）、欢go客户端。[中国电信上海公司]");
  }
}
