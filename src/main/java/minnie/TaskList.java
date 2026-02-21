package minnie;

import java.util.ArrayList;

/**
 * Represents an in-memory list of tasks and provides operation to manage them.
 */
public class TaskList {

    private static final String ERROR_TASK_NUMBER_OUT_OF_RANGE = "Task number is out of range.";

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialized with the given tasks.
     *
     * @param tasks The initial tasks to load into the list.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "tasks should not be null";
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list
     *
     * @param task The task to add
     * @return The added task.
     */
    public Task add(Task task) {
        assert task != null : "task should not be null";
        this.tasks.add(task);
        return task;
    }

    /**
     * Returrns the number of tasks in the list.
     *
     * @return The task count.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns the task at the given index (0-based)
     *
     * @param zeroBasedIndex The 0-based index.
     * @return The task at the given index.
     */
    public Task get(int zeroBasedIndex) {
        return this.tasks.get(zeroBasedIndex);
    }

    /**
     * Marks the specified task as done (1-based index).
     *
     * @param oneBasedIndex The 1-based task number shown in the UI.
     * @return The updated task.
     */
    public Task mark(int oneBasedIndex) {
        assert oneBasedIndex > 0 : "taskNumber should be positive";
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);

        Task task = tasks.get(zeroBasedIndex);
        assert task != null : "task at index should not be null";
        
        task.markAsDone();
        return task;
    }

    /**
     * Marks the specified task as not done (1-based index)
     *
     * @param oneBasedIndex The 1-based task number shown in the UI.
     * @return The updated task.
     */
    public Task unmark(int oneBasedIndex) {
        assert oneBasedIndex > 0 : "taskNumber should be positive";
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);

        Task task = tasks.get(zeroBasedIndex);
        assert task != null : "task at index should not be null";

        task.markAsNotDone();
        return task;
    }

    /**
     * Deletes the specified task (1-based index).
     *
     * @param oneBasedIndex The 1-based task nubmer shown in the UI.
     * @return The deleted task.
     */
    public Task delete(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);

        return tasks.remove(zeroBasedIndex);
    }

    /**
     * Finds tasks whose descriptions contain the given keyword (case-insensitive)
     *
     * @param keyword The keyword to search for.
     * @return A list of matching task indices (0-based)
     */
    public ArrayList<Integer> find(String keyword) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().contains(keyword)) {
                results.add(i); // zero-based index
            }
        }
        return results;
    }

    private void ensureValidIndex(int zeroBasedIndex) {
        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.size()) {
            throw new IndexOutOfBoundsException(ERROR_TASK_NUMBER_OUT_OF_RANGE);
        }
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}