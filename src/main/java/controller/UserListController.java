package controller;

import http.Cookies;
import http.Request;
import http.Response;
import model.User;
import service.UserService;
import util.HTMLBuilder;

import java.util.List;
import java.util.Optional;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class UserListController implements Controller {

    private UserService userService = new UserService();

    @Override
    public String execute(Request request, Response response) {
        if (!checkCookie(request.getCookies())) {
            return "/user/login.html";
        }

        List<User> users = userService.list();
        response.setBody(HTMLBuilder.userList(users).getBytes());
        return "/user/list.html";
    }

    private boolean checkCookie(Cookies cookies) {
        return Optional.ofNullable(cookies)
                .map(cookie -> cookie.get("logined"))
                .filter(name -> name.equals("true"))
                .isPresent();
    }



}
