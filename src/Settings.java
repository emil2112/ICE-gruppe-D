public class Settings {
    private User currentUser;
    private DBConnector connector;
    private TextUI ui;

    public Settings(User currentUser, DBConnector connector) {
        this.currentUser = currentUser;
        this.connector = connector;
        ui = new TextUI();
    }

    public void settingsMenu() {
        ui.displayMsg("1. Change username. \n2. Change password. \n3. Exit");
        int choice = ui.promptNumeric("Enter your choice:");
        switch (choice) {
            case 1:
                String newUsername = ui.promptText("Enter your new username: ");
                while (newUsername.length() < 4 || newUsername.length() > 25) {
                    ui.displayMsg("Username must be between 4 and 25 characters");
                    newUsername = ui.promptText("Type username:");
                }
                if(connector.userExists(newUsername)) { // Tjekker om den skrevner bruger allerede eksistere i databasen.
                    ui.displayMsg("Username is already in use. Please choose a different username");
                    settingsMenu();
                    return;
                }
                connector.setNewUsername(newUsername, currentUser.getUsername());
                connector.changeCalendarTableName(newUsername, currentUser.getUsername());
                currentUser.setUsername(newUsername);
                ui.displayMsg("Username has been changed");
                settingsMenu();
                break;
            case 2:
                String newPassword = ui.promptText("Enter your new password:");
                while (newPassword.length() < 4 || newPassword.length() > 25) {
                    ui.displayMsg("Password must be between 4 and 25 characters");
                    newPassword = ui.promptText("Type password:");
                }
                connector.setNewPassword(newPassword, currentUser.getUsername());
                currentUser.setPassword(newPassword);
                ui.displayMsg("Password has been changed");
                settingsMenu();
                break;
            case 3:
                Menu menu = new Menu(currentUser, connector);
                menu.displayMenu();
            default:
                ui.displayMsg("Invalid choice");
                settingsMenu();
                break;
        }
    }

}
