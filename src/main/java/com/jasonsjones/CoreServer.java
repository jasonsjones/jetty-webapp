package com.jasonsjones;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CoreHandler extends Handler.Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreHandler.class);
    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        LOGGER.info("Handling request...");

        String user = "Guest";
        String method = request.getMethod();
        String path = request.getHttpURI().getPath();
        LOGGER.info("{} => {} {}", user, method, path);

        callback.succeeded();
        return true;
    }
}

public class CoreServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreApp.class);
    private final Server server;

    public CoreServer() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        server = new Server(threadPool);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);

        server.addConnector(connector);

        ContextHandler contextHandler = new ContextHandler(new CoreHandler(), "/");
        server.setHandler(contextHandler);
    }

    public void start() throws Exception {
        server.start();
    }
}
