import javax.swing.*;
import java.awt.*;


public class MainMenu extends JFrame {
    private Application application;

    public MainMenu(Application application){
        this.application = application;
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360,640);
        setLayout(new GridLayout(6,1));


        JLabel welcomeLabel = new JLabel("Welcome to Uga Buga Workouts",SwingConstants.CENTER);
        add(welcomeLabel);

        JButton workoutButton = new JButton("Workout");
        JButton workoutProgramButton = new JButton("Workout Program");
        JButton calendar = new JButton("Calendar");
        JButton stats = new JButton("Stats");
        JButton settings = new JButton("Settings");

        add(workoutButton);
        add(workoutProgramButton);
        add(calendar);
        add(stats);
        add(settings);


        //Action listeners
        workoutButton.addActionListener(e -> handleWorkout());
        workoutProgramButton.addActionListener(e -> handleWorkoutProgram());
        calendar.addActionListener(e -> handleCalendar());
        stats.addActionListener(e -> handleStats());
        settings.addActionListener(e -> handleSettings());

        setLocationRelativeTo(null);
    }
    private void handleWorkout(){
        JOptionPane.showMessageDialog(this,"Workout clicked");
    }
    private void handleWorkoutProgram(){
        JOptionPane.showMessageDialog(this,"Workout Program clicked");
        WorkoutProgram workoutProgram = new WorkoutProgram(application.getCurrentUser());
        workoutProgram.displayWorkoutProgramMenu();
    }
    private void handleCalendar(){
        JOptionPane.showMessageDialog(this,"Calendar clicked");
    }
    private void handleStats(){
        JOptionPane.showMessageDialog(this,"Stats clicked");
    }
    private void handleSettings(){
        JOptionPane.showMessageDialog(this,"Settings clicked");
    }

}