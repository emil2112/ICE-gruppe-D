import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StartWorkoutGUI extends JFrame{
    private Application application;
    private DBConnector connector;
    private WorkoutGUI workoutGUI;
    private JLabel reps,weight,exerciseName,restMessage;
    private JTextField repsDone,weightDone;
    private Exercise exercise;

    public StartWorkoutGUI(Application application) {
        this.application = application;
        this.connector = application.getConnector();
        this.workoutGUI = new WorkoutGUI(this.application);


        String selectedWorkout = (String) workoutGUI.workoutList.getSelectedItem();
        if (selectedWorkout.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select an exercise.");
            return;
        }
        try {
            setTitle("Workout Started");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(360, 640);
            setResizable(false);
            setLayout(new GridBagLayout());

            List<Exercise> exercises = connector.getProgramExercises(selectedWorkout);

            for (Exercise e : exercises) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);


                exerciseName = new JLabel("Exercise: " + e.getExerciseName());
                gbc.gridx = 0;
                gbc.gridy = 4;
                add(exerciseName, gbc);

                //Reps
                reps = new JLabel("Reps: ");
                gbc.gridx = 0;
                gbc.gridy = 5;
                add(reps, gbc);

                repsDone = new JTextField(5);
                gbc.gridx = 1;
                gbc.gridy = 5;
                add(repsDone, gbc);

                //Weight
                weight = new JLabel("Weight: ");
                gbc.gridx = 0;
                gbc.gridy = 6;
                add(weight, gbc);

                weightDone = new JTextField(5);
                gbc.gridx = 1;
                gbc.gridy = 6;
                add(weightDone,gbc);

                //Rest
                restMessage = new JLabel("Rest for " + e.getRestTime() + " seconds.");
                gbc.gridx = 0;
                gbc.gridy = 7;
                add(restMessage, gbc);

                //Next exercise
                JButton next = new JButton("Next Exercise");
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.gridwidth = 2;
                add(next,gbc);
            }

            if (exercises.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No exercises found.");
                return;
            }
            setLocationRelativeTo(null);

            //Saving for database, not implemented yet.
            //int repsCompleted = Integer.parseInt(repsDone.getText());
            //float weightLifted = Float.parseFloat(weightDone.getText());

        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        }
    }
}

