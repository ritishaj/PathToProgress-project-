package tests;
import model.PastCourses;
import org.junit.jupiter.api.BeforeEach;

public class PastCoursesTest extends ListOfCoursesTest{
    @BeforeEach
    public void setup() {
        testCourseList = new PastCourses();
        super.setup();
    }
}
