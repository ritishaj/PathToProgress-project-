package model;

import java.util.ArrayList;
import java.util.List;


public class ListOfCourses {
    List<Course> courses;

    //EFFECTS: creates a new list of courses
    public ListOfCourses() {
        courses = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS:  inserts course into set unless it's already there, in which case do nothing
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    // MODIFIES: this
    // EFFECTS:  if the course is in the list, then remove it, otherwise do nothing
    public void removeCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
        }
    }

    // EFFECTS:  return true if a course is in the list of courses, false if not
    public boolean doesContain(Course course) {
        return courses.contains(course);

    }

    // EFFECTS: returns number of courses in the list of courses
    public int numOfCourses() {
        return courses.size();
    }

    // getters
    public List<Course> getCourses() {
        return courses;
    }


}
