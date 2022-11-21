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

    public CreateAccountDisplay() {
        currentFrame = LoginDisplay.currentFrame;
        currentFrame.setPreferredSize(new Dimension(700, 200));

        jsonReader = new JsonReader(JSON_STORAGE);
        jsonWriter = new JsonWriter(JSON_STORAGE);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        name = new JTextField(15);
        username = new JTextField(15);
        password = new JPasswordField(15);
        password.setActionCommand(OK);
        password.addActionListener(this);

        loadUsers();

        setupText();
    }


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

    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton okButton = new JButton("CREATE!");


        okButton.setActionCommand(OK);

        okButton.addActionListener(this);

        p.add(okButton);

        return p;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (OK.equals(cmd)) { //Process the password.
            String nameInput = this.name.getText();
            String usernameInput = this.username.getText();
            char[] passwordInput = this.password.getPassword();
            loginUser = new User(nameInput, usernameInput, String.valueOf(passwordInput));
            users.addUser(loginUser);
            try {
                jsonWriter.open();
                jsonWriter.write(users);
                jsonWriter.close();
            } catch (FileNotFoundException f) {
                System.out.println("Unable to write to file: " + JSON_STORAGE);
                JOptionPane.showMessageDialog(currentFrame,
                        "unable to save file");
            }
            JOptionPane.showMessageDialog(currentFrame, "Account Created! Welcome "
                    + this.users.getNameFromLogin(loginUser) + "!");
            // closeWindow();
            AccountDisplay.boot();
        }

//            //Zero out the possible password, for security.
//            Arrays.fill(passwordInput, '0');
//            this.password.selectAll();
//            resetFocus();
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
        //JFrame frame = new JFrame("Create New Account:");

        final CreateAccountDisplay createaccountContentPane = new CreateAccountDisplay();
        currentFrame.getContentPane().removeAll();
        currentFrame.getContentPane().add(createaccountContentPane);
        currentFrame.setPreferredSize(new Dimension(700, 200));
        currentFrame.setTitle("Create New Account:");
        currentFrame.revalidate();
        currentFrame.repaint();

        currentFrame.pack();
        currentFrame.setVisible(true);
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
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    public void closeWindow() {
        setVisible(false);
        currentFrame.dispose();
    }

}
