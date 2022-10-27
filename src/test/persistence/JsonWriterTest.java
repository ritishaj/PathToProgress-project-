package persistence;

import model.SetOfUsers;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// based on the supplied WorkRoom example for CPSC210:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest{
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
            sou.addUser(new User("needle", "needle1", "needle1234"));
            sou.addUser(new User("saw", "sawie", "sawie123"));
            sou.addUser(new User("suzie", "suzie", "suzie123"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUsers.json");
            writer.open();
            writer.write(sou);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUsers.json");
            sou = reader.read();
            Collection<User> users = sou.getUsers();
            assertEquals(3, users.size());
            checkUser("needle", "needle1","needle1234", sou.getAUser(new User("needle",
                    "needle1", "needle1234")));

            checkUser("saw", "sawie","sawie123", sou.getAUser(new User("saw",
                    "sawie", "sawie123")));

            checkUser("suzie", "suzie", "suzie123", sou.getAUser(new User("suzie",
                    "suzie", "suzie123")));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

