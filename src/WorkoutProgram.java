import java.util.ArrayList;
import java.util.List;

public class WorkoutProgram {
    private User currentUser;
    private TextUI ui;
    private DBConnector connector;


    public WorkoutProgram(User currentUser, DBConnector connector){
        this.currentUser = currentUser;
        ui = new TextUI();
        this.connector = connector;
        var url = "jdbc:sqlite:identifier.sqlite";
        connector.connect(url);
    }
    //Collection<Exercise> exercises;
    public void displayWorkoutProgramMenu(){
        ui.displayMsg("        Workout tab        \n========================= \n1. Create new workout \n2. Your Workout Programs");
        int choice = ui.promptNumeric("Enter number of menu:");

        if (choice == 1){
            createWorkoutProgram();
        } else if (choice == 2){
            System.out.println("Your workout programs:");
        } else{
            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            displayWorkoutProgramMenu();
        }

    }
    void createWorkoutProgram() {
        List<Exercise> exercises = connector.getAllExercises();
        if (exercises.isEmpty()) {
            ui.displayMsg("No exercises available in the database.");
            return;
        }

        ui.displayMsg("Available exercises:");
        for (Exercise exercise : exercises) {
            ui.displayMsg(exercise.toString());
        }

        List<Exercise> selectedExercises = new ArrayList<>();
        while (true) {
            int exerciseId = ui.promptNumeric("Enter the number of desired exercise to add to your program or 0 to finish:");

            if (exerciseId == 0) {
                break;
            }

            Exercise selectedExercise = null;
            for (Exercise exercise : exercises) {
                if (exercise.getExerciseID() == exerciseId) {
                    selectedExercise = exercise;
                    break;
                }
            }

            if (selectedExercise != null) {
                selectedExercises.add(selectedExercise);
                ui.displayMsg("Added: " + selectedExercise.getExerciseName());
            } else {
                ui.displayMsg("Invalid ID. Please try again.");
            }
        }

        if (selectedExercises.isEmpty()) {
            ui.displayMsg("No exercises selected. Program was not created.");
        } else {
            // save workout program to database code needed here...
            ui.displayMsg("Workout program created with the following exercises:");
            for (Exercise exercise : selectedExercises) {
                ui.displayMsg(exercise.toString());
            }
        }
    }
}

//2