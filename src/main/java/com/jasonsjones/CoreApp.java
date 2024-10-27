package com.jasonsjones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreApp.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Core app started...");
        CoreServer server = new CoreServer();
        server.start();
    }
}
