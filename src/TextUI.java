import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);

    public void displayMsg(String msg){
        System.out.println(msg);
    }

    public int promptNumeric(String msg) {
        System.out.println(msg);              // Stille brugeren et spørgsmål
        String input = scan.nextLine();
        int number;
        // Give brugere et sted at placere sit svar og vente på svaret
        try {
            number = Integer.parseInt(input);
        }
        catch(NumberFormatException e){
            displayMsg("Please type a number");
            number = promptNumeric(msg);
        }
        return number;
    }

    public String promptText(String msg){
        System.out.println(msg);//Stille brugeren et spørgsmål
        String input = scan.nextLine();
        return input;
    }
}
