package tests;

import model.Course;
import model.User;
import model.events.Event;
import model.events.EventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// class is based on EventLogTest in AlarmSystem:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class EventLogTest {
    private User user1;
    Course c1;
    Course c2;
    Course c3;

    @BeforeEach
    public void runBefore() {
        user1 = new User("bob", "bob", "bob");
        c1 = new Course("COGS200");
        c2 = new Course("MATH104");
        c3 = new Course("LING100");

    }


    @Test
    public void currentCourseAdded() {
        user1.addToCurrent(c1);
        user1.addGradeGoal(98);

        user1.addToCurrent(c2);
        user1.addGradeGoal(89);

        user1.addToCurrent(c3);
        user1.addGradeGoal(77);


        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }

        assertEquals(c1.getCourseName() +" added to current courses :(", l.get(0).getDescription());
        assertEquals("grade goal for COGS200: 98 added!", l.get(1).getDescription());

        assertEquals(c2.getCourseName() +" added to current courses :(", l.get(2).getDescription());
        assertEquals("grade goal for " + c2.getCourseName() + ": 89 added!", l.get(3).getDescription());

        assertEquals(c3.getCourseName() +" added to current courses :(", l.get(4).getDescription());
        assertEquals("grade goal for " + c3.getCourseName() + ": 77 added!", l.get(5).getDescription());

    }

    @Test
    public void endCurrentCourses() {

        user1.addToCurrent(c1);
        user1.addGradeGoal(98);
        user1.removeFromCurrent(c1);
        user1.addToPast(c1);


        List<Event> l = new ArrayList<Event>();

       EventLog el = EventLog.getInstance();

        for (Event next : el) {
            l.add(next);
        }

        assertEquals(c1.getCourseName() +" added to current courses :(", l.get(6).getDescription());
        assertEquals("grade goal for COGS200: 98 added!", l.get(7).getDescription());
        assertEquals(c1.getCourseName() +" removed!", l.get(8).getDescription());
        assertEquals(c1.getCourseName() +" added to past courses :)", l.get(9).getDescription());

    }
}
