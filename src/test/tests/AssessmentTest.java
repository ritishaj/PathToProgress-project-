package tests;

import model.Assessment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentTest {
    Assessment testAss;

    @BeforeEach
    public void setup(){
        testAss = new Assessment("Ling project", 0.8);
    }

    @Test
    public void testConstructor(){
        assertEquals("Ling project", testAss.getName());
        assertEquals(0.8, testAss.getWeight());

    }

    @Test
    public void setGrade(){
        testAss.setGrade(89);
        assertEquals(89.0, testAss.getGrade());
    }

    @Test
    public void toJsonTest(){
        testAss.toJson();
        assertEquals(0.8, testAss.toJson().getDouble("weight"));
        assertEquals("Ling project", testAss.toJson().getString("name"));
    }

}
