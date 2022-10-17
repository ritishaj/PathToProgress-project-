package model;

import java.util.ArrayList;
import java.util.List;

public class UbcCourses extends ListOfCourses {
    List<Course> ubcCourses;


    //EFFECTS: creates a new list of courses offered by UBC
    public UbcCourses() {
        super();
        ubcCourses = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds a course if ubc courses does not contain it already, otherwise do nothing
    @Override
    public void addCourse(Course course) {
        if (!(ubcCourses.contains(course))) {
            ubcCourses.add(course);
        }
    }

    // getters
    @Override
    public List<Course> getCourses() {
        return ubcCourses;
    }


}
