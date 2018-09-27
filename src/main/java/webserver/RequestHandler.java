package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.HttpRequestUtils;
import util.IOUtils;

import javax.jws.WebService;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private UserService userService = new UserService();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection
                        .getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            Request request = HttpRequestUtils.parseRequestFrom(in);
            handle(request, out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void handle(Request request, OutputStream out) throws IOException {
        if (request.getTargetPath().equals("/user/create")) {
            Map<String, String> params = request.getParameters();
            String id = params.get("userId");
            String pw = params.get("password");
            String name = params.get("name");
            String email = params.get("email");
            User newUser = new User(id, pw, name, email);
            userService.join(newUser);
        }

        DataOutputStream dataOutputStream = new DataOutputStream(out);
        Path path = Paths.get(WebServer.ROOT + request.getTargetPath());
        byte[] body = Files.readAllBytes(path);
        response200Header(dataOutputStream, body.length);
        responseBody(dataOutputStream, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
