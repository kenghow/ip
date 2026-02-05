package minnie;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * The entry point of the Minnie chatbot application.
 * Coordinates user interaction (UI), command interpretation (Parser)
 * task operations (TaskList), and persistence (Storage).
 */
public class Minnie {

    private static final String DEFAULT_FILE_PATH = "data/minnie.txt";

    private final Ui ui;
    private final Parser parser;
    private final Storage storage;
    private final TaskList taskList;

    public Minnie(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (MinnieException e) {
            ui.showError(e.getMessage());
            loaded = new TaskList();
        }
        taskList = loaded;
    }

    /**
     * Runs the main real-eval-print loop of the chatbot until the user exits.
     * Reads commands from standard input, executes the command, and prints responses.
     */
    public void run() {
        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String trimmed = scanner.nextLine().trim();

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
                    handleMarkUnmark(trimmed);
                    continue;
                }

                if (trimmed.startsWith("delete")) {
                    handleDelete(trimmed);
                    continue;
                }

                if (trimmed.startsWith("find")) {
                    handleFind(trimmed);
                    continue;
                }

                if (trimmed.isEmpty()) {
                    throw new MinnieException("Please enter a command.");
                }

                // Everything else is treated as an "add task" command (todo/deadline/event)
                Task task = parser.parseTask(trimmed);
                taskList.add(task);
                storage.save(taskList);
                ui.showAdded(task, taskList.size());

            } catch (MinnieException e) {
                ui.showError(e.getMessage());
            }
        }

        scanner.close();
    }

    private void handleMarkUnmark(String trimmed) throws MinnieException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MinnieException("Please provide a task number after '" + parts[0] + "'.");
        }

        int taskNumber = parseTaskNumber(parts[1].trim());

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
    }

    private void handleDelete(String trimmed) throws MinnieException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MinnieException("Please provide a task number after 'delete'.");
        }

        int taskNumber = parseTaskNumber(parts[1].trim());

        try {
            Task deleted = taskList.delete(taskNumber);
            storage.save(taskList);
            ui.showDeleted(deleted, taskList.size());
        } catch (IndexOutOfBoundsException e) {
            throw new MinnieException(e.getMessage());
        }
    }

    private void handleFind(String trimmed) throws MinnieException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MinnieException("Please provide a keyword after 'find'.");
        }
        String keyword = parts[1].trim();
        ArrayList<Integer> matches = taskList.find(keyword);
        ui.showFindResults(taskList, matches);
    }

    private int parseTaskNumber(String raw) throws MinnieException {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new MinnieException("Task number must be an integer.");
        }
    }

    /**
     * Lauches the Minnie chatbot application.
     * @param args Command-line argument (unused).
     */
    public static void main(String[] args) {
        new Minnie(DEFAULT_FILE_PATH).run();
    }
}