package controller;

import http.Cookies;
import http.Request;
import http.RequestMethod;
import http.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class UserListControllerTest {

    private UserListController userListController;
    private Request mockRequest;
    private Response mockResponse;

    @Before
    public void setUp() {
        userListController = new UserListController();
        mockRequest = new Request();
    }

    @Test
    public void successRequestUserListTest() {
        Cookies mockCookies = new Cookies().putKeyValue("logined", "true");

        mockRequest.setMethod(RequestMethod.GET)
                .setUrlString("user/list")
                .setCookies(mockCookies);

        String view = userListController.execute(mockRequest, mockResponse);
        String expectedView = "/user/list.html";

        assertEquals(expectedView, view);
    }

    @Test
    public void cannotGetUserListWithoutCookie() {
        mockRequest.setMethod(RequestMethod.GET)
                .setUrlString("/user/list");

        String view = userListController.execute(mockRequest, mockResponse);
        String expectedView = "/user/login.html";

        assertEquals(expectedView, view);
    }
}