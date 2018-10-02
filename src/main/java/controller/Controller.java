package controller;

import http.Request;
import http.Response;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public interface Controller {
    String execute(Request request, Response response);
}
