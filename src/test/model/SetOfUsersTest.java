package model;

import org.junit.Before;
import org.junit.jupiter.api.*;


import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
public class SetOfUsersTest {
    SetOfUsers testUsers;
    User u1;
    User u2;
    User u3;

    @BeforeEach
    public void setup() {
        testUsers = new SetOfUsers();
        u1 = new User("mark", "gio", "pooperskit9");
        u2 = new User("james", "clark", "shame1234");
        u3 = new User("cat", "catty", "mcrhymes123");
    }

    @Test
    public void addUserTest(){
        testUsers.addUser(u1);
        assertEquals(1, testUsers.numOfUsers());

        testUsers.addUser(u2);
        testUsers.addUser(u3);
        assertEquals(3, testUsers.numOfUsers());
        assertTrue(testUsers.hasUser(u1));
        assertTrue(testUsers.hasUser(u3));
    }

    @Test
    public void validateUserTestTrue(){
        testUsers.addUser(u1);
        User user = new User("mark", "gio", "pooperskit9");
        assertTrue(testUsers.validateUser(user));
    }

    @Test
    public void validateUserTestFalse(){
        testUsers.addUser(u1);
        testUsers.addUser(u2);
        User user = new User("bob", "clark", "pooperskit9");
        assertFalse(testUsers.validateUser(user));
        assertTrue(testUsers.hasUser(u2));
    }

    @Test
    public void getNameFromLoginTestNull(){
        testUsers.addUser(u1);
        testUsers.addUser(u2);
        assertTrue(testUsers.hasUser(u2));
        assertEquals(testUsers.getNameFromLogin(u3), null);
    }

    @Test
    public void getNameFromLoginTestNotNull(){
        testUsers.addUser(u1);
        testUsers.addUser(u2);
        assertTrue(testUsers.hasUser(u2));
        assertEquals(testUsers.getNameFromLogin(u1), "mark");
    }

    @Test
    public void getAUserNull(){
        testUsers.addUser(u1);
        testUsers.addUser(u2);
        assertTrue(testUsers.hasUser(u2));
        User user = new User("bob", "goo", "pooperskit9");
        assertEquals(null, testUsers.getAUser(user));
    }

    @Test
    public void getAUserMultipleTimes(){
        testUsers.addUser(u1);
        testUsers.addUser(u2);
        assertTrue(testUsers.hasUser(u2));
        User user = new User("mark", "gio", "pooperskit9");
        User user2 = new User("mark", "grow", "pooperskit9");
        assertEquals(u1, testUsers.getAUser(user));
        assertEquals(null, testUsers.getAUser(user2));

    }




}
