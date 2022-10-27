package tests;
import model.CurrentCourses;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class CurrentCoursesTest extends ListOfCoursesTest{
    @BeforeEach
    public void setup() {
        testCourseList = new CurrentCourses();
        super.setup();
    }


}
