package service;

import db.DataBase;
import model.User;

import java.util.Optional;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 28..
 */
public class UserService {
    public boolean join(User user) {
        if (DataBase.isExist(user)) {
            return false;
        }
        DataBase.addUser(user);
        return true;
    }
}
