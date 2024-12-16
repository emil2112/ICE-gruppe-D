import java.util.ArrayList;
import java.util.List;

public class WorkoutProgram {
    private User currentUser;
    private TextUI ui;
    private DBConnector connector;
    private List<Exercise> selectedExercises = new ArrayList<>();// Declare the list here
    private Menu menu;

    public WorkoutProgram(User currentUser, DBConnector connector) {
        this.currentUser = currentUser;
        this.ui = new TextUI();
        this.connector = connector;
        this.menu = new Menu(currentUser, connector);
    }

    // Method to display the workout program menu
    public void displayWorkoutProgramMenu() {
        ui.displayMsg("        Workout tab        \n========================= \n1. Create new workout \n2. Your Workout Programs \n3. Go back to main menu");
        int choice = ui.promptNumeric("Enter number of menu:");

        if (choice == 1) {
            createWorkoutProgram();
            displayWorkoutProgramMenu();
        } else if (choice == 2) {
            System.out.println("Your workout programs:");
            connector.displayPrograms(currentUser.getUsername());
            String userChoice = ui.promptText("Continue? Type yes");
            if (userChoice.equals("yes")) {
                displayWorkoutProgramMenu();
            }
        } else if (choice == 3) {
            menu.displayMenu();
        } else {
            System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            displayWorkoutProgramMenu();
        }
    }

    // method to create a workout program
    void createWorkoutProgram() {
        List<Exercise> exercises = connector.getAllExercises();
        if (exercises.isEmpty()) {
            ui.displayMsg("No exercises available in the database.");
            return;
        }

        ui.displayMsg("Available exercises:");
        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            ui.displayMsg(i + 1 + ". " + exercise.getExerciseName());
        }

        while (true) {
            int exerciseId = ui.promptNumeric("Enter the number of desired exercise to add to your program or 0 to finish:");

            if (exerciseId == 0) {
                break;
            }

            Exercise selectedExercise = exercises.get(exerciseId - 1);

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
            connector.saveWorkoutProgram(this, currentUser);

            ui.displayMsg("Workout program created with the following exercises:");
            for (Exercise exercise : selectedExercises) {
                ui.displayMsg(exercise.getExerciseName());
            }
        }
    }

    // Getter for the selected exercises
    public List<Exercise> getSelectedExercises() {
        return selectedExercises;
    }
}
