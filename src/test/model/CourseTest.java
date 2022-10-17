package model;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course testCourse;

    @BeforeEach
    public void setup(){
        testCourse = new Course("test");
    }

    @Test
    public void testConstructor(){
        assertEquals("test", testCourse.getCourseName());
    }

}





