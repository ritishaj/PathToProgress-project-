package ui;

import model.Course;
import model.CurrentCourses;
import model.UbcCourses;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// CITATION: a list of displayed courses is modelled from to How to use a List Samples :
// https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// https://docs.oracle.com/javase/tutorial/uiswing/events/listselectionlistener.html

public class AddCourseWindow extends JPanel implements ActionListener {
    JFrame frame;
    JLabel label = new JLabel("[CHOOSE COURSES]", SwingConstants.CENTER);
    DefaultListModel<String> courseList;
    JPanel panel;
    JList list;
    ListSelectionModel listselectionModel;
    CurrentCourses currentCourses;
    User user;


    private UbcCourses ubcCourses;
    private static String value = "";

    public AddCourseWindow() {
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        panel = new JPanel();

        setupCourses();
        setupList();

        label.setBounds(0, 0, 500,
                50);
        label.setFont(new Font(null, Font.CENTER_BASELINE, 20));
        label.setOpaque(true);
        label.setBackground(Color.pink);

        panel.add(label);
        panel.add(list);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(440, 420);
        frame.setVisible(true);
        panel.setVisible(true);
        user = LoginDisplay.currentUser;

    }

    public void setupList() {
        courseList = new DefaultListModel<>();
        addCourses();

        list = new JList(courseList);

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        addACourse();

    }

    public void setupCourses() {
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

        currentCourses = new CurrentCourses();


    }

    public void addCourses() {
        for (int i = 0; i < ubcCourses.getCourses().size(); i++) {
            String courseName = ubcCourses.getCourses().get(i).getCourseName();
            courseList.addElement(courseName);
        }

    }

    public void addACourse() {
        listselectionModel = list.getSelectionModel();
        listselectionModel.addListSelectionListener(
                e -> {
                    String courseName = (String) list.getSelectedValue();
                    if (user.getCurrentCourses().getCourseNames().contains(courseName)) {
                        JOptionPane.showMessageDialog(frame, "course is already there bro");
                    } else {
                        int gg = Integer.parseInt(setAcademicGoal());
                        user.addToCurrent(findCourseWithName(courseName));
                        user.addGradeGoal(gg);
                        System.out.println("user in set check 2"
                                + LoginDisplay.users.getUsers().contains(LoginDisplay.currentUser));
                    }
                });

    }

    public String setAcademicGoal() {
        int g = -1;
        while (g < 0) {
            String inputGoal = JOptionPane.showInputDialog("Set an academic goal: ");
            if (inputGoal.length() > 0) {
                g++;
                JOptionPane.showMessageDialog(frame, "Goal Set!!");
                return inputGoal;
            } else {
                JOptionPane.showMessageDialog(frame, "enter a goal bro");

            }
        }
        return "";
    }

    //EFFECTS: finds the course with corresponding course name
    public Course findCourseWithName(String courseName) {
        for (Course c : ubcCourses.getCourses()) {
            if (c.getCourseName().equals(courseName)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }
}




