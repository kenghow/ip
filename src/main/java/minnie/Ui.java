package minnie;

/**
 * Handles all user-facing input/output for the chatbot.
 * Centralizes printed messages to keep core logic separate from presentation.
 */
public class Ui {
    private static final String LINE = "__________________________________________________________";

    /**
     * Prints the welcome message shown when the application starts.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Minnie");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }

    public void echo(String userInput) {
        System.out.println(LINE);
        System.out.println(" " + userInput);
        System.out.println(LINE);
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showAdded(Task task, int totalTasks) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(" Now you have " + totalTasks + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println(LINE);
    }

    public void showMarked(Task task) {
        System.out.println(LINE);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    public void showUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    public void showDeleted(Task task, int totalTasks) {
        System.out.println(LINE);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + totalTasks + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Prints an error message to the user.
     * @param message Explanation of what went wrong.
     */
    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("Oopss! " + message);
        System.out.println(LINE);
    }
}
