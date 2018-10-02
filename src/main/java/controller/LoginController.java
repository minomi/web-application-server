package controller;

import http.Cookies;
import http.Request;
import http.Response;
import model.User;
import service.UserService;
import util.UserUtils;
import http.RequestMethod;

import java.util.Map;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class LoginController implements Controller {

    private UserService userService = new UserService();

    @Override
    public String execute(Request request, Response response) {
        Map<String, String> params = request.getParameters();
        RequestMethod requestMethod = request.getMethod();

        switch (requestMethod) {
            case GET:
                return "/user/login.html";
            case POST:
                User user = UserUtils.user(params);

                String view = userService.login(user) ?
                        "/index.html" :
                        "/user/login_failed.html";

                Cookies cookies = userService.login(user) ?
                        new Cookies().putKeyValue("logined", "true") :
                        new Cookies().putKeyValue("logined", "false");

                request.setCookies(cookies);
                return view;
        }

        return "/index.html";
    }

}
