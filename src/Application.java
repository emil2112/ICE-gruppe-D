import java.util.HashMap;

public class Application {
    private String name;
    User currentUser;
    HashMap<String, String> loginData;
    TextUI ui;
    //DBConnector connector;

    public Application(String name) {
        this.name = name;
    }

    public void startApplication() {
        ui.displayMsg("Welcome to " + this.name + "\n1. Sign up now! \n2. Login");

        int choice = ui.promptNumeric("Type 1 or 2.");

        if (choice == 1) { // Brugernes valg bliver gemt og tjekkes om hvilket nummer det er.
            createUser();
            // METODE TIL AT KOMME TIL MENUEN!!
        } else if (choice == 2) {
            loadUserData();
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
        while (userData.containsKey(username)) { // Loop der altid tjekker om det nye username allerede eksistere.
            ui.displayMsg("Username already exists. Please choose different username");
            username = ui.promptText("Type username:");
        }
        String password = ui.promptText("Type password:");
        while (password.length() < 4 || password.length() > 25) {
            ui.displayMsg("Password must be between 4 and 25 characters");
            password = ui.promptText("Type password:");
        }

        ui.displayMsg("Thank you for using " + this.name + ". You are almost ready, we just need a few informations");
        int choice = ui.promptNumeric("Which sex are you? \n1. Male \n2. Female \n Type number:");
        String sex = "";
        while(choice < 1 || choice > 2) {
            ui.displayMsg("Sorry but that choice is invalid");
        }
        if (choice == 1) {
            sex = "male";
        }
        if (choice == 2) {
            sex = "female";
        }
        int age = ui.promptNumeric("Type your age:");
        int height = ui.promptNumeric("Type your height in centimeters:");
        float weight = ui.promptDecimalNumeric("Type your weight in kilograms:");
        currentUser = new User(username, password, sex, age, height, weight);
    }

    public void loadUserData() {
        if(!userData.isEmpty()) {
            String enteredUsername = ui.promptText("Type username:");
            String enteredPassword = ui.promptText("Type password:");
            if(userData.containsKey(enteredUsername) && userData.get(enteredUsername).equals(enteredPassword)){
                // Den if tjekker om det tastede username (key) eksistere. Herefter os tastede password stemmer overens med sin key.
                currentUser = new User(enteredUsername, enteredPassword);
                // METODE TIL AT KOMME TIL MENUEN!!
            } else {
                ui.displayMsg("Username or password is wrong. Please try again");
                loadUserData();
            }
        } else {
            ui.displayMsg("Sorry, but we don't have any user yet. Please create on.");
            createUser();
        }
    }
}
