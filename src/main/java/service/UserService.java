package service;

import db.DataBase;
import model.User;
import util.UserUtils;

import java.util.Optional;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 28..
 */
public class UserService {
    public boolean join(User user) {
        if (!UserUtils.checkEmptyItem(user) || DataBase.isExist(user)) {
            return false;
        }
        DataBase.addUser(user);
        return true;
    }

    public boolean login(User user) {
        Optional<User> optionalUser = Optional.ofNullable(DataBase.findUserById(user.getUserId()));
        return optionalUser
                .filter(userFromDB -> UserUtils.checkPassword(user, userFromDB))
                .isPresent();
    }

}
