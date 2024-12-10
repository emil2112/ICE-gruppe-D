import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Calendar {
    private GregorianCalendar calendar;
    private User currentUser;
    private TextUI ui;


    public Calendar(User currentUser) {
        calendar = new GregorianCalendar(2024, 12-1, 1);
        this.currentUser = currentUser;
        this.ui = new TextUI();
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
                Menu menu = new Menu(currentUser);
                menu.displayMenu();
                break;
            default:
                ui.displayMsg("Invalid choice. Try again.");
                calendarMenu();
                break;
        }
    }

    public void displayCalendar(){
        int firstDayOfMonth = calendar.getFirstDayOfWeek();
        int daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        ui.displayMsg("Your Workout Calendar");
        ui.displayMsg("=================================");
        ui.displayMsg("December, 2024");
        ui.displayMsg("Sun Mon Tue Wed Thu Fri Sat");
        for(int i = 1; i < firstDayOfMonth; i++){ //
            ui.displayMsg("    ");
        }

        int dayOfMonth = 1;
        for(int i = firstDayOfMonth; i <= 7; i++){
            System.out.printf("%3d ", dayOfMonth++);
        }
        ui.displayMsg("");

        while(dayOfMonth <= daysInMonth){
            for(int i = 1; i <= 7 && dayOfMonth <= daysInMonth; i++){
                System.out.printf("%3d ", dayOfMonth++);
            }
            ui.displayMsg("");
        }
    }


    public void addToCalendar(){

    }

    public void removeFromCalendar(){

    }

    public void showWorkouts(){

    }
}
