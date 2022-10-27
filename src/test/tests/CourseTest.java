package tests;
import exceptions.AssessmentFullException;
import exceptions.NoCompleteAssessmentException;
import model.Assessment;
import model.Course;
import model.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


class CourseTest {
    private User u1;
    private Course testCourse;
    private Assessment a1;
    private Assessment a2;
    private Assessment a3;
    private Assessment a4;


    @BeforeEach
    public void setup(){
        testCourse = new Course("LING100");
        a1 = new Assessment("project", 20);
        a2 = new Assessment("midterm 1", 30);
        a3 = new Assessment("participation", 8);
        a4 = new Assessment("midterm 2", 90);
        u1 = new User("smith" , "smitty", "jackie123");
    }

    @Test
    public void testConstructor(){
        assertEquals("LING100", testCourse.getCourseName());
        assertEquals(0, testCourse.numOfAssessments());
    }

    @Test
    public void addAssessmentTest(){
        testCourse.addAssessment(a1);
        assertEquals(1, testCourse.getAllAssessments().size());
        assertTrue(testCourse.containsAssessment(a1));

        testCourse.addAssessment(a1);
        assertEquals(2, testCourse.getAllAssessments().size());
        assertTrue(testCourse.containsAssessment(a1));

        testCourse.addAssessment(a2);
        assertEquals(3, testCourse.getAllAssessments().size());
        assertTrue(testCourse.containsAssessment(a1));
    }

    @Test
    public void addAssessmentOnceAndRemoveTest(){
        testCourse.addAssessment(a1);
        assertEquals("project", testCourse.getAllAssessments().get(0).getName());
        assertTrue(testCourse.containsAssessment(a1));
        testCourse.removeAssessment(a1);
        assertEquals(0, testCourse.numOfAssessments());
        assertFalse(testCourse.containsAssessment(a1));
    }



    @Test
    public void getCourseAverageExpectNoCompleteAssessmentException(){
       u1.addToCurrent(testCourse);
        testCourse.addAssessment(a1);
        testCourse.addAssessment(a2);
        testCourse.addAssessment(a3);
        assertEquals(0, testCourse.getCompleteAssessments().size());

        try {
            testCourse.getCourseAverage();
            fail("exception should be thrown");
        } catch (NoCompleteAssessmentException e) {
        }

        assertEquals(-1.0, u1.getCourseAverage(testCourse));

    }

    @Test
    public void getCourseAverageExpectNoException() {
        u1.addToCurrent(testCourse);
        a1.setGrade(88);
        a2.setGrade(90);
        testCourse.addAssessment(a1);
        testCourse.addAssessment(a2);
        testCourse.addAssessment(a2);
        testCourse.addAssessment(a3);
        assertEquals(3, testCourse.getCompleteAssessments().size());

        double avg = 0;

        try {
            avg = testCourse.getCourseAverage();
        } catch (NoCompleteAssessmentException e) {
            fail("exception should not be thrown");
        }
        assertEquals(23.839998245239258, avg);

    }

    @Test
    public void toJsonTest(){
        testCourse.toJson();
        assertEquals("LING100", testCourse.toJson().getString("courseName"));
        testCourse.addAssessment(a1);
        testCourse.addAssessment(a2);
       assertEquals(2, testCourse.toJson().getJSONArray("assessments").length());
    }





}





