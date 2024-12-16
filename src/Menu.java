public class Menu {
    private User currentUser;
    private TextUI ui;
    private WorkoutProgram workoutProgram;
    private DBConnector connector;
    private Workout workout;

    public Menu(User currentUser, DBConnector connector) {
        this.currentUser = currentUser;
        ui = new TextUI();
        this.connector = connector;

    }

    public void displayMenu() {
        ui.displayMsg("        Main Menu        \n========================= \n1. Workout \n2. Workout Program \n3. Calendar \n4. Stats\n5. Settings \n6. Exit");
        int choice = ui.promptNumeric("Enter number of menu:");

        switch (choice) {
            case 1:
                ui.displayMsg("Entering Workout menu...");
                this.workout = new Workout(connector, currentUser);
                workout.startWorkout();
                break;
            case 2:
                System.out.println("Entering Workout program tab...");
                this.workoutProgram = new WorkoutProgram(currentUser, connector);
                workoutProgram.displayWorkoutProgramMenu();
                break;
            case 3:
                Calendar calendar = new Calendar(currentUser, connector);
                ui.displayMsg("Entering Calendar...");
                calendar.calendarMenu();
                break;
            case 4:
                System.out.println("Entering stats page...");
                ui.displayMsg("Under Development");
                ui.promptNumeric("Press 1 to go back");
                displayMenu();
                break;
            case 5:
                Settings settings = new Settings(currentUser, connector);
                settings.settingsMenu();
                System.out.println("Entering settings...");
                break;
            case 6:
                ui.displayMsg("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                displayMenu();
                break;
        }
    }
}
