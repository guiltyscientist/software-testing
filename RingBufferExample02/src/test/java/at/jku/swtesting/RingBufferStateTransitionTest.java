package at.jku.swtesting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * State transition tests for the RingBuffer class.
 * Based on the state transition diagram with states: Empty, Filled, Full.
 * Each test case corresponds to one logical test case from RingBufferStateTransitionTests.txt.
 */
public class RingBufferStateTransitionTest {

    @Test
    void tc1_emptyToFilledViaEnqueue() {
        RingBuffer<String> buffer = new RingBuffer<>(3);

        buffer.enqueue("a");

        assertEquals(1, buffer.size());
        assertEquals("a", buffer.peek());
        assertFalse(buffer.isEmpty());
        assertFalse(buffer.isFull());
    }

    @Test
    void tc2_emptyToFullViaEnqueueCapacityOne() {
        RingBuffer<String> buffer = new RingBuffer<>(1);

        buffer.enqueue("a");

        assertEquals(1, buffer.size());
        assertEquals("a", buffer.peek());
        assertTrue(buffer.isFull());
        assertFalse(buffer.isEmpty());
    }

    @Test
    void tc3_emptyToFilledToEmptyViaEnqueueDequeue() {
        RingBuffer<String> buffer = new RingBuffer<>(3);

        buffer.enqueue("a");
        String result = buffer.dequeue();

        assertEquals("a", result);
        assertEquals(0, buffer.size());
        assertTrue(buffer.isEmpty());
    }

    @Test
    void tc4_filledStaysFilledViaEnqueue() {
        RingBuffer<String> buffer = new RingBuffer<>(3);

        buffer.enqueue("a");
        buffer.enqueue("b");

        assertEquals(2, buffer.size());
        assertEquals("a", buffer.peek());
        assertFalse(buffer.isFull());
        assertFalse(buffer.isEmpty());
    }

    @Test
    void tc5_filledToFullViaEnqueue() {
        RingBuffer<String> buffer = new RingBuffer<>(3);

        buffer.enqueue("a");
        buffer.enqueue("b");
        buffer.enqueue("c");

        assertEquals(3, buffer.size());
        assertEquals("a", buffer.peek());
        assertTrue(buffer.isFull());
    }

    @Test
    void tc6_fullToFilledViaDequeue() {
        RingBuffer<String> buffer = new RingBuffer<>(3);
        buffer.enqueue("a");
        buffer.enqueue("b");
        buffer.enqueue("c");

        String result = buffer.dequeue();

        assertEquals("a", result);
        assertEquals(2, buffer.size());
        assertFalse(buffer.isFull());
        assertFalse(buffer.isEmpty());
    }

    @Test
    void tc7_fullStaysFullViaEnqueueOverwrite() {
        RingBuffer<String> buffer = new RingBuffer<>(3);
        buffer.enqueue("a");
        buffer.enqueue("b");
        buffer.enqueue("c");

        buffer.enqueue("x"); // overwrites "a"

        assertEquals(3, buffer.size());
        assertTrue(buffer.isFull());
        assertEquals("b", buffer.peek()); // "a" was overwritten, "b" is now first
    }

    @Test
    void tc8_fullToEmptyViaDequeueCapacityOne() {
        RingBuffer<String> buffer = new RingBuffer<>(1);
        buffer.enqueue("a");

        String result = buffer.dequeue();

        assertEquals("a", result);
        assertEquals(0, buffer.size());
        assertTrue(buffer.isEmpty());
    }

    @Test
    void tc9_peekOnFilledNoStateChange() {
        RingBuffer<String> buffer = new RingBuffer<>(3);
        buffer.enqueue("a");
        buffer.enqueue("b");

        String result = buffer.peek();

        assertEquals("a", result);
        assertEquals(2, buffer.size()); // unchanged
        assertFalse(buffer.isFull());
        assertFalse(buffer.isEmpty());
    }

    @Test
    void tc10_peekOnFullNoStateChange() {
        RingBuffer<String> buffer = new RingBuffer<>(3);
        buffer.enqueue("a");
        buffer.enqueue("b");
        buffer.enqueue("c");

        String result = buffer.peek();

        assertEquals("a", result);
        assertEquals(3, buffer.size()); // unchanged
        assertTrue(buffer.isFull());
    }

    @Test
    void tc11_dequeueOnEmptyThrowsException() {
        RingBuffer<String> buffer = new RingBuffer<>(3);

        RuntimeException ex = assertThrows(RuntimeException.class, buffer::dequeue);

        assertEquals("Empty ring buffer.", ex.getMessage());
        // state remains Empty
        assertEquals(0, buffer.size());
        assertTrue(buffer.isEmpty());
    }

    @Test
    void tc12_peekOnEmptyThrowsException() {
        RingBuffer<String> buffer = new RingBuffer<>(3);

        RuntimeException ex = assertThrows(RuntimeException.class, buffer::peek);

        assertEquals("Empty ring buffer.", ex.getMessage());
        // state remains Empty
        assertEquals(0, buffer.size());
        assertTrue(buffer.isEmpty());
    }
}
