package com.citi.data_market;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {
  Router router;
  ThymeleafTemplateEngine thymeleafTemplateEngine;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Initialize Router
    router = Router.router(vertx);
    //Initialize ThymeleafTemplateEngine
    thymeleafTemplateEngine = ThymeleafTemplateEngine.create(vertx);
    //set the static source
    router.route("/static/assets/*").handler(StaticHandler.create());

    //config Router url
    router.route("/").handler(
      req -> {
        var obj = new JsonObject();
        obj.put("db_nums", 123456);
        //ThymeleafTemplateEngine render
        thymeleafTemplateEngine.render(obj,
          "templates/index.html",
          bufferAsyncResult -> {
            if (bufferAsyncResult.succeeded()){
              req.response()
                .putHeader("content-type", "text/html")
                .end(bufferAsyncResult.result());
            }else{
              log.error("start error");
            }
          });
      }
    );
    router.route("/:page").handler(
      req -> {
        var page = req.request().getParam("page");
        var obj = new JsonObject();
        obj.put("db_nums", 123456);
        //ThymeleafTemplateEngine render
        thymeleafTemplateEngine.render(obj,
          "templates/"+page,
          bufferAsyncResult -> {
            if (bufferAsyncResult.succeeded()){
              req.response()
                .putHeader("content-type", "text/html")
                .end(bufferAsyncResult.result());
            }else{
              log.error("start error");
            }
          });
      }
    );
    //Router bounding with vertx HttpServer
    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        log.info("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
