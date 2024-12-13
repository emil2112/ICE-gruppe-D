import org.w3c.dom.Text;
import java.sql.*;
import java.util.ArrayList;

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

        //Print workout names with numbers
        ui.displayMsg("Your workout programs:");
        for(String s : workoutNames){
            int counter = 1;
            System.out.println(counter +". " + s);
        }
        int userChoice = ui.promptNumeric("Choose a workout to begin");

        String workoutChoice = workoutNames.get(userChoice-1);

    }

}
