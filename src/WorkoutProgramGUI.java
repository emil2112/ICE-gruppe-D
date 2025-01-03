import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkoutProgramGUI extends JFrame{
    private Application application;
    private MainMenuGUI mainMenu = new MainMenuGUI(application);
    private JButton goBack;

    public WorkoutProgramGUI(Application application){
        this.application = application;
        setTitle("Workout Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360,640);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Title
        JLabel titleLabel = new JLabel("Workout Program",SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel,gbc);

        //Workout
        JButton createWorkoutButton = new JButton("Create Workout");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(createWorkoutButton,gbc);

        //Exercise
        JButton createExerciseButton = new JButton("Create Exercise");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(createExerciseButton,gbc);

        //Go back
        goBack = new JButton("Go Back");
        gbc.gridx = 0;
        gbc.gridy = 16;
        add(goBack, gbc);

        //Methods
        createWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateWorkout();
            }
        });

        createExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateExercise();
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuGUI(application).setVisible(true);
            }
        });

        setLocationRelativeTo(null);
    }
    private void handleCreateWorkout(){

       new CreateWorkoutProgramGUI(application).setVisible(true);
       dispose();
    }
    private void handleCreateExercise(){
        new CreateExerciseGUI(application).setVisible(true);
        dispose();
    }
}

//2