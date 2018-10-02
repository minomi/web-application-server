package controller;

import http.Request;
import http.Response;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class RootController implements Controller {
    @Override
    public String execute(Request request, Response response) {
        return "/index.html";
    }
}
