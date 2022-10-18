package tests;
import model.CurrentCourses;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CurrentCoursesTest extends ListOfCoursesTest{
    @BeforeEach
    public void setup() {
        testCourseList = new CurrentCourses();
        super.setup();
    }


}
