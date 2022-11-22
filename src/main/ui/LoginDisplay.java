package ui;

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
import java.io.IOException;
import java.util.Arrays;

// CITATION: code is modelled from the PasswordDemo tutorial
// https://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
public class LoginDisplay extends JPanel implements ActionListener {
    private static String OK = "OK";
    private static String CREATE_ACCOUNT = "Don't have an account?";
    protected static JFrame currentFrame;
    private JTextField username;
    private JPasswordField password;
    private static final String JSON_STORAGE = "./data/users.json";
    //private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    protected static User currentUser;
    protected static SetOfUsers users;

    public LoginDisplay(JFrame f) {
        currentFrame = f;
        currentFrame.setPreferredSize(new Dimension(700, 200));

        jsonReader = new JsonReader(JSON_STORAGE);
        //jsonWriter = new JsonWriter(JSON_STORAGE);
        //loadUsers();

        username = new JTextField(15);
        password = new JPasswordField(15);
        password.setActionCommand(OK);
        password.addActionListener(this);

        setupText();
    }


    public void setupText() {
        JLabel usernameLabel = new JLabel("Enter the username:");
        usernameLabel.setLabelFor(username);

        JLabel passwordLabel = new JLabel("Enter the password: ");
        passwordLabel.setLabelFor(password);

        JComponent buttonPane = createButtonPanel();

        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel base = new JPanel(new BorderLayout());
        base.add(usernameLabel, BorderLayout.LINE_START);
        base.add(username, BorderLayout.LINE_END);
        textPane.add(base, BorderLayout.PAGE_START);

        JPanel height = new JPanel(new BorderLayout());
        height.add(passwordLabel, BorderLayout.LINE_START);
        height.add(password, BorderLayout.LINE_END);
        textPane.add(height, BorderLayout.CENTER);

        add(textPane);
        add(buttonPane);
    }

    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton okButton = new JButton("OK");
        JButton wrongPasswordButton = new JButton("Don't have an account?");


        okButton.setActionCommand(OK);
        wrongPasswordButton.setActionCommand(CREATE_ACCOUNT);

        okButton.addActionListener(this);
        wrongPasswordButton.addActionListener(this);

        p.add(okButton);
        p.add(wrongPasswordButton);

        return p;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (OK.equals(cmd)) { //Process the password.
            loadUsers();
            String usernameInput = this.username.getText();
            char[] passwordInput = this.password.getPassword();
            if (isPasswordCorrect(usernameInput, passwordInput)) {
                currentUser = users.getAUser(new User(usernameInput, String.valueOf(passwordInput)));
               // System.out.println("user in set check 1" + LoginDisplay.users.getUsers().
                // contains(LoginDisplay.currentUser));
                JOptionPane.showMessageDialog(currentFrame, "Success! Welcome "
                        + this.users.getNameFromLogin(currentUser) + "!");
                // closeWindow();
                loadDataOption();
                AccountDisplay.boot();
            } else {
                JOptionPane.showMessageDialog(currentFrame,
                        "Invalid username or password. Try again.",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }

            Arrays.fill(passwordInput, '0');
            this.password.selectAll();
            resetFocus();
        } else if (cmd.equals(CREATE_ACCOUNT)) {
            CreateAccountDisplay.boot();
        }
    }


    protected void resetFocus() {
        password.requestFocusInWindow();
    }

    private static boolean isPasswordCorrect(String usernameInput, char[] passwordInput) {
        String password = String.valueOf(passwordInput);
        boolean userCorrect = users.validateUser(new User(usernameInput, password));

        return userCorrect;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane
        final LoginDisplay newContentPane = new LoginDisplay(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Make sure the focus goes to the right component
        //whenever the frame is initially given the focus.
        frame.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                newContentPane.resetFocus();
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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

    private void loadUsers() {
        try {
            users = jsonReader.read();
            System.out.println(users.getUsers());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    private void loadDataOption() {
        ImageIcon doggy = new ImageIcon("./data/tobs.jpg");
        Image image = doggy.getImage(); // transform it
        Image newimg = image.getScaledInstance(200, 120, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        doggy = new ImageIcon(newimg);

        int loadData = JOptionPane.showOptionDialog(null, "Load your saved courses?", "Load Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                doggy,
                null, 0);
        if (loadData == JOptionPane.YES_OPTION) {
            try {
                users = jsonReader.read();
                currentUser = users.getAUser(currentUser);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORAGE);
            }
        }
    }

    public void closeWindow() {
        setVisible(false);
        currentFrame.dispose();
    }

}




