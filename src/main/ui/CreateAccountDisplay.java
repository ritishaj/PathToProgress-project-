package ui;

import model.SetOfUsers;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateAccountDisplay extends JPanel implements ActionListener {
    private static String OK = "CREATE!";
    protected static JFrame currentFrame;
    private JTextField username;
    private JTextField name;
    private JPasswordField password;
    private static final String JSON_STORAGE = "./data/users.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    private static User loginUser;
    private static SetOfUsers users;

    // MODIFIES: LoginDisplay, this
    // EFFECTS: creates elements and sets up create account display
    public CreateAccountDisplay() {
        currentFrame = LoginDisplay.currentFrame;
        currentFrame.setPreferredSize(new Dimension(1200, 200));

        jsonReader = new JsonReader(JSON_STORAGE);
        jsonWriter = new JsonWriter(JSON_STORAGE);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        name = new JTextField(15);
        username = new JTextField(15);
        password = new JPasswordField(15);

        password.setActionCommand(OK);
        password.addActionListener(this);


        setupText();
    }

    // MODIFIES: this
    // EFFECTS: sets up name, username and password text field with their labels
    public void setupText() {
        JLabel nameLabel = new JLabel("Enter the name:");
        nameLabel.setLabelFor(name);

        JLabel usernameLabel = new JLabel("Enter the username:");
        usernameLabel.setLabelFor(username);

        JLabel passwordLabel = new JLabel("Enter the password: ");
        passwordLabel.setLabelFor(password);

        JComponent buttonPane = createButtonPanel();

        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(nameLabel);
        textPane.add(name);
        textPane.add(usernameLabel);
        textPane.add(username);
        textPane.add(passwordLabel);
        textPane.add(password);

        add(textPane);
        add(buttonPane);
    }

    // MODIFIES: this
    // EFFECTS: creates button panel and adds it to the JFrame
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton okButton = new JButton("CREATE!");


        okButton.setActionCommand(OK);

        okButton.addActionListener(this);

        p.add(okButton);

        return p;

    }

    // EFFECTS: sets up the action event when a certain button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (OK.equals(cmd)) { //Process the password.
            loadUsers();
            String nameInput = this.name.getText();
            String usernameInput = this.username.getText();
            char[] passwordInput = this.password.getPassword();
            loginUser = new User(nameInput, usernameInput, String.valueOf(passwordInput));
            try {
                jsonWriter.open();
                jsonWriter.write(users);
                jsonWriter.close();
                LoginDisplay.users.addUser(loginUser);
            } catch (FileNotFoundException f) {
                System.out.println("Unable to write to file: " + JSON_STORAGE);
                JOptionPane.showMessageDialog(currentFrame,
                        "unable to save file");
            }
            JOptionPane.showMessageDialog(currentFrame, "Account Created! Welcome "
                    + nameInput + "!");
            // closeWindow();
            AccountDisplay.boot();
        }

//            //Zero out the possible password, for security.
//            Arrays.fill(passwordInput, '0');
//            this.password.selectAll();
//            resetFocus();
    }


    // MODIFIES: LoginDisplay, this
    // EFFECTS: removes elements from login display frame and replaces with new panel
    private static void createAndShowGUI() {
        //JFrame frame = new JFrame("Create New Account:");

        final CreateAccountDisplay createaccountContentPane = new CreateAccountDisplay();
        currentFrame.getContentPane().removeAll();
        currentFrame.getContentPane().add(createaccountContentPane);
        currentFrame.setPreferredSize(new Dimension(1200, 200));
        currentFrame.setTitle("Create New Account:");
        currentFrame.revalidate();
        currentFrame.repaint();

        currentFrame.setVisible(true);
    }

    // EFFECTS: starts up GUI class
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

    // EFFECTS: loads all user files from JSON
    private void loadUsers() {
        try {
            users = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

}
