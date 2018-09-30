package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.HttpRequestUtils;
import util.UserUtils;

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
        String targetPath = request.getTargetPath();
        Map<String, String> params = request.getParameters();
        DataOutputStream dataOutputStream = new DataOutputStream(out);

        if (targetPath.equals("/user/create")) {
            User user = UserUtils.user(params);
            if (userService.join(user)) { response302Header(dataOutputStream, "/index.html"); }
            return;
        }

        if (targetPath.equals("/user/login")) {
            User user = UserUtils.user(params);
            String redirectURL = userService.login(user) ? "/index.html" : "/user/login_failed.html";
            Cookies cookies = userService.login(user) ?
                    new Cookies().putKeyValue("logined", "true") :
                    new Cookies().putKeyValue("logined", "false");
            response302Header(dataOutputStream, redirectURL, cookies);
            return;
        }

        Path path = Paths.get(WebServer.ROOT + request.getTargetPath());
        byte[] body = Files.readAllBytes(path);
        response200Header(dataOutputStream, body.length, Optional.ofNullable(request.getCookies()));
        responseBody(dataOutputStream, body);
    }

    private void response302Header(DataOutputStream dataOutputStream, String redirectURL) {
        try {
            _response302Header(dataOutputStream, redirectURL);
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dataOutputStream, String redirectURL, Cookies cookies) {
        try {
            _response302Header(dataOutputStream, redirectURL);
            dataOutputStream.writeBytes(cookies.makeResponseString());
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void _response302Header(DataOutputStream dataOutputStream, String redirectURL) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            dataOutputStream.writeBytes("Location: "+ redirectURL +"\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(
            DataOutputStream dataOutputStream,
            int lengthOfBodyContent,
            Optional<Cookies> optionalCookies) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            optionalCookies.ifPresent(Cookies::makeResponseString);
            dataOutputStream.writeBytes("\r\n");
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
