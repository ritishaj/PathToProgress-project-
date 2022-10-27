package persistence;
import model.CurrentCourses;
import model.PastCourses;
import model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonTest {

    protected void checkUser(String name, String username, String password, User user) {
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());

    }
}
