package com.local.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class MyHttpServer {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/", new RootHandler());
        server.setExecutor(null);
        server.start();
    }

    private static class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            File file = new File("src/main/resources/index.html");
            he.sendResponseHeaders(200, file.length());
            try (OutputStream os = he.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }

}
