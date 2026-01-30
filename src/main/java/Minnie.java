import java.util.Scanner;

public class Minnie {

    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();

        while(true) {
            String userInput = scanner.nextLine();
            String trimmed = userInput.trim();
            if ((trimmed).equals("bye")) {
                ui.showGoodbye();
                break;
            } else if (trimmed.equals("list")) {
                ui.showList(taskList);
            } else {
                taskList.add(userInput);
                ui.showAdded(userInput);
            }
        }
        scanner.close();
    }
}