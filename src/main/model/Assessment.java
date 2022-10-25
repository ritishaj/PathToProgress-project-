package model;

public class Assessment {
    protected double weight;
    protected String name;
    protected int grade;


    // EFFECTS: creates a new assessment, sets this.name to name and this.weight to weight
    public Assessment(String name, double weight) {
        this.name = name;
        this.weight = weight;
        this.grade = -1;
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


}
