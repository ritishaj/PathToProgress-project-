package ui;


import model.*;

import java.util.List;
import java.util.Scanner;

// path to progress application
public class PathToProgress {
    private Scanner myObj;
    private ListOfCourses courses;
    private SetOfUsers users;
    private UbcCourses ubcCourses;

    // EFFECTS: runs the path to progress application
    public PathToProgress() {
        runPathToProgress();
    }

    // MODIFIES:this
    // EFFECTS: processes user input
    private void runPathToProgress() {
        init();
        System.out.println("Would you like to login or create an account? Enter as login or create");
        String choice = myObj.nextLine();
        if (choice.equals("login")) {
            logIn();
        } else if (choice.equals("create")) {
            createChoice();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes users and institution courses
    private void init() {
        users = new SetOfUsers();
        User rits = new User("ROTITTY", "ritisha", "jhamb");
        users.addUser(rits);
        ubcCourses = new UbcCourses();
        Course cpsc110 = new Course("CPSC110");
        Course phil220 = new Course("PHIL220");
        Course ling100 = new Course("LING100");
        Course math100 = new Course("MATH100");
        Course cpsc210 = new Course("CPSC210");
        Course psyc101 = new Course("PSYC101");
        Course eosc320 = new Course("EOSC320");
        Course astr111 = new Course("ASTR111");
        ubcCourses.addCourse(cpsc110);
        ubcCourses.addCourse(phil220);
        ubcCourses.addCourse(ling100);
        ubcCourses.addCourse(math100);
        ubcCourses.addCourse(cpsc210);
        ubcCourses.addCourse(psyc101);
        ubcCourses.addCourse(eosc320);
        ubcCourses.addCourse(astr111);


        myObj = new Scanner(System.in);
    }

    // EFFECTS: prompts user to log in
    private void logIn() {
        System.out.println("Enter your username: ");
        String username = myObj.nextLine();
        System.out.println("Enter your password: ");
        String password = myObj.nextLine();
        User inputUser = new User(username, password);
        if (users.validateUser(inputUser)) {
            System.out.println("Welcome, " + users.getNameFromLogin(inputUser) + "!");
            loggedInDisplay(users.getAUser(inputUser));
        } else {
            System.out.println("Incorrect details. Try again :( ");
            logIn();
        }
    }

    // EFFECTS: prompts user to create an account
    private void createChoice() {
        System.out.println("Name:");
        String name = myObj.nextLine();
        System.out.println("Add username(Must have at least 5 characters)");
        String username = myObj.nextLine();
        if (validateUsername(username)) {
            System.out.println("Create password (must have at least one number " + "and be 8 characters long):");
            String password = myObj.nextLine();
            validatePassword(name, username, password);
        } else {
            System.out.println("Username not valid. Must have at least 5 characters");
            createChoice();
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if password fulfills all requirements:
    //          must have at least one and be 8 characters long
    private void validatePassword(String name, String username, String password) {
        char[] ch = new char[password.length()];
        for (int i = 0; i < password.length(); i++) {
            ch[i] = password.charAt(i);
        }
        boolean contains = false;
        for (char c : ch) {
            if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6'
                    || c == '7' || c == '8' || c == '9') {
                contains = true;
                break;
            }
        }
        if (!contains) {
            System.out.println("Invalid password. Must have at least one number and 8 characters");
            createChoice();
        } else if (password.length() >= 8) {
            System.out.println("Creation successful!");
            createUser(name, username, password);
            logIn();
        } else {
            System.out.println("Invalid password. Must be at least 8 characters");
            createChoice();
        }
    }

    // EFFECTS: checks if username fulfills all requirements:
    //          must be at least 5 characters
    private boolean validateUsername(String username) {
        return username.length() >= 5;
    }

    // MODIFIES: this
    // EFFECTS: creates a new user with given name, username and password
    private void createUser(String name, String username, String password) {
        User user = new User(name, username, password);
        users.addUser(user);
    }

    // MODIFIES: this
    // EFFECTS: displays menu of options to user and processes the commands
    private void loggedInDisplay(User user) {
        System.out.println("\nSelect from:");
        System.out.println("\tC -> CURRENT COURSES");
        System.out.println("\tP -> PAST COURSES");
        System.out.println("\tA -> ADD NEW COURSES");
        System.out.println("\tO -> LOG OUT");
        String choice = myObj.nextLine();
        if (choice.equals("C")) {
            viewCurrentCourses(user);
        } else if (choice.equals("P")) {
            viewPastCourses(user);
        } else if (choice.equals("A")) {
            addNewCourses(user);
        } else if (choice.equals("O")) {
            System.out.println("-logged out-");
            return;
        }
    }


    // EFFECTS: displays all the current courses of user
    private void viewCurrentCourses(User user) {
        System.out.println("Current Courses:");
        for (Course u : user.getCurrentCourses().getCourses()) {
            System.out.println(u.getCourseName());
            System.out.println("Goal:" + user.getGradeGoal(u));
        }
        reviewCourseDisplay(user);
    }

    // EFFECTS: displays all the past courses of user
    private void viewPastCourses(User user) {
        System.out.println("Past Courses:");
        for (Course u : user.getPastCourses().getCourses()) {
            System.out.println(u.getCourseName());
            System.out.println(user.getCourseAverage(u));
        }
        loggedInDisplay(user);
    }

    // MODIFIES: this
    // EFFECTS: displays all ubc courses and prompts user to add a course
    private void addNewCourses(User user) {
        System.out.println("Add a course:");
        for (Course u : ubcCourses.getCourses()) {
            System.out.println(u.getCourseName());
        }
        String courseSelect = myObj.nextLine();
        for (Course u : ubcCourses.getCourses()) {
            if (u.getCourseName().equals(courseSelect)) {
                if (user.addToCurrent(u)) {
                    System.out.println("set academic goal:");
                    Integer gradeGoal = Integer.valueOf(myObj.nextLine());
                    user.addGradeGoal(gradeGoal);
                }
            }
        }
        currentCourseDisplay(user);
    }

    // MODIFIES: this
    // EFFECTS: displays current courses and prompts user to remove a course from their list
    private void removeCourses(User user) {
        System.out.println("Which course would you like to remove?");
        for (Course u : user.getCurrentCourses().getCourses()) {
            System.out.println(u.getCourseName());
        }
        String courseSelect = myObj.nextLine();
        for (Course u : user.getCurrentCourses().getCourses()) {
            if (u.getCourseName().equals(courseSelect)) {
                user.removeFromCurrent(u);
                System.out.println("course removed successfully");
            }
        }
        currentCourseDisplay(user);
    }

    // MODIFIES: this
    // EFFECTS: displays current courses and prompts user to end a course
    private void endCourses(User user) {
        System.out.println("Choose a course to end:");
        for (Course u : user.getCurrentCourses().getCourses()) {
            System.out.println(u.getCourseName());
        }
        String courseEnd = myObj.nextLine();
        List<Course> currentCourses = user.getCurrentCourses().getCourses();
        for (int i = 0; i < currentCourses.size(); i++) {
            Course u = currentCourses.get(i);
            if (u.getCourseName().equals(courseEnd)) {
                System.out.println("course tracking over! " + "Academic goal: "
                        + user.getGradeGoals().get(i) + " Total grade: " + user.getCourseAverage(u));
                if (user.getCourseAverage(u) >= user.getGradeGoals().get(i)) {
                    System.out.println("Goal achieved :)  CONGRATULATIONS!");
                    user.removeFromCurrent(u);
                    user.addToPast(u);
                }
            }
        }
        currentCourseDisplay(user);
    }

    // MODIFIES: this
    // EFFECTS: displays menu of options to user and processes the user command
    private void currentCourseDisplay(User user) {
        System.out.println("\nSelect from:");
        System.out.println("\tC -> REVIEW CURRENT COURSES");
        System.out.println("\tA -> ADD MORE COURSES");
        System.out.println("\tR -> REMOVE COURSES");
        System.out.println("\tE -> END COURSES");
        System.out.println("\tB -> GO BACK");
        String choice = myObj.nextLine();
        if (choice.equals("R")) {
            removeCourses(user);
        } else if (choice.equals("E")) {
            endCourses(user);
        } else if (choice.equals("B")) {
            loggedInDisplay(user);
        } else if (choice.equals("C")) {
            viewCurrentCourses(user);
        } else if (choice.equals("A")) {
            addNewCourses(user);
        }
    }

    private void reviewCourseDisplay(User user) {
        System.out.println("\nSelect from:");
        System.out.println("\tE -> EDIT COURSES");
        System.out.println("\tB -> GO BACK");
        String choice = myObj.nextLine();
        if (choice.equals("B")) {
            currentCourseDisplay(user);
        }
        if (choice.equals("E")) {
            editCurrentCourses(user);
        }

    }

    private void editCurrentCourses(User user) {
        System.out.println("Choose a course to edit:");
        for (Course u : user.getCurrentCourses().getCourses()) {
            System.out.println(u.getCourseName());
        }
        String courseSelect = myObj.nextLine();
        List<Course> currentCourses = user.getCurrentCourses().getCourses();
        for (Course u : currentCourses) {
            if (u.getCourseName().equals(courseSelect)) {
                editCourseDisplay(user, u);
            }
        }
        reviewCourseDisplay(user);
    }

    private void editCourseDisplay(User user, Course courseSelected) {
        System.out.println("\nSelect from:");
        System.out.println("\tA -> VIEW ASSESSMENTS");
        System.out.println("\tC -> CALCULATE AVERAGE");
        System.out.println("\tG -> CHANGE ACADEMIC GOAL");
        System.out.println("\tB -> GO BACK");
        String choice = myObj.nextLine();
        if (choice.equals("B")) {
            reviewCourseDisplay(user);
        }
        if (choice.equals("A")) {
            viewAssessmentDisplay(user, courseSelected);
        }
        if (choice.equals("C")) {
            calculateAverage(user, courseSelected);
        }
        if (choice.equals("G")) {
            changeGradeGoal(user, courseSelected);
        }
    }

    private void changeGradeGoal(User user, Course courseSelected) {
        for (Course u : user.getCurrentCourses().getCourses()) {
            if (u.getCourseName().equals(courseSelected.getCourseName())) {
                System.out.println("Put in desired number:");
                Integer newGradeGoal = Integer.valueOf(myObj.nextLine());
                int index = user.getCurrentCourses().getCourses().indexOf(courseSelected);
                user.getGradeGoals().set(index, newGradeGoal);
                System.out.println("GOAL is now " + newGradeGoal);
            }

        }
    }

    private void calculateAverage(User user, Course courseSelected) {
        System.out.println(user.getCourseAverage(courseSelected));
    }

    private void viewAssessmentDisplay(User user, Course courseSelected) {
        for (Course u : user.getCurrentCourses().getCourses()) {
            if (u.getCourseName().equals(courseSelected.getCourseName())) {
                for (Assessment ass : u.getAllAssessments()) {
                    System.out.println("Name: " + ass.getName());
                    System.out.println("Weight: " + ass.getWeight());
                    if (ass.getGrade() < 0) {
                        System.out.println("Grade: needs to be added");
                    } else {
                        System.out.println("Grade: " + ass.getGrade());
                    }
                }
            }

        }
        editAssessmentDisplay(user, courseSelected);
    }

    private void editAssessmentDisplay(User user, Course courseSelected) {
        System.out.println("\nSelect from:");
        System.out.println("\tA -> ADD ASSESSMENTS");
        System.out.println("\tR -> REMOVE ASSESSMENTS");
        System.out.println("\tG -> ADD GRADES");
        System.out.println("\tB -> GO BACK");
        String choice = myObj.nextLine();
        if (choice.equals("A")) {
            addAssessment(user, courseSelected);
        }
        if (choice.equals("R")) {
            removeAssessment(user, courseSelected);
        }
        if (choice.equals("G")) {
            addAssessmentGrades(user, courseSelected);
        }
        if (choice.equals("B")) {
            editCourseDisplay(user, courseSelected);
        }

    }

    private void addAssessmentGrades(User user, Course courseSelected) {
        System.out.println("choose assessment:");
        for (Assessment ass : courseSelected.getAllAssessments()) {
            System.out.println(ass.getName());
        }
        String assessmentSelect = myObj.nextLine();
        List<Assessment> assessmentList = courseSelected.getAllAssessments();
        for (Assessment ass : assessmentList) {
            if (ass.getName().equals(assessmentSelect)) {
                System.out.println("edit grade:");
                Integer assessmentGrade = Integer.valueOf(myObj.nextLine());
                ass.setGrade(assessmentGrade);
            }
        }
        editAssessmentDisplay(user, courseSelected);

    }

    private void addAssessment(User user, Course courseSelected) {
        System.out.println("give it a funky name:");
        String nameChoice = myObj.nextLine();
        System.out.println("weightage:");
        Integer weightChoice = Integer.valueOf(myObj.nextLine());
        courseSelected.addAssessment(new Assessment(nameChoice, weightChoice));
        System.out.println(nameChoice + " added!");
        editAssessmentDisplay(user, courseSelected);
    }

    private void removeAssessment(User user, Course courseSelected) {
        for (Assessment ass : courseSelected.getAllAssessments()) {
            System.out.println(ass.getName());
        }
        String deleteChoice = myObj.nextLine();
        List<Assessment> assessments = courseSelected.getAllAssessments();
        for (int i = 0; i < assessments.size(); i++) {
            Assessment a = assessments.get(i);
            if (a.getName().equals(deleteChoice)) {
                assessments.remove(a);
            }
        }
        editAssessmentDisplay(user, courseSelected);
    }
}

