package com.citi.data_market;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;

import java.util.ArrayList;

public class MysqlVerticle extends AbstractVerticle {

  //声明Router
  Router router;

  //第一步 配置连接参数
  MySQLConnectOptions connectOptions = new MySQLConnectOptions()
    .setPort(3306)
    .setHost("127.0.0.1")
    .setDatabase("mysql")
    .setUser("root")
    .setPassword("Suri52022$");

  //第二步 配置连接池 Pool options
  PoolOptions poolOptions = new PoolOptions()
    .setMaxSize(5);

  //第三步 Create the client pool
  MySQLPool client;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    client = MySQLPool.pool(vertx, connectOptions, poolOptions);

    //初始化Router
    router = Router.router(vertx);

    //配置Router解析url
    router.route("/").handler(
      req -> {
        req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!");
      }
    );

    //配置Router解析url
    router.route("/test/list").handler(
      req -> {
        //Get a connection from the pool
        client.getConnection(ar1 -> {
          if(ar1.succeeded()){
            System.out.println("Connected");
            //Obtain our connection
            SqlConnection conn = ar1.result();
            //All operations execute on the same connection
            conn
              .query("select id, name, age, info from person")
              .execute(ar2 -> {
                //Release the connection to the pool
                conn.close();
                if(ar2.succeeded()){
                  var list = new ArrayList<JsonObject>();
                  ar2.result().forEach(item -> {
                    var json = new JsonObject();
                    json.put("id", item.getValue("id"));
                    json.put("name", item.getValue("name"));
                    json.put("age", item.getValue("age"));
                    json.put("info", item.getValue("info"));
                    list.add(json);
                  });
                  req.response()
                    .putHeader("content-type", "application/json")
                    .end(list.toString());
                }else{
                  req.response()
                    .putHeader("content-type", "text/plain")
                    .end(ar2.cause().toString());
                }
              });
          }else{
            System.out.println("Could not connect:" + ar1.cause().getMessage());
          }
        });
      }
    );

    //将Router与vertx HttpServer 绑定
    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
