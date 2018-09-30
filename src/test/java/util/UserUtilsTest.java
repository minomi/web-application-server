package util;

import model.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 29..
 */
public class UserUtilsTest {

    @Test
    public void checkPassword() {
        User mockUser = new User().setPassword("1234");
        User mockUserFromDB = new User().setPassword("1234");
        assertTrue(UserUtils.checkPassword(mockUser, mockUserFromDB));

        User mockUser1 = new User().setPassword("1234");
        User mockUserFromDB1 = new User().setPassword("2345");
        assertFalse(UserUtils.checkPassword(mockUser1, mockUserFromDB1));
    }

    @Test
    public void checkEmptyItem() {
        User fullItemUser = new User().setUserId("id").setPassword("pw").setName("name").setEmail("email");
        assertTrue(UserUtils.checkEmptyItem(fullItemUser));

        User emptyItemUser = new User().setUserId("id");
        assertFalse(UserUtils.checkEmptyItem(emptyItemUser));
    }

    @Test
    public void createUserFromMap() {
        String expectedID = "id";
        String expectedPW = "pw";
        String expectedEmail = "email";
        String expectedName = "name";

        Map<String, String> params = new HashMap<>();
        params.put("userId", expectedID);
        params.put("password", expectedPW);
        params.put("email", expectedEmail);
        params.put("name", expectedName);

        User testUser = UserUtils.user(params);
        assertEquals(expectedID, testUser.getUserId());
        assertEquals(expectedPW, testUser.getPassword());
        assertEquals(expectedEmail, testUser.getEmail());
        assertEquals(expectedName, testUser.getName());
    }
}