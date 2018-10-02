package http;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 10. 2..
 */
public class ResponseTest {

    private Response response;

    @Before
    public void setUp() {
        response = new Response();
    }

    @Test
    public void setAndAppendBody() {
        Response response = new Response();
        response.setBody(new byte[] {1, 2, 3})
                .appendBody(new byte[]{1, 2, 3});
        byte[] expectedBody = new byte[] {1, 2, 3, 1, 2, 3};
        assertArrayEquals(expectedBody, response.getBody());
    }

    @Test
    public void appendBody() {
        response.appendBody(new byte[] {1, 2, 3});
        byte[] expectedBody = new byte[] {1, 2, 3};
        assertArrayEquals(expectedBody, response.getBody());
    }
}