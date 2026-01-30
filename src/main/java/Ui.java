public class Ui {
    private static final String LINE = "__________________________________________________________";

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
}
