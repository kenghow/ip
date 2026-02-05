package minnie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseTask_validTodo_success() throws MinnieException {
        Parser parser = new Parser();
        Task task = parser.parseTask("todo read book");
        assertTrue(task instanceof Todo);
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void parseTask_emptyTodo_throwsException() {
        Parser parser = new Parser();
        assertThrows(MinnieException.class, () -> parser.parseTask("todo"));
    }

    @Test
    public void parseTask_unknownCommand_throwsException() {
        Parser parser = new Parser();
        assertThrows(MinnieException.class, () -> parser.parseTask("blah blah"));
    }
}
