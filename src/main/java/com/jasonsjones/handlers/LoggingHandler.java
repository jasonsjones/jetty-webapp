package com.jasonsjones.handlers;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHandler extends Handler.Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingHandler.class);

    @Override
    public boolean handle(Request request, Response response, Callback callback) {
        String user = "Guest";
        String method = request.getMethod();
        String path = request.getHttpURI().getPath();
        LOGGER.info("{} => {} {}", user, method, path);

        // Important: Always forward to the next handler
        // Return false to continue the handler chain
        return false;
    }
}
