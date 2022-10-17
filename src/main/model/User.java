package model;

import java.util.ArrayList;
import java.util.List;

// Represents a USER having a name, username, password, past courses,
// current courses and a list of grade goals
public class User {
    private String name;
    private String username;
    private String password;
    private PastCourses pastCourses;
    private CurrentCourses currentCourses;
    private List<Integer> gradeGoals;


    //REQUIRES: name, username and password has a non-zero length
    //EFFECTS: sets this.name to name, this.username to username and this.password to password
    //         creates a new empty list for current courses and their grade goals, and past courses
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.pastCourses = new PastCourses();
        this.currentCourses = new CurrentCourses();
        this.gradeGoals = new ArrayList<>();
    }

    //REQUIRES: username and password has a non-zero length
    //EFFECTS: sets this.username to username and this.password to password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // MODIFIES: CurrentCourses
    // EFFECTS: adds course to current courses if it is not there already and returns true,
    //          returns false otherwise
    public boolean addToCurrent(Course course) {
        if (!(currentCourses.doesContain(course))) {
            currentCourses.addCourse(course);
            System.out.println("course added");
            return true;
        } else if (currentCourses.doesContain(course)) {
            System.out.println("course is already there bro");
            return false;
        }
        return false;
    }

    // MODIFIES: CurrentCourses
    // EFFECTS: remove course from current courses if it contains the course, otherwise do nothing
    public void removeFromCurrent(Course course) {
        if (currentCourses.doesContain(course)) {
            currentCourses.removeCourse(course);
        }
    }

    // MODIFIES: PastCourses
    // EFFECTS: adds a course to the list of past courses
    public void addToPast(Course course) {
        pastCourses.addCourse(course);
    }

    // REQUIRES: goal >= 50 & goal <=100
    // MODIFIES: this
    // EFFECTS: adds a grade goal
    public void addGradeGoal(Integer goal) {
        gradeGoals.add(goal);
    }

    //EFFECTS: takes index of course and returns the set grade goal for it
    public int getGradeGoal(Course course) {
        int index = currentCourses.getCourses().indexOf(course);
        return gradeGoals.get(index);
    }

    // getters
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public PastCourses getPastCourses() {
        return pastCourses;
    }

    public CurrentCourses getCurrentCourses() {
        return currentCourses;
    }

}
