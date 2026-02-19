package minnie;

import java.util.ArrayList;

public class TaskList {

    private static final String ERROR_TASK_NUMBER_OUT_OF_RANGE = "Task number is out of range.";

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from the given tasks.
     * A defensive copy is made to avoid unexpected external mutation.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "tasks should not be null";
        this.tasks = new ArrayList<>(tasks);
    }

    public Task add(Task task) {
        assert task != null : "task should not be null";
        this.tasks.add(task);
        return task;
    }

    public int size() {
        return this.tasks.size();
    }

    /**
     * Gets a task by its zero-based index (0..size-1).
     */
    public Task get(int zeroBasedIndex) {
        return this.tasks.get(zeroBasedIndex);
    }

    public Task mark(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);

        Task task = tasks.get(zeroBasedIndex);
        task.markAsDone();
        return task;
    }

    public Task unmark(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);

        Task task = tasks.get(zeroBasedIndex);
        task.markAsNotDone();
        return task;
    }

    public Task delete(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);

        return tasks.remove(zeroBasedIndex);
    }

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