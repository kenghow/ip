package minnie;

import java.util.ArrayList;

public class Minnie {

    private static final String DEFAULT_FILE_PATH = "data/minnie.txt";

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";

    private String startupWarning = "";

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
            startupWarning = "Note: I could not load your saved data. Starting fresh.";
            loaded = new TaskList();
        }
        taskList = loaded;
    }

    public String getWelcomeMessage() {
        return "Hello! I'm Minnie.\nWhat can I do for you?" + (startupWarning.isEmpty() ? "" : "\n" + startupWarning);
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

            String commandWord = firstWord(trimmed);

            switch (commandWord) {
                case COMMAND_BYE:
                    return "Bye. Hope to see you again soon!";
                case COMMAND_LIST:
                    return handleList();
                case COMMAND_MARK:
                    return handleMarkUnmark(trimmed, true);
                case COMMAND_UNMARK:
                    return handleMarkUnmark(trimmed, false);
                case COMMAND_DELETE:
                    return handleDelete(trimmed);
                case COMMAND_FIND:
                    return handleFind(trimmed);
                default:
                    return handleAddTask(trimmed);
            }
        } catch (MinnieException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e) {
            // Defensive: prevents GUI from “freezing” due to uncaught runtime exceptions.
            return e.getMessage();
        }
    }

    private String handleList() {
        return taskList.toString();
    }

    private String handleMarkUnmark(String input, boolean isMark) throws MinnieException {
        int oneBasedIndex = parseTaskNumber(input, isMark ? COMMAND_MARK : COMMAND_UNMARK);

        Task updated = isMark ? taskList.mark(oneBasedIndex) : taskList.unmark(oneBasedIndex);
        storage.save(taskList);

        if (isMark) {
            return "Nice! I've marked this task as done:\n" + updated;
        }
        return "OK, I've marked this task as not done yet:\n" + updated;
    }

    private String handleDelete(String input) throws MinnieException {
        int oneBasedIndex = parseTaskNumber(input, COMMAND_DELETE);
        Task deleted = taskList.delete(oneBasedIndex);
        storage.save(taskList);

        return "Noted. I've removed this task:\n" + deleted
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }

    private String handleFind(String input) throws MinnieException {
        String keyword = parseFindKeyword(input);
        ArrayList<Integer> matches = taskList.find(keyword);

        if (matches.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int zeroBasedIndex : matches) {
            sb.append(zeroBasedIndex + 1)
                    .append(". ")
                    .append(taskList.get(zeroBasedIndex))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    private String handleAddTask(String input) throws MinnieException {
        Task task = parser.parseTask(input);
        if (isDuplicateTask(task)) {
            return "This task already exists. I didn't add a duplicate:\n" + task;
        }
        taskList.add(task);
        storage.save(taskList);

        return "Got it. I've added this task:\n" + task
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }

    private String firstWord(String input) {
        String[] parts = input.split("\\s+", 2);
        return parts[0];
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

    private boolean isDuplicateTask(Task newTask) {
        for (int i = 0; i < taskList.size(); i++) {
            Task existing = taskList.get(i);
            if (existing.toString().equals(newTask.toString())) {
                return true;
            }
        }
        return false;
    }
}