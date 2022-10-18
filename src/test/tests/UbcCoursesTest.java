package tests;

import model.UbcCourses;
import org.junit.jupiter.api.BeforeEach;

public class UbcCoursesTest extends ListOfCoursesTest {

    @BeforeEach
    public void setup() {
        testCourseList = new UbcCourses();
        super.setup();
    }

}



