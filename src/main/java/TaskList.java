public class TaskList {
    private static final int CAPACITY = 100;

    private final Task[] tasks;
    private int size;

    public TaskList() {
        tasks = new Task[CAPACITY];
        size = 0;
    }

    public Task add(Task task) {
        tasks[size] = task;
        size++;
        return task;
    }

    public int size() {
        return size;
    }

    public Task get(int index) {
        return tasks[index];
    }

    public Task mark(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);
        Task task = tasks[zeroBasedIndex];
        task.markAsDone();
        return task;
    }

    public Task unmark(int oneBasedIndex) {
        int zeroBasedIndex = oneBasedIndex - 1;
        ensureValidIndex(zeroBasedIndex);
        Task task = tasks[zeroBasedIndex];
        task.markAsNotDone();
        return task;
    }

    private void ensureValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Task number is out of range");
        }
    }
}
