package nl.han.ica.datastructures;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HanStackTest {
    private HANStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new HANStack<>();
    }

    @Test
    public void testPush() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.peek());
    }

    @Test
    public void testPop() {
        //act
        stack.push(1);
        stack.push(2);
        stack.push(3);
        //assert
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void testPeekAfterRemovingTOS() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
        stack.pop();
        assertEquals(1, stack.peek());
    }
}

