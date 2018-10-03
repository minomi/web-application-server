package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 10. 3..
 */
public class HttpSessions {
    static private Map<String, HttpSession> sessionMap = new HashMap<>();

    static public void removeSession(String id) {
        sessionMap.remove(id);
    }

    static public HttpSession getSession(String id) {
        HttpSession session = sessionMap.get(id);

        if (session == null) {
            session = new HttpSession(id);
            sessionMap.put(id, session);
            return session;
        }

        return session;
    }

}
