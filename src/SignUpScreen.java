import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpScreen extends JFrame {
    private JTextField usernameField, ageField, heightField, weightField;
    private JPasswordField passwordField;
    private JComboBox<String> sexBox;
    private JButton signUpButton;
    private Application application;

    public SignUpScreen(Application application){
        this.application = application;
        setTitle("Sign up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360,640);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JLabel sexLabel = new JLabel("Sex:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(sexLabel, gbc);

        sexBox = new JComboBox<>(new String[]{"Male","Female"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(sexBox,gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(ageLabel, gbc);

        ageField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(ageField, gbc);

        JLabel heightLabel = new JLabel("Height (cm):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(heightLabel, gbc);

        heightField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(heightField, gbc);

        JLabel weightLabel = new JLabel("Weight (kg):");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(weightLabel, gbc);

        weightField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(weightField, gbc);

        signUpButton = new JButton("Sign Up");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
        setLocationRelativeTo(null);
    }
    private void handleSignUp(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String sex = (String) sexBox.getSelectedItem();
        int age = Integer.parseInt(ageField.getText());
        int height = Integer.parseInt(heightField.getText());
        int weight = Integer.parseInt(weightField.getText());

        if(!application.getConnector().userExists(username)){
            application.getConnector().registerUser(username,password,sex,age,height,weight);
            JOptionPane.showMessageDialog(this,"Signup succesful, Welcome");
            dispose();
            new LoginScreenGUI(application).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this,
                    "Username already exists. " + "Please choose another");
        }
    }
}
