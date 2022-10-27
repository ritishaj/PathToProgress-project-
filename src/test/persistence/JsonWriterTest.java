package persistence;

import model.Course;
import model.SetOfUsers;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// based on the supplied WorkRoom example for CPSC210:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest{
    private User u1;
    private User u2;
    private User u3;
    private Course c1;
    private Course c2;
    private Course c3;


    @BeforeEach
    void setup(){
        u1 = new User("needle", "needle1", "needle1234");
        u2 = new User("saw", "sawie","sawie123");
        u3 = new User("suzie", "suzie", "suzie123");
        c1 = new Course("LING100");
        c2 = new Course("CPSC210");
        c3 = new Course("EOSC320");


    }
    @Test
    void testWriterInvalidFile() {
        try {
            SetOfUsers users= new SetOfUsers();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            SetOfUsers users= new SetOfUsers();
            JsonWriter writer = new JsonWriter("./data/testWriterNoUsers.json");
            writer.open();
            writer.write(users);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoUsers.json");
            users = reader.read();
            assertEquals(0, users.numOfUsers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            SetOfUsers sou = new SetOfUsers();
            sou.addUser(u1);
            sou.addUser(u2);
            sou.addUser(u3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUsers.json");
            writer.open();
            writer.write(sou);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUsers.json");
            sou = reader.read();
            assertEquals(3, sou.numOfUsers());
            checkUser("needle", "needle1","needle1234", u1);
            u1.addToCurrent(c1);
            u1.addToPast(c2);
            u1.addGradeGoal(88);
            assertEquals(1, u1. getCurrentCourses().numOfCourses());
            assertEquals(1, u1.getCurrentCourses().numOfCourses());
            assertEquals(1, u1.getGradeGoals().size());

            checkUser("saw", "sawie","sawie123", u2);
            u2.addToPast(c3);
            assertEquals(0, sou.getAUser(new User("saw", "sawie","sawie123")).
                    getCurrentCourses().numOfCourses());
            assertEquals(1,u2.getPastCourses().numOfCourses());
            assertEquals("EOSC320", u2.getPastCourses().getCourses().get(0).getCourseName());
            assertEquals(0, sou.getAUser(new User("saw", "sawie","sawie123")).
                    getGradeGoals().size());

            checkUser("suzie", "suzie", "suzie123", sou.getAUser(new User("suzie",
                    "suzie", "suzie123")));
            assertEquals(0, sou.getAUser(new User("suzie", "suzie", "suzie123")).
                    getCurrentCourses().numOfCourses());
            assertEquals(0, sou.getAUser(new User("suzie", "suzie", "suzie123")).
                    getPastCourses().numOfCourses());
            assertEquals(0, sou.getAUser(new User("suzie", "suzie", "suzie123")).
                    getGradeGoals().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

