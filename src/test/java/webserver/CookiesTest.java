package webserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 29..
 */
public class CookiesTest {

    private Cookies cookies;

    @Before
    public void setUp() {
        cookies = new Cookies()
                .putKeyValue("key1", "value1")
                .putKeyValue("key2", "value2")
                .putKeyValue("key3", "value3");
    }

    @Test
    public void cookieString() {
        String expected = "key1=value1;key2=value2;key3=value3";
        assertEquals(expected, cookies.toString());
    }

    @Test
    public void makeResponseString() {
        String expected =
                "Set-Cookie: key1=value1; Path=/\r\n" +
                "Set-Cookie: key2=value2; Path=/\r\n" +
                "Set-Cookie: key3=value3; Path=/\r\n";
        assertEquals(expected, cookies.makeResponseString());
    }

}