import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Calendar {
    private GregorianCalendar calendar;
    private User currentUser;
    private TextUI ui;
    private DBConnector connector;

    public Calendar(User currentUser, DBConnector connector) {
        calendar = new GregorianCalendar(2024, 12-1, 1);
        this.currentUser = currentUser;
        this.ui = new TextUI();
        this.connector = connector;
    }

    public void calendarMenu() {
        displayCalendar();
        ui.displayMsg("1. Add Workout \n2. Remove Workout \n3. Show Workouts \n4. Exit to main menu");
        int choice = ui.promptNumeric("Enter choice: ");

        switch (choice) {
            case 1:
                addToCalendar();
                break;
            case 2:
                removeFromCalendar();
                break;
            case 3:
                showWorkouts();
                break;
            case 4:
                Menu menu = new Menu(currentUser, connector);
                menu.displayMenu();
                break;
            default:
                ui.displayMsg("Invalid choice. Try again.");
                calendarMenu();
                break;
        }
    }

    public void displayCalendar() {
        int firstDayOfMonth = calendar.getFirstDayOfWeek();
        int daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        ui.displayMsg("Your Workout Calendar");
        ui.displayMsg("=================================");
        ui.displayMsg("December, 2024");
        ui.displayMsg("Sun Mon Tue Wed Thu Fri Sat");
        for (int i = 1; i < firstDayOfMonth; i++) { //
            ui.displayMsg("    ");
        }

        int dayOfMonth = 1;
        while (dayOfMonth <= daysInMonth) {
            for (int i = 1; i <= 7 && dayOfMonth <= daysInMonth; i++) {
                if (connector.hasWorkout(dayOfMonth, currentUser.getUsername())) {
                    System.out.printf("%3d* ", dayOfMonth);
                } else {
                    System.out.printf("%3d ", dayOfMonth);
                }
                dayOfMonth++;
            }
            ui.displayMsg("");
        }
    }

    public void addToCalendar(){
        int day = ui.promptNumeric("Enter the day you want to add a workout program to:");
        ArrayList<String> workoutNames = connector.getworkoutNames(currentUser.getUsername());
        int counter = 1;
        for(String e: workoutNames){
            ui.displayMsg(counter + ". " + e);
            counter++;
        }

        int userChoice = ui.promptNumeric("Enter the number on the program you want to add:");
        String workoutName = workoutNames.get(userChoice-1);

        int workoutID = connector.getWorkoutID(workoutName);
        connector.registerWorkoutDay(workoutID, day, currentUser.getUsername());
        calendarMenu();
    }

    public void removeFromCalendar(){
        int choice = ui.promptNumeric("Enter the day you want to remove a workout program from:");
        connector.removeWorkoutDay(choice, currentUser.getUsername());
        calendarMenu();
    }

    public void showWorkouts(){
        // Man skal kunne vælge en dag fra kalenderen, hvor den så viser program for en given dag (hvis der ligger noget på den).
    }
}
