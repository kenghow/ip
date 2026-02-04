import java.util.Scanner;

public class Minnie {

    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/minnie.txt");

        try {
            taskList = new TaskList(storage.load());
        } catch (MinnieException e) {
            taskList = new TaskList();
            ui.showError(e.getMessage());
        }

        ui.showWelcome();

        while (true) {
            String userInput = scanner.nextLine();
            String trimmed = userInput.trim();

            try {
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
                        throw new MinnieException("Please provide a task number after '" + parts[0] + "'.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        throw new MinnieException("Task number must be an integer.");
                    }

                    try {
                        if (trimmed.startsWith("mark")) {
                            Task task = taskList.mark(taskNumber);
                            storage.save(taskList);
                            ui.showMarked(task);
                        } else {
                            Task task = taskList.unmark(taskNumber);
                            storage.save(taskList);
                            ui.showUnmarked(task);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        throw new MinnieException(e.getMessage());
                    }

                    continue;
                }

                if (trimmed.startsWith("delete")) {
                    String[] parts = trimmed.split("\\s+", 2);
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new MinnieException("Please provides a task number after 'delete'.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        throw new MinnieException("Task number must be an integer.");
                    }

                    try {
                        Task deleted = taskList.delete(taskNumber);
                        storage.save(taskList);
                        ui.showDeleted(deleted, taskList.size());
                    } catch (IndexOutOfBoundsException e) {
                        throw new MinnieException(e.getMessage());
                    }
                    continue;
                }

                if (trimmed.isEmpty()) {
                    throw new MinnieException("Please enter a command.");
                }

                // Everything else is treated as an "add task" command (todo/deadline/event)
                Task task = parser.parseTask(trimmed); // <-- now throws DukeException
                taskList.add(task);
                storage.save(taskList);
                ui.showAdded(task, taskList.size());

            } catch (MinnieException e) {
                ui.showError(e.getMessage());
            }
        }

        scanner.close();
    }
}