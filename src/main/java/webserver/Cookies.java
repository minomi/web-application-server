package webserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 29..
 */
public class Cookies {

    private Map<String, String> cookies = new HashMap<>();
    private String path = "/";

    public Cookies() { }

    public Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Cookies putKeyValue(String key, String value) {
        cookies.put(key, value);
        return this;
    }

    public String makeResponseString() {
        return cookies.keySet()
                .stream()
                .reduce("", (response, key) ->
                        response += ("Set-Cookie: " + key + "=" + cookies.get(key) + "; Path=" + path + "\r\n"));
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        List<String> cookieString = cookies.keySet()
                                            .stream()
                                            .map(key -> key + "=" + cookies.get(key))
                                            .collect(Collectors.toList());
        return String.join(";", cookieString);
    }
}
