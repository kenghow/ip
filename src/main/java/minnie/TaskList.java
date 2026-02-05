package minnie;

import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task add(Task task) {
        tasks.add(task);
        return task;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
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

    private void ensureValidIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("minnie.Task number is out of range");
        }
    }
}
