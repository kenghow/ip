package minnie;

/**
 * Represents errors specific to the Minnie chatbot domain,
 * such as invalid commands or storage format issues.
 */
public class MinnieException extends Exception {
    public MinnieException(String message) {
        super(message);
    }
}
