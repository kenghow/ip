package minnie;

import java.util.ArrayList;

/**
 * Represents an in-memory list (collection) of tasks and provides operations
 * to add, remove and update tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task into the list.
     * @param task Task to be added.
     * @return Task added (to be printed)
     */
    public Task add(Task task) {
        tasks.add(task);
        return task;
    }

    /**
     * Returns the number of tasks currently stored.
     * @return Task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given 0-based index (for internal use).
     * @param index 0-based index
     * @return The task at the index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Marks the task at the given 1-based task number as done
     * @param oneBasedIndex taskNumber 1-based index shown to the user.
     * @return The updated task.
     */
    public Task mark(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);
        Task task = tasks.get(zeroBasedIndex);
        task.markAsDone();
        return task;
    }

    /**
     * Unarks the task at the given 1-based task number as undone
     * @param oneBasedIndex taskNumber 1-based index shown to the user.
     * @return The updated task.
     */
    public Task unmark(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);
        Task task = tasks.get(zeroBasedIndex);
        task.markAsNotDone();
        return task;
    }

    /**
     * Delete the task at the given 1-based task number.
     * @param oneBasedIndex taskNumber 1-based index shown to the user.
     * @return The updated task.
     */
    public Task delete(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);
        return tasks.remove(zeroBasedIndex);
    }

    public ArrayList<Integer> find(String keyword) {
        ArrayList<Integer> matches = new ArrayList<>();
        String needle = keyword.toLowerCase();

        for (int i = 0; i < tasks.size(); i++) {
            String haystack = tasks.get(i).getDescription().toLowerCase();
            if (haystack.contains(needle)) {
                matches.add(i);
            }
        }
        return matches;
    }

    private void ensureValidIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number is out of range");
        }
    }
}
