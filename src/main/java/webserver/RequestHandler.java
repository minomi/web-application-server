package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


import http.Cookies;
import http.Request;
import http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection
                        .getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            
            Request request = HttpRequestUtils.parseRequestFrom(in);
            Response response = new Response();

            if (request.getUrlString().startsWith("/js") ||
                request.getUrlString().startsWith("/css")) {
                String urlString = request.getUrlString();
                Path path = Paths.get(WebServer.ROOT + urlString);
                response.appendBody(Files.readAllBytes(path));
            }

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.service(request, response);

            if(response.getResponseCode() == 302) {
                response302Header(dataOutputStream, response);
                return;
            }
            
            response200Header(dataOutputStream, response);
            responseBody(dataOutputStream, response.getBody());

            log.debug("Parse request {}", request);
            log.debug("Parse response {}", response);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



    private void response302Header(DataOutputStream dataOutputStream, Response response) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            dataOutputStream.writeBytes("Location: "+ response.getRedirectUrl() +"\r\n");
            Optional.ofNullable(response.getCookies())
                    .ifPresent(cookies -> writeCookies(dataOutputStream, cookies));
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dataOutputStream, Response response) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: " + response.getContentType() + "\r\n");
            dataOutputStream.writeBytes("Content-Length: " + response.getContentLength() + "\r\n");
            Optional.ofNullable(response.getCookies())
                    .ifPresent(cookies -> writeCookies(dataOutputStream, cookies));
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeCookies(DataOutputStream dataOutputStream, Cookies cookies) {
        try {
            dataOutputStream.writeBytes(cookies.makeResponseString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dataOutputStream, byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
