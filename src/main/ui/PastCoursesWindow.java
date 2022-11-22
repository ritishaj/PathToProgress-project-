package ui;

import model.Course;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PastCoursesWindow extends JPanel implements ActionListener {
    JFrame frame;
    JLabel label = new JLabel("[PAST COURSES]", SwingConstants.CENTER);
    DefaultListModel<String> courseList;
    JPanel panel;
    JList list;
    ListSelectionModel listselectionModel;
    User user;


    public PastCoursesWindow() {
        user = LoginDisplay.currentUser;
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        panel = new JPanel();


        setupCourses();
        setupList();

        label.setBounds(0, 0, 500,
                50);
        label.setFont(new Font(null, Font.CENTER_BASELINE, 20));
        label.setOpaque(true);
        label.setForeground(Color.black);
        label.setBackground(Color.yellow);

        panel.add(label);
        panel.add(list);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(440, 420);
        frame.setVisible(true);
        panel.setVisible(true);

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


    public void setupCourses() {
        user.getPastCourses().getCourses();
    }

    public void addCourses() {
        for (int i = 0; i < user.getPastCourses().getCourses().size(); i++) {
            String courseName = user.getPastCourses().getCourses().get(i).getCourseName();
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
                    JOptionPane.showMessageDialog(null, "Course completed :)",
                            "info for " + courseName, JOptionPane.INFORMATION_MESSAGE);
                });
    }


    //EFFECTS: finds the course with corresponding course name
    public Course findCourseWithName(String courseName) {
        for (Course c : user.getPastCourses().getCourses()) {
            if (c.getCourseName().equals(courseName)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent a) {
//
    }
}

