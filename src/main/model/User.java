package model;

import exceptions.NoCompleteAssessmentException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Storable;

import java.util.ArrayList;
import java.util.List;

// Represents a USER having a name, username, password, past courses,
// current courses and a list of grade goals
public class User implements Storable {
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
        this.pastCourses = new PastCourses();
        this.currentCourses = new CurrentCourses();
        this.gradeGoals = new ArrayList<>();
    }

    // MODIFIES: CurrentCourses
    // EFFECTS: adds course to current courses if it is not there already and returns true,
    //          returns false otherwise
    public boolean addToCurrent(Course course) {
        if (!(currentCourses.doesContain(course))) {
            currentCourses.addCourse(course);
            System.out.println("course added");
            return true;
        } else {
            System.out.println("course is already there bro");
            return false;
        }
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

    // MODIFIES: this
    // EFFECTS: removes a grade goal
    public void removeGradeGoal(Integer goal) {
        gradeGoals.remove(goal);
    }

    //EFFECTS: takes index of course and returns the set grade goal for it
    public int getGradeGoal(Course course) {
        int index = currentCourses.getCourses().indexOf(course);
        return gradeGoals.get(index);
    }

    // EFFECTS: returns true if course average is computed,
    //          return false if NoCompleteException is caught or course is not in the system
    public double getCourseAverage(Course course) {
        double grade = -1.0;
        if (currentCourses.doesContain(course)) {
            try {
                grade = course.getCourseAverage();
            } catch (NoCompleteAssessmentException e) {
                System.out.println("need to add grades for assessments");
            }
        }
        return grade;
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

    public List<Integer> getGradeGoals() {
        return gradeGoals;
    }

    //EFFECTS: returns User as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("username", username);
        json.put("password", password);
        json.put("pastCourses", pastCoursesToJson());
        json.put("currentCourses", currentCoursesToJson());
        json.put("gradeGoals", gradeGoalsToJson());
        return json;
    }
    // EFFECTS: returns past courses in user as a JSON array
    // Method was taken from WorkRoom
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    private JSONArray pastCoursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course u : pastCourses.getCourses()) {
            jsonArray.put(u.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns grade goals in user as a JSON array
    // Method was taken from WorkRoom
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    private JSONArray gradeGoalsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Integer i : gradeGoals) {
            jsonArray.put(i);
        }

        return jsonArray;
    }

    // EFFECTS: returns current courses in user as a JSON array
    // Method was taken from WorkRoom
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    private JSONArray currentCoursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course u : currentCourses.getCourses()) {
            jsonArray.put(u.toJson());
        }

        return jsonArray;
    }



}
