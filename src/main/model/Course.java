package model;

import exceptions.NoCompleteAssessmentException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Storable;

import java.util.ArrayList;
import java.util.List;

// Represents a course, having a course name
public class Course implements Storable {
    List<Assessment> assessments;
    private final String courseName;


    // REQUIRES: course name has a non-zero length
    // EFFECTS: courseName is set to name
    public Course(String name) {
        courseName = name;
        assessments = new ArrayList<>();
    }

    // REQUIRES: all max assessment weightage must equal to 100, no more
    // EFFECTS: adds an assessment to the list of assessments in course
    public void addAssessment(Assessment ass) {
        assessments.add(ass);

    }

    // EFFECTS: removes an assessment from the list of assessments in course
    public void removeAssessment(Assessment ass) {
        assessments.remove(ass);
    }

    // EFFECTS: checks to see if assessments have an inputted grade value
    //          if they do, they are added to a new list of complete assessments
    public List<Assessment> getCompleteAssessments() {
        List<Assessment> completeAss = new ArrayList<>();
        for (Assessment ass : assessments) {
            if (ass.getGrade() >= 0) {
                completeAss.add(ass);
            }
        }
        return completeAss;
    }


    // EFFECTS: takes all the grades and weights of complete assessments and returns average
    //          exception is thrown if there are no complete assessments in the course
    //          (complete = having a grade)
    public double getCourseAverage() throws NoCompleteAssessmentException {
        if (getCompleteAssessments().size() > 0) {
            float sum = 0;
            for (Assessment ass : assessments) {
                double calcWeight = (ass.getWeight() / 100);

                sum += (ass.getGrade() * calcWeight);
            }
            float average = sum / getCompleteAssessments().size();

            return average;
        } else {
            throw new NoCompleteAssessmentException();
        }
    }

    // EFFECTS: returns number of assessments
    public int numOfAssessments() {
        return assessments.size();
    }

    // EFFECTS: returns true if list of assessments contains ass, otherwise false
    public boolean containsAssessment(Assessment ass) {
        return assessments.contains(ass);
    }




    //getters
    public String getCourseName() {
        return courseName;
    }

    public List<Assessment> getAllAssessments() {
        return assessments;
    }


    //EFFECTS: returns Course as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseName", courseName);
        json.put("assessments", assessmentsToJson());
        return json;
    }

    // EFFECTS: returns users in the set of users as a JSON array
    // Method was taken from WorkRoom
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private JSONArray assessmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assessment a : assessments) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
