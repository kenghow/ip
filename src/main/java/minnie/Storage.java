package minnie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading tasks from disk and saving tasks to disk.
 * Stores data in a simple line-based format under relative file path.
 */
public class Storage {
    private final Path filePath;

    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Loads tasks from the data file.
     * If the file does not exist, returns an empty task list.
     * @return Tasks loaded from disk.
     * @throws MinnieException If an I/O error occurs or the file format is invalid.
     */
    public ArrayList<Task> load() throws MinnieException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            ArrayList<Task> tasks = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                tasks.add(parseLine(line));
            }

            return tasks;
        } catch (IOException e) {
            throw new MinnieException("Unable to load tasks from disk.");
        }
    }

    /**
     * Saves the current task list to disk, overwriting the existing file content.
     * Creates the parent directory if it does not exist.
     * @param taskList The task list to persist.
     * @throws MinnieException If an I/O error occurs while saving.
     */
    public void save(TaskList taskList) throws MinnieException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (int i = 0; i < taskList.size(); i++) {
                lines.add(serialize(taskList.get(i)));
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new MinnieException("Unable to save tasks to disk.");
        }
    }

    private Task parseLine(String line) throws MinnieException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new MinnieException("Data file is corrupted: " + line);
        }

        String type = parts[0];
        boolean isDone = parseDoneFlag(parts[1]);
        String desc = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new MinnieException("Data file is corrupted: " + line);
                }
                try {
                    LocalDate by = LocalDate.parse(parts[3]);
                    task = new Deadline(desc, by);
                } catch (DateTimeParseException e) {
                    throw new MinnieException("Data file has an invalid date: " + parts[3]);
                }
                break;
            case "E":
                if (parts.length < 5) {
                    throw new MinnieException("Data file is corrupted: " + line);
                }
                task = new Event(desc, parts[3], parts[4]);
                break;
            default:
                throw new MinnieException("Data file is corrupted: " + line);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    private boolean parseDoneFlag(String flag) throws MinnieException {
        if (flag.equals("0")) {
            return false;
        }
        if (flag.equals("1")) {
            return true;
        }
        throw new MinnieException("Data file is corrupted: invalid done flag");
    }

    private String serialize(Task task) throws MinnieException {
        String done = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        }
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        }
        if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        }

        throw new MinnieException("Unknown task type.");
    }
}
