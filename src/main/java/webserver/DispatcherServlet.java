package webserver;

import controller.*;
import http.Request;
import http.Response;
import util.HttpRequestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class DispatcherServlet {

    void service(Request request, Response response) throws IOException {
        Controller controller = getController(request);
        String view = controller.execute(request, response);
        response.setCookies(request.getCookies());

        if (view.startsWith("redirect")) {
            String redirectURL = view.substring(9);
            response.setResponseCode(302);
            response.setRedirectUrl(redirectURL);
            return;
        }

        Path path = Paths.get(WebServer.ROOT + view);
        byte[] body = Files.readAllBytes(path);

        String contentType = HttpRequestUtils.parseContentType(request);
        response.setContentType(contentType);
        response.setContentLength(body.length);
        response.appendBody(body);
    }

    private Controller getController(Request request) {
        String urlString = request.getUrlString();
        if (urlString.startsWith("/index.html")) {
            return new RootController();
        }
        if (urlString.startsWith("/user/login")) {
            return new LoginController();
        }
        if (urlString.startsWith("/user/create") ||
                urlString.startsWith("/user/form.html")) {
            return new JoinController();
        }
        if (urlString.startsWith("/user/list")) {
            return new UserListController();
        }
        return new RootController();
    }

}
