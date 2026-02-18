package minnie;

import java.util.ArrayList;

public class Minnie {

    private static final String DEFAULT_FILE_PATH = "data/minnie.txt";

    private final Parser parser;
    private final Storage storage;
    private final TaskList taskList;

    public Minnie() {
        this(DEFAULT_FILE_PATH);
    }

    public Minnie(String filePath) {
        parser = new Parser();
        storage = new Storage(filePath);

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (MinnieException e) {
            loaded = new TaskList();
        }
        taskList = loaded;
    }

    public String getWelcomeMessage() {
        return "Hello! I'm Minnie.\nWhat can I do for you?";
    }

    /**
     * GUI entry-point: takes user input, returns Minnie response text.
     */
    public String getResponse(String input) {
        String trimmed = (input == null) ? "" : input.trim();

        try {
            if (trimmed.isEmpty()) {
                throw new MinnieException("Please enter a command.");
            }

            if (trimmed.equals("bye")) {
                return "Bye. Hope to see you again soon!";
            }

            if (trimmed.equals("list")) {
                return taskList.toString();
            }

            if (trimmed.startsWith("mark")) {
                int n = parseTaskNumber(trimmed, "mark");
                assert n > 0 : "Task number should be positive after parseTaskNumber";
                if (n > taskList.size()) {
                    throw new MinnieException("Error: Invalid task id!");
                }
                try {
                    Task t = taskList.mark(n);
                    assert t != null : "Task returned by mark() should not be null";
                    storage.save(taskList);
                    return "Nice! I've marked this task as done:\n" + t;
                } catch (IndexOutOfBoundsException e) {
                    throw new MinnieException("Error: Invalid task id!");
                }
            }

            if (trimmed.startsWith("unmark")) {
                int n = parseTaskNumber(trimmed, "unmark");
                assert n > 0 : "Task number should be positive after parseTaskNumber";

                if (n > taskList.size()) {
                    throw new MinnieException("Error: Invalid task id!");
                }
                try {
                    Task t = taskList.unmark(n);
                    assert t != null : "Task returned by unmark() should not be null";
                    storage.save(taskList);
                    return "OK, I've marked this task as not done yet:\n" + t;
                } catch (IndexOutOfBoundsException e) {
                    throw new MinnieException("Error: Invalid task id!");
                }
            }

            if (trimmed.startsWith("delete")) {
                int n = parseTaskNumber(trimmed, "delete");
                Task deleted = taskList.delete(n);
                storage.save(taskList);
                return "Noted. I've removed this task:\n" + deleted
                        + "\nNow you have " + taskList.size() + " tasks in the list.";
            }

            if (trimmed.startsWith("find")) {
                String keyword = parseFindKeyword(trimmed);
                ArrayList<Integer> matches = taskList.find(keyword);

                if (matches.isEmpty()) {
                    return "No matching tasks found.";
                }

                StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                for (int taskNumber : matches) {
                    sb.append(taskNumber + 1).append(". ").append(taskList.get(taskNumber)).append("\n");
                }
                return sb.toString().trim();
            }

            // Otherwise: add task (todo/deadline/event)
            Task task = parser.parseTask(trimmed);
            taskList.add(task);
            storage.save(taskList);
            return "Got it. I've added this task:\n" + task
                    + "\nNow you have " + taskList.size() + " tasks in the list.";

        } catch (MinnieException e) {
            return e.getMessage();
        }
    }

    private int parseTaskNumber(String input, String commandWord) throws MinnieException {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MinnieException("Please provide a task number after '" + commandWord + "'.");
        }
        try {
            int n = Integer.parseInt(parts[1].trim());
            if (n <= 0) {
                throw new MinnieException("Task number must be a positive integer.");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new MinnieException("Task number must be an integer.");
        }
    }

    private String parseFindKeyword(String input) throws MinnieException {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MinnieException("Please provide a keyword after 'find'.");
        }
        return parts[1].trim();
    }
}