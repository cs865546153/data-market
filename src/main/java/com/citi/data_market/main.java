package com.citi.data_market;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class main extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    vertx.createHttpServer().requestHandler(req -> {
      req.response().end("dsad");
    }).listen(8888);
  }
}
