import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutProgramGUI extends JFrame {

    private Application application;
    private DBConnector connector;
    private User currentUser;
    private JComboBox<String> exBox1, exBox2, exBox3, exBox4, exBox5;
    private JButton save, goBack;
    private JLabel nameLabel;
    private JTextField nameField;



    public CreateWorkoutProgramGUI(Application application){
        this.application = application;
        this.connector = application.getConnector();


        setTitle("Create Workout Program");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(360, 640);
        setLayout(new GridBagLayout());
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Workout Program Here");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title,gbc);

        //Name
        nameLabel = new JLabel("Workout name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nameLabel,gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nameField,gbc);

        /*
        List<Exercise> exercises = connector.getAllExercises();
        List<String> exerciseName = List.of();
        for(Exercise e: exercises) {
            exerciseName.add(e.getExerciseName());
        }
        String exerciseName1 = exercises.get(0).getExerciseName();
        String exerciseName2 = exercises.get(1).getExerciseName();
        String exerciseName3 = exercises.get(2).getExerciseName();
        String exerciseName4 = exercises.get(3).getExerciseName();
*/

        exBox1 = new JComboBox<>(new String[]{"Bench", "Squat", "DeadLift", "Bench"});
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(exBox1,gbc);

        exBox2 = new JComboBox<>(new String[]{"Bench", "Squat", "DeadLift", "Bench"});
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(exBox2,gbc);

        exBox3 = new JComboBox<>(new String[]{"Bench", "Squat", "DeadLift", "Bench"});
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(exBox3,gbc);

        exBox4 = new JComboBox<>(new String[]{"Bench", "Squat", "DeadLift", "Bench"});
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(exBox4,gbc);

        exBox5 = new JComboBox<>(new String[]{"Bench", "Squat", "DeadLift", "Bench"});
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(exBox5,gbc);


        //Save
        save = new JButton("Save Workout");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(save,gbc);


        //Go back
        goBack = new JButton("Go back");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(goBack,gbc);


        //Action listeners
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WorkoutProgramGUI(application).setVisible(true);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }

    //Methods
    private void handleSave(){
        String name = nameField.getText();
        String ex1 = (String) exBox1.getSelectedItem();
        String ex2 = (String) exBox2.getSelectedItem();
        String ex3 = (String) exBox3.getSelectedItem();
        String ex4 = (String) exBox4.getSelectedItem();
        String ex5 = (String) exBox5.getSelectedItem();

        application.getConnector().saveWorkoutProgramGUI(application.getCurrentUser(), name, ex1, ex2, ex3, ex4, ex5);

        JOptionPane.showMessageDialog(this,
                "Your workout has been saved");
    }

}
