package controller;

import db.DataBase;
import http.Request;
import http.RequestMethod;
import http.Response;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class LoginControllerTest {

    private Request mockRequest;
    private Response mockResponse;
    private LoginController loginController;

    @Before
    public void setUp() {
        mockRequest = new Request();
        loginController = new LoginController();
    }

    @Test
    public void requestLoginViewTest() {
        mockRequest.setUrlString("/user/login.html")
                .setMethod(RequestMethod.GET);
        String view = loginController.execute(mockRequest, mockResponse);
        String expected = "/user/login.html";
        assertEquals(expected, view);
    }

    @Test
    public void successLoginTest() {
        addTestUserInDB();

        Map<String, String> params = new HashMap<>();
        params.put("userId", "id");
        params.put("password", "pw");

        mockRequest.setUrlString("/user/login.html")
                .setMethod(RequestMethod.POST)
                .setParameters(params);

        String view = loginController.execute(mockRequest, mockResponse);

        String expectedView = "/index.html";
        String expectedCookies = "logined=true";

        assertEquals(expectedView, view);
        assertEquals(expectedCookies, mockRequest.getCookies().toString());
    }

    @Test
    public void failLoginTest() {
        addTestUserInDB();

        Map<String, String> params = new HashMap<>();
        params.put("userId", "id");
        params.put("password", "wrongPW");

        mockRequest.setUrlString("/user/login.html")
                .setMethod(RequestMethod.POST)
                .setParameters(params);

        String view = loginController.execute(mockRequest, mockResponse);

        String expectedView = "/user/login_failed.html";
        String expectedCookies = "logined=false";

        assertEquals(expectedView, view);
        assertEquals(expectedCookies, mockRequest.getCookies().toString());
    }

    private void addTestUserInDB() {
        User user = new User()
                .setUserId("id")
                .setPassword("pw");
        DataBase.addUser(user);
    }
}