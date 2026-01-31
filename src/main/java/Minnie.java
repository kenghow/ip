import java.util.Scanner;

public class Minnie {

    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();

        while(true) {
            String userInput = scanner.nextLine();
            String trimmed = userInput.trim();

            if (trimmed.equals("bye")) {
                ui.showGoodbye();
                break;
            }

            if (trimmed.equals("list")) {
                ui.showList(taskList);
                continue;
            }

            if (trimmed.startsWith("mark") || trimmed.startsWith("unmark")) {
                String[] parts = trimmed.split("\\s+", 2);
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    ui.showError("Please provide a task number after '" + parts[0] + ".");
                    return;
                }

                try {
                    int taskNumber = Integer.parseInt(parts[1].trim());
                    if (trimmed.startsWith("mark")) {
                        Task task = taskList.mark(taskNumber);
                        ui.showMarked(task);
                    } else {
                        Task task = taskList.unmark(taskNumber);
                        ui.showUnmarked(task);
                    }
                } catch (NumberFormatException e) {
                    ui.showError("Task number must be an integer.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError(e.getMessage());
                }
                continue;
            }

            if (trimmed.isEmpty()) {
                ui.showError("Please enter a task description");
                continue;
            }

            try {
                Task task = parser.parseTask(trimmed);
                taskList.add(task);
                ui.showAdded(task, taskList.size());
            } catch (IllegalArgumentException e) {
                ui.showError(e.getMessage());
            }
        }
        scanner.close();
    }


}