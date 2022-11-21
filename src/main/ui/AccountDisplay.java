package ui;

import model.SetOfUsers;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AccountDisplay extends JPanel implements ActionListener {
    private static final String JSON_STORAGE = "./data/users.json";
    private static SetOfUsers users;
    private static final String CURRENT_COURSES = "view current courses";
    private static final String PAST_COURSES = "view past courses";
    private static final String ADD_COURSES = "add courses";
    private static final String LOG_OUT = "Log Out";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static JFrame currentFrame;

    public AccountDisplay() {
        currentFrame = LoginDisplay.currentFrame;
        currentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jsonReader = new JsonReader(JSON_STORAGE);
        jsonWriter = new JsonWriter(JSON_STORAGE);
        loadUsers();

        JComponent buttonPanel = displayOptions();
        add(buttonPanel);

        ImageIcon icon = new ImageIcon("./data/download.jpg");
        JLabel label = new JLabel(icon);
        add(label);

    }

    public static void boot() {
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        final AccountDisplay accountContentPane = new AccountDisplay();
        currentFrame.getContentPane().removeAll();
        currentFrame.getContentPane().add(accountContentPane);
        currentFrame.setPreferredSize(new Dimension(400, 600));
        currentFrame.setTitle("live laugh love");
        currentFrame.revalidate();
        currentFrame.repaint();

        currentFrame.pack();
        currentFrame.setVisible(true);
    }

    private void loadUsers() {
        try {
            users = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    private JComponent displayOptions() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton currentCourses = new JButton("View Current Courses");
        JButton pastCourses = new JButton("View Past Courses");
        JButton addCourses = new JButton("Add New Courses");
        JButton logOut = new JButton("Log Out");

        currentCourses.setActionCommand(CURRENT_COURSES);
        pastCourses.setActionCommand(PAST_COURSES);
        addCourses.setActionCommand(ADD_COURSES);
        logOut.setActionCommand(LOG_OUT);

        currentCourses.addActionListener(this);
        pastCourses.addActionListener(this);
        addCourses.addActionListener(this);
        logOut.addActionListener(this);

        p.add(currentCourses);
        p.add(pastCourses);
        p.add(addCourses);
        p.add(logOut);

        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (CURRENT_COURSES.equals(cmd)) {
            CurrentCoursesWindow currentCoursesWindow = new CurrentCoursesWindow();
        } else if (PAST_COURSES.equals(cmd)) {
            //
        } else if (ADD_COURSES.equals(cmd)) {
            AddCourseWindow addcoursewindow = new AddCourseWindow();
        } else if (LOG_OUT.equals(cmd)) {
            saveDataOption();
            closeWindow();
            LoginDisplay.boot();

        }

    }

    public void saveDataOption() {
        ImageIcon smiley = new ImageIcon("smile.png");
        int loadData = JOptionPane.showOptionDialog(null, "Save your courses?",
                "Save Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                smiley,
                null, 0);
        if (loadData == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(users);
                jsonWriter.close();
                JOptionPane.showMessageDialog(currentFrame,
                        "Courses have been saved :)");
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORAGE);
                JOptionPane.showMessageDialog(currentFrame,
                        "unable to save file");
            }
        }
    }

    public void closeWindow() {
        setVisible(false);
        currentFrame.dispose();
    }

}
