import org.w3c.dom.Text;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Workout {
    private DBConnector dbConnector;
    private TextUI ui = new TextUI();
    private User currentUser;

    public Workout (DBConnector dbConnector, User currentUser) {
        this.dbConnector = dbConnector;
        this.currentUser = currentUser;
    }

    void startWorkout(){
        Connection conn = dbConnector.getConnection();
        ArrayList<String> workoutNames = dbConnector.displayPrograms(currentUser.getUsername());
        boolean workingOut = true;


        //Print workout names with numbers
        int counter = 1;
        ui.displayMsg("Your workout programs:");
        for(String s : workoutNames){
            System.out.println(counter +". " + s);
            counter++;
        }
        int userChoice = ui.promptNumeric("Choose a workout to begin");

        String workoutChoice = workoutNames.get(userChoice-1);
        List<Exercise> exercises = dbConnector.getProgramExercises(workoutChoice);

        while(workingOut){
            for(Exercise e : exercises){
                int setsChoice = ui.promptNumeric("You are doing "+e.getExerciseName()+". How many sets are you doing?");
                e.setSets(setsChoice);
                int restTime = ui.promptNumeric("How many minutes are you resting between each set?");
                e.setRestTime(restTime);
                ui.displayMsg("Ok, uga buga time! Have a good workout!");
                for(int sets = 1; sets <= e.getSets(); sets++){
                    ui.displayMsg("Current exercise: "+ e.getExerciseName()+"\n Set number "+sets);
                    ui.promptNumeric("How many reps did you do?");
                    ui.promptNumeric("How much weight did you lift?");
                    ui.displayMsg("Good job! Now rest for "+e.getRestTime()+" minutes");
                }
            }
            workingOut = false;
            ui.displayMsg("Your workout is complete! Good job!");
            Menu menu = new Menu(currentUser, dbConnector);
            userChoice = ui.promptNumeric("Press 1 to return to the main menu");
            if(userChoice == 1){
                menu.displayMenu();
            }
        }
    }

}
