import java.lang.runtime.SwitchBootstraps;
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
            displayYourWorkoutPrograms();
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

    public void displayYourWorkoutPrograms() {
        ArrayList<String> workoutNames = connector.displayPrograms(currentUser.getUsername());
        boolean editWorkout = true;
        int counter = 1;
        for (String s : workoutNames) {
            System.out.println(counter + ". " + s);
            counter++;
        }
        int workoutProgramUserChoice = ui.promptNumeric("Choose workout to edit or remove:");
        String workoutProgramChoice = workoutNames.get(workoutProgramUserChoice - 1);
        List<Exercise> exercises = connector.getProgramExercises(workoutProgramChoice);

        while (editWorkout) {
            counter = 1;
            ui.displayMsg("0. Remove entire workout program");
                for (Exercise e : exercises) {
                    ui.displayMsg(counter + ". " + e.getExerciseName());
                    counter++;
            }
            int exerciseChoice = ui.promptNumeric("Choose an exercise to replace or delete entire workout program:");

            if (exerciseChoice == 0){
                // Delete workout program from user
                connector.deleteWorkoutProgram(currentUser.getUsername(), workoutProgramChoice);
                ui.displayMsg("Workout program deleted succesfully");
                workoutNames.remove(workoutProgramChoice);
                editWorkout = false;
            } else if (exerciseChoice >= 1 && exerciseChoice <= exercises.size()){
                // replace exercise
                List<Exercise> allExercises = connector.getAllExercises();
                ui.displayMsg("Available Exercises to replace with: ");
                for (int i = 0; i < allExercises.size(); i++){
                    ui.displayMsg((i + 1) + ". " + allExercises.get(i).getExerciseName());
                }
                int newExerciseChoice = ui.promptNumeric("Choose a new exercise:");
                if (newExerciseChoice >= 1 && newExerciseChoice <= exercises.size()){
                    Exercise newExercise = allExercises.get(newExerciseChoice - 1);
                    exercises.set(exerciseChoice - 1, newExercise);
                    connector.updateWorkoutProgram(workoutProgramChoice, exercises);
                    ui.displayMsg("Exercise replaced succesfully");
                } else {
                    ui.displayMsg("Invalid exercise selection");
                }
            } else {

            }
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
