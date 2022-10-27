package persistence;

import model.SetOfUsers;
import model.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.fail;

// based on the supplied WorkRoom example for CPSC210:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SetOfUsers users = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoUsers() {
        JsonReader reader = new JsonReader("./data/testReaderNoUsers.json");
        try {
            SetOfUsers users = reader.read();
            assertEquals(0, users.numOfUsers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUsers() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUsers.json");
        try {
            SetOfUsers sou = reader.read();
            Collection<User> users = sou.getUsers();
            assertEquals(3, users.size());
            checkUser("needle", "needle1","needle1234", sou.getAUser(new User("needle",
                    "needle1", "needle1234")));
            assertEquals(1, sou.getAUser(new User("needle", "needle1", "needle1234")).
                    getCurrentCourses().numOfCourses());
            assertEquals(1, sou.getAUser(new User("needle1", "needle1234")).
                    getPastCourses().numOfCourses());
            assertEquals(1, sou.getAUser(new User("needle1", "needle1234")).
                    getGradeGoals().size());

            checkUser("saw", "sawie","sawie123", sou.getAUser(new User("saw",
                    "sawie", "sawie123")));
            assertEquals(0, sou.getAUser(new User("saw", "sawie","sawie123")).
                    getCurrentCourses().numOfCourses());
            assertEquals(1, sou.getAUser(new User("saw", "sawie","sawie123")).
                    getPastCourses().numOfCourses());
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
            fail("Couldn't read from file");
        }
    }




}
