import java.util.Scanner;

public class Minnie {

    public static void main(String[] args) {
        Ui ui = new Ui();
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();

        while(true) {
            String userInput = scanner.nextLine();
            if ((userInput.trim()).equals("bye")) {
                ui.showGoodbye();
                break;
            }
            ui.echo(userInput);
        }
        scanner.close();
    }
}