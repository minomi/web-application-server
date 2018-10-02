package controller;

import http.Request;
import http.Response;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class RootControllerTest {

    @Test
    public void execute() {
        RootController controller = new RootController();
        Request mockRequest = new Request();
        Response mockResponse = new Response();
        assertEquals("/index.html", controller.execute(mockRequest, mockResponse));
    }
}