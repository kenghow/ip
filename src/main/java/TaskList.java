public class TaskList {
    private static final int CAPACITY = 100;

    private final String[] tasks;
    private int size;

    public TaskList() {
        tasks = new String[CAPACITY];
        size = 0;
    }

    public void add(String taskDescription) {
        tasks[size] = taskDescription;
        size++;
    }

    public int size() {
        return size;
    }

    public String get(int index) {
        return tasks[index];
    }
}
