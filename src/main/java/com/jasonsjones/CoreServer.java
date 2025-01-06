package com.jasonsjones;

import com.jasonsjones.handlers.ApiHandler;
import com.jasonsjones.handlers.BaseDocumentHandler;
import com.jasonsjones.handlers.LoggingHandler;

import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.PathMappingsHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.ResourceFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CoreServer {
    private final Server server;

    public CoreServer() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        server = new Server(threadPool);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        PathMappingsHandler pathMappingsHandler = new PathMappingsHandler();
        pathMappingsHandler.addMapping(PathSpec.from("/"), new BaseDocumentHandler());
        pathMappingsHandler.addMapping(PathSpec.from("/about"), new BaseDocumentHandler());
        pathMappingsHandler.addMapping(PathSpec.from("/api/*"), new ApiHandler());

        ResourceHandler staticResourceHandler = getStaticResourceHandler();

        Handler.Sequence rootHandler = new Handler.Sequence();
        rootHandler.setHandlers(new LoggingHandler(), pathMappingsHandler, staticResourceHandler);
        server.setHandler(rootHandler);
    }

    public void start() throws Exception {
        server.start();
    }

    private ResourceHandler getStaticResourceHandler() {
        Path webRoot = Paths.get("src/main/resources/web").toAbsolutePath().normalize();
        if (!Files.isDirectory(webRoot)) {
            System.err.println("ERROR: Unable to find " + webRoot + ".");
            System.exit(-1);
        }
        ResourceFactory resourceFactory = ResourceFactory.of(server);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirAllowed(true);
        resourceHandler.setBaseResource(resourceFactory.newResource(webRoot));
        return resourceHandler;
    }
}
