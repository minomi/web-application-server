package controller;

import http.Request;
import http.RequestMethod;
import http.Response;
import model.User;
import service.UserService;
import util.UserUtils;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class JoinController implements Controller {

    private UserService userService = new UserService();

    @Override
    public String execute(Request request, Response response) {
        RequestMethod requestMethod = request.getMethod();

        switch (requestMethod) {
            case GET:
                return "/user/form.html";
            case POST:
                User user = UserUtils.user(request.getParameters());
                userService.join(user);
                return "redirect:/index.html";
        }
        return "/index.html";
    }

}
