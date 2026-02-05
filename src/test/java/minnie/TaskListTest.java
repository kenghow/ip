package minnie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TaskListTest {

    @Test
    public void delete_validIndex_removesCorrectTaskAndDecrementsSize() {
        TaskList list = new TaskList();
        list.add(new Todo("a"));
        list.add(new Todo("b"));
        list.add(new Todo("c"));

        Task deleted = list.delete(2); // 1-based index, should delete "b"

        assertEquals("[T][ ] b", deleted.toString());
        assertEquals(2, list.size());
        assertEquals("[T][ ] a", list.get(0).toString());
        assertEquals("[T][ ] c", list.get(1).toString());
    }

    @Test
    public void delete_outOfRange_throwsIndexOutOfBounds() {
        TaskList list = new TaskList();
        list.add(new Todo("a"));

        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(-1));
    }
}