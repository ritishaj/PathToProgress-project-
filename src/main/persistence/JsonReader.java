package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
// based on the supplied WorkRoom example for CPSC210:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads sets of users from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SetOfUsers read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSetOfUsers(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses set of users from JSON object and returns it
    private SetOfUsers parseSetOfUsers(JSONObject jsonObject) {
        SetOfUsers sou = new SetOfUsers();
        addUsers(sou, jsonObject);
        return sou;
    }

    // MODIFIES: sou
    // EFFECTS: parses set of users from JSON object and adds them to set of users
    private void addUsers(SetOfUsers sou, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(sou, nextUser);
        }
    }

    // MODIFIES: sou
    // EFFECTS: parses user from JSON object and adds it to set of users
    private void addUser(SetOfUsers sou, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        PastCourses pastCourses = new PastCourses();
        addPastCourses(pastCourses, jsonObject);
        // JSONObject pastCourses = new JSONObject();
        // jsonObject.put("pastCourses", pastCourses);
        CurrentCourses currentCourses = new CurrentCourses();
        addCurrentCourses(currentCourses, jsonObject);
        //jsonObject.put("currentCourses", currentCourses);
        JSONArray gradeGoals = jsonObject.getJSONArray("gradeGoals");
        // jsonObject.put("gradeGoals", new ArrayList<Integer>());

        User user = new User(name, username, password);
        for (int i = 0; i < gradeGoals.length(); i++) {
            user.addGradeGoal(gradeGoals.getInt(i));
        }
        sou.addUser(user);
    }

    // EFFECTS:  parses courses from JSON object and adds them to past courses
    private void addPastCourses(PastCourses pc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pastCourses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addPastCourse(pc, nextCourse);
        }
    }

    // EFFECTS: parses course from JSON object and adds it to past courses
    private void addPastCourse(PastCourses pc, JSONObject jsonObject) {
        String courseName = jsonObject.getString("courseName");
        // JSONArray assessments = jsonObject.getJSONArray("assessments");
        // jsonObject.put("assessments", new ArrayList<Integer>());

        Course pastCourse = new Course(courseName);
        pc.addCourse(pastCourse);
        addAssessments(pastCourse, jsonObject);

    }

    // MODIFIES: CurrentCourses
    // EFFECTS:  parses courses from JSON object and adds them to current courses
    private void addCurrentCourses(CurrentCourses cc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("currentCourses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCurrentCourse(cc, nextCourse);
        }
    }

    // MODIFIES: CurrentCourses
    // EFFECTS: parses course from JSON object and adds it to current courses
    private void addCurrentCourse(CurrentCourses cc, JSONObject jsonObject) {
        String courseName = jsonObject.getString("courseName");
        //  JSONArray assessments = jsonObject.getJSONArray("assessments");
        jsonObject.put("assessments", new ArrayList<Integer>());

        Course currentCourse = new Course(courseName);
        cc.addCourse(currentCourse);
        addAssessments(currentCourse, jsonObject);

    }

    // MODIFIES: course
    // EFFECTS: parses assessments from JSON object and adds them to course
    private void addAssessments(Course c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("assessments");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addAssessment(c, nextThingy);
        }
    }

    // MODIFIES: course
    // EFFECTS: parses assessment from JSON object and adds it to course
    private void addAssessment(Course c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double weight = jsonObject.getDouble("weight");
        jsonObject.put("assessments", new ArrayList<Integer>());

        Assessment ass = new Assessment(name, weight);
        c.addAssessment(ass);

    }


}

