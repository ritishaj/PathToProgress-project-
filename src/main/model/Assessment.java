package model;

import org.json.JSONObject;
import persistence.Storable;

public class Assessment implements Storable {
    protected double weight;
    protected String name;
    protected int grade;


    // EFFECTS: creates a new assessment, sets this.name to name and this.weight to weight
    public Assessment(String name, double weight) {
        this.name = name;
        this.weight = weight;
        this.grade = -1;
    }

    // EFFECTS: creates a new assessment, sets this.name to name and this.weight to weight, and this.grade to grade
    public Assessment(String name, double weight, int grade) {
        this.name = name;
        this.weight = weight;
        this.grade = grade;
    }

    // MODIFIES: this
    // EFFECTS: sets this.grade to given grade
    public void setGrade(int grade) {
        this.grade = grade;
    }

    // getters
    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    //EFFECTS: returns Assessment as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("weight", weight);
        json.put("grade", grade);
        return json;
    }
}
