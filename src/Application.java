import java.util.HashMap;

public class Application {
    private String name;
    //User currentUser;
    HashMap<String, String> loginData;
    TextUI ui;
    //DBConnecttor connector;

    public Application(String name) {
        this.name = name;
    }

    void startApplication() {
        ui.displayMsg("Welcome to " + this.name + "\n1. Sign up now! \n2. Login");

        int choice = ui.promptNumeric("Type 1 or 2.");

        if (choice == 1) { // Brugernes valg bliver gemt og tjekkes om hvilket nummer det er.
            createUser();
            homeMenu();
        } else if (choice == 2) {
            loadUserData();
        } else {
            ui.displayMsg("Sorry but that choice is invalid");
            // hvis ikke de skriver 1 eller 2, bliver dette her printet og funktionen bliver kaldt igen.
            startStreamingService();
        }
    }
    }


}
