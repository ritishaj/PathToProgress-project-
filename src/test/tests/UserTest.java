package tests;
import model.Course;
import model.CurrentCourses;
import model.PastCourses;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class UserTest {
    User testUser;
    CurrentCourses currentCoursesTest;
    PastCourses pastCoursesTest;
    Course c1;
    Course c2;
    Course c3;

    @BeforeEach
    public void setup() {
        testUser = new User("mark", "gio", "pooperskit9");
        c1 = new Course("ENG101");
        c2 = new Course("MATH104");
        c3 = new Course("LING100");
    }

    @Test
    public void testConstructorThreeFields() {
        assertEquals("mark", testUser.getName());
        assertEquals("gio", testUser.getUsername());
        assertEquals("pooperskit9", testUser.getPassword());

        assertEquals(0, testUser.getCurrentCourses().numOfCourses());
    }

    @Test
    public void testConstructorTwoFields() {
        User inputTestUser = new User("gio", "pooperskit9");
        assertEquals("gio", testUser.getUsername());
        assertEquals("pooperskit9", testUser.getPassword());

    }

    @Test
    public void addToCurrentTestNotThere() {
        testUser.addToCurrent(c1);
        assertEquals(1, testUser.getCurrentCourses().numOfCourses());
        assertTrue(testUser.getCurrentCourses().doesContain(c1));
    }


    @Test
    public void addToCurrentTestAlreadyThere() {
        testUser.addToCurrent(c1);
        testUser.addToCurrent(c1);
        testUser.addToCurrent(c2);
        testUser.addToCurrent(c1);
        assertEquals(2, testUser.getCurrentCourses().numOfCourses());
        assertTrue(testUser.getCurrentCourses().doesContain(c2));
        assertEquals(c2, testUser.getCurrentCourses().getCourses().get(1));
    }

    @Test
    public void removeFromCurrentTestNotThere() {
        testUser.addToCurrent(c1);
        testUser.addToCurrent(c2);
        testUser.addToCurrent(c2);
        assertEquals(2, testUser.getCurrentCourses().numOfCourses());

        testUser.removeFromCurrent(c3);
        assertEquals(2, testUser.getCurrentCourses().numOfCourses());

    }

    @Test
    public void removeFromCurrentTestAlreadyThere() {
        testUser.addToCurrent(c1);
        testUser.addToCurrent(c2);
        testUser.addToCurrent(c3);
        assertEquals(3, testUser.getCurrentCourses().numOfCourses());
        assertEquals("LING100", testUser.getCurrentCourses().getCourses().get(2).getCourseName());

        testUser.removeFromCurrent(c3);
        assertEquals(2, testUser.getCurrentCourses().numOfCourses());
    }

    @Test
    public void addGradeGoalTest() {
        testUser.addToCurrent(c1);
        testUser.addGradeGoal(89);
        testUser.addToCurrent(c2);
        testUser.addGradeGoal(90);

        assertEquals(89,testUser.getGradeGoal(c1));
        assertEquals(90,testUser.getGradeGoal(c2));
        assertEquals(90,testUser.getGradeGoals().get(1));
    }

    @Test
    public void addToPastTest() {
        testUser.addToCurrent(c1);
        testUser.addToCurrent(c2);
        testUser.addToPast(c1);
        assertEquals(2, testUser.getCurrentCourses().numOfCourses());
        assertEquals(1, testUser.getPastCourses().numOfCourses());
    }


}
