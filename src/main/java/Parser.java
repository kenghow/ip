public class Parser {

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
            throw new MinnieException("Deadline format: deadline <desc> /by <time>");
        }
        String desc = parts[0].trim();
        String by = parts[1].trim();
        ensureNotEmpty(desc, "The description of a deadline cannot be empty.");
        ensureNotEmpty(by, "The /by value cannot be empty");
        return new Deadline(desc, by);
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
