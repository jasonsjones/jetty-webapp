package com.jasonsjones.handlers;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class BaseDocumentHandler extends Handler.Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDocumentHandler.class);

    @Override
    public boolean handle(Request request, Response response, Callback callback) {
        if (!request.getMethod().equals("GET") || !request.getHttpURI().getPath().equals("/")) {
            return false;
        }

        LOGGER.info("Base document response");
        File indexFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("web/index.html")).getFile());
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(indexFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        response.getHeaders().add("Content-Type", "text/html");
        Content.Sink.write(response, true, sb.toString(), callback);
        return true;
    }
}
