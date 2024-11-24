package com.jasonsjones;

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

        ContextHandler resourceContextHandler = getResourceContextHandler();
        ContextHandler coreContextHandler = new ContextHandler(new CoreHandler(), "/core");

        ContextHandlerCollection handlers = new ContextHandlerCollection();
        handlers.setHandlers(resourceContextHandler, coreContextHandler);
        server.setHandler(handlers);
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
