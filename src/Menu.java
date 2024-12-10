public class Menu {
    private User currentUser;
    private TextUI ui;
    private WorkoutProgram workoutProgram;
    private DBConnector connector;

    public Menu(User currentUser) {
        this.currentUser = currentUser;
        ui = new TextUI();
        this.connector = connector;

    }

    public void displayMenu() {
        ui.displayMsg("        Main Menu        \n========================= \n1. Workout \n2. Workout Program \n3. Calendar \n4. Stats\n5. Settings");
        int choice = ui.promptNumeric("Enter number of menu:");

        if (choice == 1){
            System.out.println("Entering workout tab...");
        } else if (choice == 2){
            System.out.println("Entering Workout program tab...");
            this.workoutProgram = new WorkoutProgram(currentUser, connector);
            workoutProgram.displayWorkoutProgramMenu();
        } else if (choice == 3){
            Calendar calender = new Calendar(currentUser);
            ui.displayMsg("Entering Calendar...");
            calender.calendarMenu();
        } else if (choice == 4){
            System.out.println("Entering stats page...");
        } else if (choice == 5){
            System.out.println("Entering settings...");
        } else {
            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            displayMenu();
        }
    }
}
