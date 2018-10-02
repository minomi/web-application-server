package util;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 29..
 */
public class UserUtils {
    private static final Logger log = LoggerFactory.getLogger(UserUtils.class);

    public static boolean checkPassword(User user, User userFromDB) {
        return user.getPassword().equals(userFromDB.getPassword());
    }

    public static boolean checkEmptyItem(User user) {
        return user.getName() != null &&
                user.getPassword() != null &&
                user.getUserId() != null &&
                user.getEmail() != null;
    }

    public static User user(Map<String, String> params) {
        User user = new User();
        params.keySet()
                .forEach(key -> callSetter(user, key, params.get(key)));
        return user;
    }

    private static void callSetter(User user, String paramKey, String paramValue) {
        Arrays.stream(user.getClass().getMethods())
                .filter(method -> method.getName().startsWith("set"))
                .filter(method -> method.getName().substring(3).toLowerCase().equals(paramKey.toLowerCase()))
                .findFirst()
                .ifPresent(method -> call(method, user, paramValue));
    }

    private static void call(Method method, Object object, String param) {
        try {
            method.invoke(object, param);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
