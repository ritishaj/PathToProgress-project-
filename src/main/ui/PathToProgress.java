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

    private void createChoice() {
        System.out.println("Name:");
        String name = myObj.nextLine();
        System.out.println("Add username(Must have at least 5 characters)");
        String username = myObj.nextLine();
        if (validateUsername(username)) {
            System.out.println("Create password (must have at least one number "
                    + "and be 8 characters long):");
            String password = myObj.nextLine();
            validatePassword(name, username, password);
        } else {
            System.out.println("Username not valid. Must have at least 5 characters");
            createChoice();
        }
    }

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

    private boolean validateUsername(String username) {
        if (username.length() >= 5) {
            return true;
        } else {
            return false;
        }
    }

    private void createUser(String name, String username, String password) {
        User user = new User(name, username, password);
        users.addUser(user);
    }

    private void loggedInDisplay(User user) {
        System.out.println("choose CURRENT COURSES, PAST COURSES, ADD NEW COURSES, LOG OUT"
                + "(enter as C, P, A, O)");
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

    private void viewCurrentCourses(User user) {
        System.out.println("Current Courses:");
        for (Course u : user.getCurrentCourses().getCourses()) {
            System.out.println(u.getCourseName());
            System.out.println("Goal:" + user.getGradeGoal(u));
        }
        currentCourseDisplay(user);
    }

    private void viewPastCourses(User user) {
        System.out.println("Past Courses:");
        for (Course u : user.getPastCourses().getCourses()) {
            System.out.println(u.getCourseName());
        }
        loggedInDisplay(user);
    }

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

    private void removeCourses(User user) {
        System.out.println("Which course would you like to remove?");
        String courseSelect = myObj.nextLine();
        for (Course u : user.getCurrentCourses().getCourses()) {
            if (u.getCourseName().equals(courseSelect)) {
                user.removeFromCurrent(u);
                System.out.println("course removed successfully");
            }
        }
        currentCourseDisplay(user);
    }

    private void endCourses(User user) {
        System.out.println("Choose a course to end:");
        String courseEnd = myObj.nextLine();
        List<Course> currentCourses = user.getCurrentCourses().getCourses();
        for (int i = 0; i < currentCourses.size(); i++) {
            Course u = currentCourses.get(i);
            if (u.getCourseName().equals(courseEnd)) {
                user.removeFromCurrent(u);
                user.addToPast(u);
                System.out.println("course tracking over. CONGRATULATIONS!");
            }
        }
        currentCourseDisplay(user);
    }

    private void currentCourseDisplay(User user) {
        System.out.println("VIEW CURRENT COURSES, ADD MORE COURSES, REMOVE COURSES, END COURSES, GO BACK"
                + " (enter as C, A, R, E, B)");
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


}

