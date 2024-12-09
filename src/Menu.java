public class Menu {
    private User currentUser;

    public Menu(User currentUser) {
        this.currentUser = currentUser;
    }

    void displayMenu() {
        ui.displayMsg("        Main Menu        \n========================= \n1. Workout \n2. Workout Program \n3. Calendar \n4. Stats\n5. Settings");
        int choice = ui.promptNumeric("Enter number of menu:");

        if (choice == 1){
            System.out.println("Entering workout tab...");
        } else if (choice == 2){
            System.out.println("Entering Workout program tab...");
        } else if (choice == 3){
            System.out.println("Entering Calendar...");
        } else if (choice == 4){
            System.out.println("Entering stats page...");
        } else if (choice == 5){
            System.out.println("Entering settings...");
        }


    }
}
