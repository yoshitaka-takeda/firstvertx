package com.tento.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(context -> {
      String address = context.request().connection().remoteAddress().toString();

      MultiMap queryParam = context.queryParams();
      String name = (queryParam.contains("name"))? queryParam.get("name") : "?";
      context.json(new JsonObject()
        .put("name", name)
        .put("address", address)
        .put("message", "Hello "+ name + ", from: "+address)
      );
    });

    vertx.createHttpServer().requestHandler(
      // req -> {
      //   req.response()
      //     .putHeader("content-type", "text/plain")
      //     .end("Hello from Vert.x!");
      // }
      router
    ).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
