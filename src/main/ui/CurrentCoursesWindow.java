package ui;

import model.Course;
import model.SetOfUsers;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
// CITATION: JOptionPane code modelled from java tutorial
// https://youtu.be/BuW7y21FcYI

public class CurrentCoursesWindow extends JPanel implements ActionListener {
    JFrame frame;
    JLabel label = new JLabel("[CURRENT COURSES]", SwingConstants.CENTER);
    DefaultListModel<String> courseList;
    JPanel panel;
    JList<String> list;
    JsonReader jsonReader;
    JsonWriter jsonWriter;
    private static final String JSON_STORAGE = "./data/users.json";
    ListSelectionModel listselectionModel;
    private static final String END_COURSE = "end a course";
    User user;
    //SetOfUsers users;


    public CurrentCoursesWindow() {
        user = LoginDisplay.currentUser;
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        panel = new JPanel();
        //jsonReader = new JsonReader(JSON_STORAGE);
        //jsonWriter = new JsonWriter(JSON_STORAGE);
        //loadUsers();

        JComponent buttonPanel = displayOptions();

        init();



        panel.add(label);
        panel.add(list);
        frame.add(panel);
        frame.add(buttonPanel);
        /*frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });*/

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(440, 420);
        frame.setVisible(true);
        panel.setVisible(true);

    }

    public void init() {
        setupLabel();
        setupCourses();
        setupList();
    }

    public void setupLabel() {
        label.setBounds(0, 0, 500,
                50);
        label.setFont(new Font(null, Font.CENTER_BASELINE, 20));
        label.setOpaque(true);
        label.setForeground(Color.YELLOW);
        label.setBackground(Color.black);
    }

    public void setupList() {
        courseList = new DefaultListModel<>();
        addCourses();

        list = new JList(courseList);

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        selectACourse();

    }

    public JComponent displayOptions() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton endCourse = new JButton("End a Course");
        endCourse.setActionCommand(END_COURSE);

        endCourse.addActionListener(this);

        p.add(endCourse);

        return p;
    }

    public void setupCourses() {
        user.getCurrentCourses().getCourses();
    }

    public void addCourses() {
        for (int i = 0; i < user.getCurrentCourses().getCourses().size(); i++) {
            String courseName = user.getCurrentCourses().getCourses().get(i).getCourseName();
            courseList.addElement(courseName);
        }

    }

    public void selectACourse() {
        listselectionModel = list.getSelectionModel();
        listselectionModel.addListSelectionListener(
                e -> {
                    String courseName = (String) list.getSelectedValue();
                    Course course = findCourseWithName(courseName);
                    //int index = user.getCurrentCourses().getCourses().indexOf(course);
                    JOptionPane.showMessageDialog(null, "Academic Goal: "
                                    + user.getGradeGoal(course),
                            "info for " + courseName, JOptionPane.INFORMATION_MESSAGE);
                });
    }


    //EFFECTS: finds the course with corresponding course name
    public Course findCourseWithName(String courseName) {
        for (Course c : user.getCurrentCourses().getCourses()) {
            if (c.getCourseName().equals(courseName)) {
                return c;
            }
        }
        return null;
    }
    /*
    public void loadUsers() {
        try {
            users = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    public void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(users);
            jsonWriter.close();
            System.out.println(users.getUsers());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORAGE);
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent a) {
        String cmd = a.getActionCommand();

        if (END_COURSE.equals(cmd)) {

            String courseName = list.getSelectedValue();
            Course course = findCourseWithName(courseName);
            int endCourse = JOptionPane.showConfirmDialog(null, "End this course?",
                    "END " + courseName, JOptionPane.YES_NO_OPTION);
            if (endCourse == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Course tracking over! "
                                + "Congrats :)",
                        courseName + " over!", JOptionPane.INFORMATION_MESSAGE);
                user.removeFromCurrent(course);
                int index = user.getCurrentCourses().getCourses().indexOf(course);
                user.removeGradeGoal(index);
                user.addToPast(course);
            }

        }

    }
    //listselectionModel = list.getSelectionModel();
            /*listselectionModel.addListSelectionListener(
                    e -> {
                        String courseName = (String) list.getSelectedValue();
                        Course course = findCourseWithName(courseName);
                        int endCourse = JOptionPane.showConfirmDialog(null, "End this course?",
                                "END " + courseName, JOptionPane.YES_NO_OPTION);
                        if (endCourse == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Course tracking over! "
                                            + "Congrats :)",
                                    courseName + " over!", JOptionPane.INFORMATION_MESSAGE);
                            user.removeFromCurrent(course);
                            int index = user.getCurrentCourses().getCourses().indexOf(course);
                            user.removeGradeGoal(index);
                            user.addToPast(course);
                        }

                    });*/
}
