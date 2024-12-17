import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutGUI extends JFrame{
    private Application application;
    private JTextField repsDone,weightDone;
    private JButton goBack, startWorkout;
    private JLabel reps,weight,exerciseName,restMessage;
    private JComboBox<String> workoutList;
    private DBConnector connector;

    public WorkoutGUI(Application application){
        this.application = application;
        this.connector = application.getConnector();

        setTitle("Workout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360,640);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Title
        JLabel titleLabel = new JLabel("Select Workout", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel,gbc);

        //Workout Selection
        JLabel selectWorkout = new JLabel("Choose your workout");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(selectWorkout,gbc);

        workoutList = new JComboBox<>();
        loadWorkouts();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(workoutList,gbc);


        //Start
        startWorkout = new JButton("Start Workout");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(startWorkout,gbc);

        //Go back
        goBack = new JButton("Go back");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(goBack,gbc);

        //Action listeners
        startWorkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startWorkout();
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuGUI(application).setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null);

    }
    private void loadWorkouts(){
        List<String> workouts = connector.displayPrograms(application.getCurrentUser().getUsername());
        for (String workout : workouts){
            workoutList.addItem(workout);
        }
    }

    private void startWorkout(){
        String selectedWorkout = (String) workoutList.getSelectedItem();
        if (selectedWorkout.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please select an exercise.");
            return;
        }
        try {
            List<Exercise> exercises = connector.getProgramExercises(selectedWorkout);
            if (exercises.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No exercises found.");
                return;
            }
        for (Exercise e : exercises){
            for (int set = 1; set <= e.getSets(); set++){

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10,10,10,10);

                exerciseName = new JLabel("Exercise: "+e.getExerciseName());
                gbc.gridx = 0;
                gbc.gridy = 4;
                add(exerciseName,gbc);

                //Reps
                reps = new JLabel("Reps: ");
                gbc.gridx = 0;
                gbc.gridy = 5;
                add(reps,gbc);

                repsDone = new JTextField(5);
                gbc.gridx = 1;
                gbc.gridy = 5;
                add(repsDone,gbc);

                //Weight
                weight = new JLabel("Weight: ");
                gbc.gridx = 0;
                gbc.gridy = 6;
                add(weight,gbc);

                weightDone = new JTextField(5);
                gbc.gridx = 1;
                gbc.gridy = 6;

                //Rest
                restMessage = new JLabel("Rest for " + e.getRestTime() + "seconds");
                gbc.gridx = 0;
                gbc.gridy = 7;
                add(restMessage,gbc);

                //Saving for database, not implemented yet.
                int repsCompleted = Integer.parseInt(repsDone.getText());
                float weightLifted = Float.parseFloat(weightDone.getText());

            }
        }
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}

