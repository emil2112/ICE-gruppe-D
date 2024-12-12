import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateExerciseGUI extends JFrame {
    private Application application;
    private JTextField nameField, setsField, repsField, weightField, restTimeField, muscleGroupField, idField;
    private JButton saveButton, goBack;
    private ExerciseCreator exerciseCreator;

    MainMenuGUI mainMenu = new MainMenuGUI(application);

    public CreateExerciseGUI(Application application) {
        this.application = application;
        setTitle("Create exercise");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(360, 640);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Create Exercise");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        //ExerciseName
        JLabel nameLabel = new JLabel("Exercise Name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(nameField, gbc);

        //Sets
        JLabel setsLabel = new JLabel("Sets: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(setsLabel, gbc);

        setsField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(setsField, gbc);


        //Reps
        JLabel repsLabel = new JLabel("Reps: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(repsLabel, gbc);

        repsField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(repsField, gbc);


        //Weight
        JLabel weightLabel = new JLabel("Weight: ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(weightLabel, gbc);

        weightField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(weightField, gbc);


        //restTime
        JLabel restLabel = new JLabel("Rest time (seconds): ");
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(restLabel, gbc);

        restTimeField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 10;
        add(restTimeField, gbc);


        //Muscle group
        JLabel muscleLabel = new JLabel("Muscle groups used: ");
        gbc.gridx = 0;
        gbc.gridy = 11;
        add(muscleLabel, gbc);

        muscleGroupField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 12;
        add(muscleGroupField, gbc);


        //ID (NOT FINAL)
        JLabel idLabel = new JLabel("Enter ID: ");
        gbc.gridx = 0;
        gbc.gridy = 13;
        add(idLabel,gbc);

        idField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 14;
        add(idField,gbc);

        //Save
        saveButton = new JButton("Save Exercise");
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        //Back
        goBack = new JButton("Go Back");
        gbc.gridx = 0;
        gbc.gridy = 16;
        add(goBack, gbc);


        //Action listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                handleSaveExercise();
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainMenu.handleWorkoutProgram();
            }
        });

        setLocationRelativeTo(null);
    }

    //Methods
    private void handleSaveExercise() {
        String name = nameField.getText();
        int sets = Integer.parseInt(setsField.getText());
        int reps = Integer.parseInt(repsField.getText());
        float weight = Float.parseFloat(weightField.getText());
        float rest = Float.parseFloat(restTimeField.getText());
        String muscle = muscleGroupField.getText();
        int id = Integer.parseInt(idField.getText());

        if (!name.isEmpty()) {
            try {
                Exercise exercise = new Exercise(id, name, sets, reps, weight, rest, muscle);

                application.getExerciseCreator().addExercise(exercise);

                JOptionPane.showMessageDialog(this, "Save complete!");
                dispose();
                new WorkoutProgramGUI(application).setVisible(true);

            }catch (SQLException e){
                JOptionPane.showMessageDialog(this, "Error saving");
            }
        }else{
            JOptionPane.showMessageDialog(this,"Please fill all fields");
        }
    }
}

//2