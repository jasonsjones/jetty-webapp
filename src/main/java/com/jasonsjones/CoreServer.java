package com.jasonsjones;

import com.jasonsjones.handlers.LoggingHandler;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.resource.ResourceFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ApiHandler extends Handler.Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiHandler.class);
    @Override
    public boolean handle(Request request, Response response, Callback callback) {
        LOGGER.info("API Handler Handling request...");
        callback.succeeded();
        return true;
    }
}

public class CoreServer {
    private final Server server;

    public CoreServer() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        server = new Server(threadPool);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ContextHandler resourceContextHandler = getResourceContextHandler();
        ContextHandler coreContextHandler = new ContextHandler(new ApiHandler(), "/api");
        ContextHandlerCollection ctxHandlers = new ContextHandlerCollection();
        ctxHandlers.setHandlers(resourceContextHandler, coreContextHandler);

        Handler.Sequence rootHandler = new Handler.Sequence();
        rootHandler.setHandlers(new LoggingHandler(), ctxHandlers);
        server.setHandler(rootHandler);
    }

    public void start() throws Exception {
        server.start();
    }

    private ContextHandler getResourceContextHandler() {
        Path webRoot = Paths.get("src/main/resources/web").toAbsolutePath().normalize();
        if (!Files.isDirectory(webRoot)) {
            System.err.println("ERROR: Unable to find " + webRoot + ".");
            System.exit(-1);
        }
        ResourceFactory resourceFactory = ResourceFactory.of(server);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirAllowed(true);
        resourceHandler.setBaseResource(resourceFactory.newResource(webRoot));
        return new ContextHandler(resourceHandler, "/");
    }
}
