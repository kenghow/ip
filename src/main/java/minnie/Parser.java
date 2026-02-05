package minnie;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input strings into task objects or structured command data.
 * Ensures command formats are valid before tasks are created.
 */
public class Parser {

    /**
     * Parses a user command into a task
     * @param input Full user input line.
     * @return The parse Task instance.
     * @throws MinnieException If the input does not match a supported task format.
     */
    public Task parseTask(String input) throws MinnieException {
        String trimmed = input.trim();

        if (trimmed.equals("todo")) {
            throw new MinnieException("Please provide a description for a todo.");
        }

        if (trimmed.startsWith("todo ")) {
            String desc = trimmed.substring(5).trim();
            ensureNotEmpty(desc, "The description of a todo cannot be empty.");
            return new Todo(desc);
        }

        if (trimmed.equals("deadline")) {
            throw new MinnieException("Please provide a description and /by for a deadline");
        }

        if (trimmed.startsWith("deadline ")) {
            return parseDeadline(trimmed.substring(9).trim());
        }

        if (trimmed.equals("event")) {
            throw new MinnieException("Please provide a description, /from, and /to for an event.");
        }

        if (trimmed.startsWith("event ")) {
            return parseEvent(trimmed.substring(6).trim());
        }

        throw new MinnieException("I don't understand this command.");
    }

    private Deadline parseDeadline(String rest) throws MinnieException {
        String[] parts = rest.split(" /by", 2);
        if (parts.length < 2) {
            throw new MinnieException("Deadline format: deadline <desc> /by <yyyy-mm-dd>");
        }
        String desc = parts[0].trim();
        String byRaw = parts[1].trim();

        ensureNotEmpty(desc, "The description of a deadline cannot be empty.");
        ensureNotEmpty(byRaw, "The /by value cannot be empty.");

        try {
            LocalDate by = LocalDate.parse(byRaw);
            return new Deadline(desc, by);
        } catch (DateTimeParseException e) {
            throw new MinnieException("Please use date format yyyy-mm-dd, eg 2026-09-01.");
        }
    }

    private Event parseEvent(String rest) throws MinnieException {
        String[] fromSplit = rest.split(" /from ", 2);
        if (fromSplit.length < 2) {
            throw new MinnieException("Event format: event <desc> /from <start> /to <end>");
        }
        String desc = fromSplit[0].trim();

        String[] toSplit = fromSplit[1].split(" /to ", 2);
        if (toSplit.length < 2) {
            throw new MinnieException("Event format: event <desc> /from <start> /to <end>");
        }
        String from = toSplit[0].trim();
        String to = toSplit[1].trim();

        ensureNotEmpty(desc, "The description of an event cannot be empty.");
        ensureNotEmpty(from, "The /from value cannot be empty");
        ensureNotEmpty(to, "The /to value cannot be empty");

        return new Event(desc, from, to);
    }

    private void ensureNotEmpty(String s, String message) throws MinnieException {
        if (s.isEmpty()) {
            throw new MinnieException(message);
        }
    }
}
