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
                for(int sets = 0; sets <= e.getSets(); sets++){
                    ui.displayMsg("Current exercise: "+ e.getExerciseName()+"\n Set number "+sets);
                    ui.promptNumeric("How many reps did you do?");
                    ui.promptNumeric("How much weight did you lift?");
                    ui.displayMsg("Good job! Now rest for "+e.getRestTime());
                }
            }
            workingOut = false;

        }
    }

}
