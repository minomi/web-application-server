package service;

import db.DataBase;
import model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 29..
 */
public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        User testUser1 = new User().setUserId("testID1").setPassword("testPW1");
        User testUser2 = new User().setUserId("testID2").setPassword("testPW2");
        User testUser3 = new User().setUserId("testID3").setPassword("testPW3");
        DataBase.addUser(testUser1);
        DataBase.addUser(testUser2);
        DataBase.addUser(testUser3);
        userService = new UserService();
    }

    @Test
    public void joinSuccess() {
        User mockUser = new User()
                .setUserId("testID4")
                .setPassword("testPW2")
                .setName("testName")
                .setEmail("testEmail");
        assertTrue(userService.join(mockUser));
    }

    @Test
    public void cannotJoinDuplicationID() {
        User mockUser = new User().setUserId("testID1");
        assertFalse(userService.join(mockUser));
    }

    @Test
    public void cannotJoinEmptyItem() {
        User mockUser = new User().setUserId("testID").setPassword("testPW");
        assertFalse(userService.join(mockUser));
    }

    @Test
    public void loginSuccess() {
        User mockUser = new User().setUserId("testID1").setPassword("testPW1");
        assertTrue(userService.login(mockUser));
    }

    @Test
    public void cannotLoginWrongPW() {
        User mockUser = new User().setUserId("testID1").setPassword("wrongPW");
        assertFalse(userService.login(mockUser));
    }

    @Test
    public void cannotLoginNonExistUser() {
        User mockUser = new User().setUserId("id").setPassword("wq");
        assertFalse(userService.login(mockUser));
    }

    @Test
    public void cannotLoginEmptyItemUser() {
        User mockUser = new User();
        assertFalse(userService.login(mockUser));
    }
}