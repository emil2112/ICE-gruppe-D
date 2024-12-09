public class Menu {
    private User currentUser;

    public Menu(User currentUser) {
        this.currentUser = currentUser;
    }

    void displayMenu() {
        Calendar calendar = new Calendar(currentUser);
        calendar.displayCalendar();
    }
}
