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

        int fir

    }

    public void addToCalendar(){

    }

    public void removeFromCalendar(){

    }
}
