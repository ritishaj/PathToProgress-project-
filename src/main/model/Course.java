package model;

// Represents a course, having a course name
public class Course {
    private String courseName;

    // REQUIRES: accountName has a non-zero length
    // EFFECTS: courseName is set to name
    public Course(String name) {
        courseName = name;
    }


    //getters
    public String getCourseName() {
        return courseName;
    }

}
