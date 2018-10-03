package http;

import util.HttpRequestUtils;

import java.util.Map;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 10. 4..
 */
public class HttpCookie {
    private Map<String, String> cookies;

    public HttpCookie(String cookieHeader) {
        cookies = HttpRequestUtils.parseCookies(cookieHeader);
    }

    public String getValue(String name) {
        return cookies.get(name);
    }
}
