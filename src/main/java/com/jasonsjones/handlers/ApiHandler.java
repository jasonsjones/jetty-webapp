package com.jasonsjones.handlers;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiHandler extends Handler.Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiHandler.class);
    @Override
    public boolean handle(Request request, Response response, Callback callback) {
        response.getHeaders().put(HttpHeader.CONTENT_TYPE, "application/json" );

        JSONObject json = new JSONObject();
        json.put("status", "success");
        json.put("message", "api handler response");

        LOGGER.info("API response {}", json);
        Content.Sink.write(response, true, json.toString(), callback);
        return true;
    }
}
