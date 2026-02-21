package minnie;

/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return The description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return 'true' if done, otherwise 'false'.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon used in the UI.
     *
     * @return 'X' if done, otherwise " ".
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the string representation of this task for display.
     *
     * @return The display string.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}