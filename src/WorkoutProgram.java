public class WorkoutProgram {
    private User currentUser;
    private TextUI ui;

    public WorkoutProgram(User currentUser){
        this.currentUser = currentUser;
        ui = new TextUI();
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
    void createWorkoutProgram(){

    }
}
