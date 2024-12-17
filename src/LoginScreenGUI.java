import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreenGUI extends JFrame{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;
    private Application application;

    public LoginScreenGUI(Application app){

        this.application = app;
        setTitle("Login screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360,640);
        setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Username text and field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel,gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField,gbc);


        //Password text and field
        JLabel passwordLabel = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel,gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField,gbc);


        //Login button
        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton,gbc);

        //Signup button
        signUpButton = new JButton("Sign up");
        signUpButton.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(signUpButton,gbc);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });


        setLocationRelativeTo(null);

    }

    //Methods
   private void handleLogin(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if(application.getConnector().validLogin(username,password)){
            application.setCurrentUser(application.getConnector().getUserData(username));
            dispose();
            new MainMenuGUI(application).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this,"Invalid Username or Password");
        }
    }

    private void handleSignUp() {
        dispose();
        new SignUpScreenGUI(application).setVisible(true);
    }
}

//2
