import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Calendar {
    private User currentUser;
    private TextUI ui;


    public Calendar(User currentUser) {
        this.currentUser = currentUser;
        this.ui = new TextUI();
    }

    public void displayCalendar(){
        int year = ui.promptNumeric("Enter year:");
        int month = ui.promptNumeric("Enter month");

        GregorianCalendar cal = new GregorianCalendar(year, month-1, 1);
        ui.displayMsg("\n   " + cal.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, Locale.ENGLISH) + " " + year);
        ui.displayMsg("Su Mo Tu We Th Fr Sa");

        int firstDayOfWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);

        // Get the number of days in the month
        int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        // Print spaces for the days before the first day of the month
        for (int i = 1; i < firstDayOfWeek; i++) {
            ui.displayMsg("   ");
        }

        // Print the days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            System.out.printf("%2d ", day);

            // Start a new line after Saturday
            if ((day + firstDayOfWeek - 1) % 7 == 0) {
                ui.displayMsg("");
            }
        }
        ui.displayMsg(""); // Add a new line at the end
    }


    public void addToCalendar(){

    }

    public void removeFromCalendar(){

    }
}
