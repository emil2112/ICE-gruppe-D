import javax.swing.*;
import java.util.HashMap;

public class Application {
    private String name;
    private User currentUser;
    private TextUI ui;
    private DBConnector connector;
    private Menu menu;

    public Application(String name) {
        this.name = name;
        ui = new TextUI();
        connector = new DBConnector();
        var url = "jdbc:sqlite:identifier.sqlite";
        connector.connect(url);
    }

    public void startApplication() {
        SwingUtilities.invokeLater(()-> new LoginScreenGUI(this).setVisible(true));
        ui.displayMsg("Welcome to " + this.name + "\n1. Sign up now! \n2. Login");
        int choice = ui.promptNumeric("Type 1 or 2.");

        if (choice == 1) { // Brugernes valg bliver gemt og tjekkes om hvilket nummer det er.
            createUser();
            menu = new Menu(currentUser, connector);
            menu.displayMenu();
        } else if (choice == 2) {
            loadUserData();
            menu = new Menu(currentUser, connector);
            menu.displayMenu();
        } else {
            ui.displayMsg("Sorry but that choice is invalid");
            // hvis ikke de skriver 1 eller 2, bliver dette her printet og funktionen bliver kaldt igen.
            startApplication();
        }
    }

    public void createUser() {
        String username = ui.promptText("Type username:");
        while (username.length() < 4 || username.length() > 25) {
            ui.displayMsg("Username must be between 4 and 25 characters");
            username = ui.promptText("Type username:");
        }
        if(connector.userExists(username)) { // Tjekker om den skrevner bruger allerede eksistere i databasen.
            ui.displayMsg("Username is already in use. Please choose a different username");
            createUser();
            return;
        }
        String password = ui.promptText("Type password:");
        while (password.length() < 4 || password.length() > 25) {
            ui.displayMsg("Password must be between 4 and 25 characters");
            password = ui.promptText("Type password:");
        }

        ui.displayMsg("Thank you for using " + this.name + ". You are almost ready, we just need a few informations");
        String sex = ui.promptText("Which sex are you? (male/female):");
        while(!sex.equalsIgnoreCase("male") && !sex.equalsIgnoreCase("female")) {
            ui.displayMsg("Sorry, but that input is invald. Please write male or female");
            sex = ui.promptText("Write your sex:");
        }
        int age = ui.promptNumeric("Type your age:");
        while(age < 15) {
            ui.displayMsg("You must be minimum 15 years of age to use the service");
            age = ui.promptNumeric("Type your age:");
        }
        int height = ui.promptNumeric("Type your height in centimeters:");
        float weight = ui.promptDecimalNumeric("Type your weight in kilograms:");

        connector.registerUser(username, password, sex, age, height, weight); // Med informationerne bliver der lavet en row med de angivne informationer
        currentUser = new User(username, password, sex, age, height, weight);
        connector.createCalendarTable(currentUser.getUsername());
    }

    public void loadUserData() {
        String enteredUsername = ui.promptText("Type username:");
        String enteredPassword = ui.promptText("Type password:");
        if (connector.validLogin(enteredUsername, enteredPassword)) { // Tjekker om det skrevne username og password passer til databasen.
            // Loader en user med data fra databasen
            User user = connector.getUserData(enteredUsername);
            if (user != null && user.getPassword().equals(enteredPassword)) {
                // Laver useren med dataen
                currentUser = user;
            }
        } else {
            ui.displayMsg("Invalid username or password. Please try again");
            loadUserData();
        }
    }

    public DBConnector getConnector() {
        return connector;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
