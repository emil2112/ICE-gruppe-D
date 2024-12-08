import java.util.HashMap;

public class Application {
    private String name;
    private User currentUser;
    private HashMap<String, String> loginData;
    private TextUI ui;
    private DBConnector connector;
    private Menu menu;

    public Application(String name) {
        this.name = name;
        ui = new TextUI();
        connector = new DBConnector();
        connector.connect("jdbc:sqlite:identifier.sqlite");
    }

    public void startApplication() {
        ui.displayMsg("Welcome to " + this.name + "\n1. Sign up now! \n2. Login");
        int choice = ui.promptNumeric("Type 1 or 2.");

        if (choice == 1) { // Brugernes valg bliver gemt og tjekkes om hvilket nummer det er.
            createUser();
            menu = new Menu(currentUser);
            menu.displayMenu();
        } else if (choice == 2) {
            loadUserData();
            menu = new Menu(currentUser);
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
        if(connector.userExistsInDatabase(username)) {
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
        int age = ui.promptNumeric("Type your age:");
        int height = ui.promptNumeric("Type your height in centimeters:");
        float weight = ui.promptDecimalNumeric("Type your weight in kilograms:");

        connector.registerUser(username, password, sex, age, height, weight);
        currentUser = new User(username, password, sex, age, height, weight);
    }

    public void loadUserData() {
        String enteredUsername = ui.promptText("Type username:");
        String enteredPassword = ui.promptText("Type password:");

        // Attempt to load user data from the database
        if (connector.isValidLogin(enteredUsername, enteredPassword)) {
            // Retrieve additional user details from the database
            User user = connector.getUserDetails(enteredUsername);
            if (user != null && user.getPassword().equals(enteredPassword)) {
                // Create a new User object with full details
                currentUser = user;
                // Proceed to the next part of the application (e.g., user menu)
            } else {
                ui.displayMsg("Username or password is incorrect. Please try again");
                loadUserData();
            }
        } else {
            ui.displayMsg("User does not exist. Please create a new one");
            createUser();  // Retry if credentials are wrong
        }
    }

}
