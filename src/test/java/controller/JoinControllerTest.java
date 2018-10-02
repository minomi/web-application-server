package controller;

import db.DataBase;
import http.Request;
import http.RequestMethod;
import http.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class JoinControllerTest {

    private JoinController joinController;
    private Request mockRequest;
    private Response mockResponse;

    @Before
    public void setUp() {
        joinController = new JoinController();
        mockRequest = new Request();
        mockResponse = new Response();
    }

    @Test
    public void requestJoinFormTest() {
        mockRequest.setUrlString("/user/create")
                .setMethod(RequestMethod.GET);

        String view = joinController.execute(mockRequest, mockResponse);
        String expectedView = "/user/form.html";

        assertEquals(expectedView, view);
    }

    @Test
    public void successJoinTest() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "id");
        params.put("password", "pw");
        params.put("name", "name");
        params.put("email", "email");

        mockRequest.setUrlString("/user/create.html")
        .setMethod(RequestMethod.POST)
        .setParameters(params);

        String view = joinController.execute(mockRequest, mockResponse);
        String expectedView = "redirect:/index.html";

        assertEquals(expectedView, view);
    }

    @Test
    public void failJoinTest() {

    }
}