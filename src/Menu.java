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

        if (choice == 1){
            ui.displayMsg("Entering Workout menu...");
            this.workout = new Workout(connector, currentUser);
            workout.startWorkout();
        } else if (choice == 2){
            System.out.println("Entering Workout program tab...");
            this.workoutProgram = new WorkoutProgram(currentUser, connector);
            workoutProgram.displayWorkoutProgramMenu();
        } else if (choice == 3){
            Calendar calender = new Calendar(currentUser, connector);
            ui.displayMsg("Entering Calendar...");
            calender.calendarMenu();
        } else if (choice == 4){
            System.out.println("Entering stats page...");
            ui.displayMsg("Under Development");
            ui.promptNumeric("Press 1 to go back");
            displayMenu();
        } else if (choice == 5) {
            Settings settings = new Settings(currentUser, connector);
            settings.settingsMenu();
            System.out.println("Entering settings...");
        } else if (choice == 6){
            ui.displayMsg("Exiting program...");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            displayMenu();
        }
    }
}
