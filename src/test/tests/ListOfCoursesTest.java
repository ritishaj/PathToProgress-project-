package tests;

import model.Course;
import model.ListOfCourses;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

public abstract class ListOfCoursesTest {
    ListOfCourses testCourseList;
    Course c1;
    Course c2;
    Course c3;

    @BeforeEach
    public void setup() {
        c1 = new Course("PHIL220");
        c2 = new Course("MATH200");
        c3 = new Course("LING100");
    }

    @Test
    public void addCourseTestNotThere() {
        testCourseList.addCourse(c1);
        assertEquals(1, testCourseList.numOfCourses());
        assertTrue(testCourseList.doesContain(c1));

    }

    @Test
    public void addCourseTestAlreadyThere() {
        testCourseList.addCourse(c1);
        testCourseList.addCourse(c2);
        assertEquals(2, testCourseList.numOfCourses());
        assertTrue(testCourseList.doesContain(c1));
        assertTrue(testCourseList.getCourseNames().contains("MATH200"));

        testCourseList.addCourse(c1);
        assertEquals(2, testCourseList.numOfCourses());
        assertTrue(testCourseList.doesContain(c2));

    }

    @Test
    public void removeCourseTest(){
        testCourseList.addCourse(c1);
        testCourseList.addCourse(c2);
        testCourseList.addCourse(c3);
        testCourseList.addCourse(c2);

        assertEquals(3, testCourseList.numOfCourses());
        assertTrue(testCourseList.doesContain(c3));
        assertEquals(c1, testCourseList.getCourses().get(0));
        assertTrue(testCourseList.getCourseNames().contains("MATH200"));

        testCourseList.removeCourse(c2);
        assertEquals(2, testCourseList.numOfCourses());
        assertFalse(testCourseList.doesContain(c2));
    }
}
