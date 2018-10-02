package webserver;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import controller.*;
import http.Request;
import http.RequestMethod;
import http.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */

@RunWith(PowerMockRunner.class)
public class DispatcherServletTest {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServletTest.class);

    private DispatcherServlet dispatcherServlet;
    private Request mockRequest;
    private Response mockResponse;

    @Before
    public void setUp() {
        dispatcherServlet = new DispatcherServlet();
        mockRequest = new Request();
        mockResponse = new Response();
    }

    @Test
    public void loginResponse() {
        try {
            mockRequest.setUrlString("/user/login");
            dispatcherServlet.service(mockRequest, mockResponse);
            assertNotNull(mockResponse.getBody());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail((e.getMessage()));
        }
    }

    @Test
    public void failLoginResponse() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "id");
        params.put("password", "pw");

        try {
            mockRequest.setUrlString("/user/login")
                    .setMethod(RequestMethod.POST)
                    .setParameters(params);
            dispatcherServlet.service(mockRequest, mockResponse);
            assertNotNull(mockResponse.getBody());
            assertEquals("logined=false", mockResponse.getCookies().toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail(e.getMessage());
        }
    }

    @Test
    public void successJoinTest() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "id");
        params.put("password", "pw");
        params.put("name", "name");
        params.put("email", "email");
        try {
            mockRequest.setUrlString("/user/create")
                    .setMethod(RequestMethod.POST)
                    .setParameters(params);
            dispatcherServlet.service(mockRequest, mockResponse);
            assertEquals("/index.html", mockResponse.getRedirectUrl());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail(e.getMessage());
        }
    }

    @Test
    public void getRootController() {
        mockRequest.setUrlString("/index.html");
        assertEquals(RootController.class, getController(mockRequest).getClass());
    }

    @Test
    public void getJoinController() {
        mockRequest.setUrlString("/user/create");
        assertEquals(JoinController.class, getController(mockRequest).getClass());

        mockRequest.setUrlString("/user/form.html");
        assertEquals(JoinController.class, getController(mockRequest).getClass());
    }

    @Test
    public void getUserListController() {
        mockRequest.setUrlString("/user/list");
        assertEquals(UserListController.class, getController(mockRequest).getClass());
    }

    @Test
    public void getLoginController() {
        mockRequest = new Request()
                .setUrlString("/user/login.html")
                .setMethod(RequestMethod.GET);
        assertEquals(LoginController.class, getController(mockRequest).getClass());
    }

    private Controller getController(Request mockRequest) {
        try {
            return Whitebox.invokeMethod(
                    dispatcherServlet,
                    "getController",
                    mockRequest);
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail();
        }
        return null;
    }

}