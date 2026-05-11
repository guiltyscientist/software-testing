package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferModelWithAdapter extends RingBufferModel {

    private RingBuffer<String> buffer;

    public RingBufferModelWithAdapter(int capacity) {
        super(capacity);
        buffer = new RingBuffer<>(capacity);
    }

    @Override
    public void reset(boolean testing) {
        super.reset(testing);
        buffer = new RingBuffer<>(capacity);
    }

    @Override
    @Action
    public void enqueue() {
        super.enqueue();
        buffer.enqueue("A");
        assertEquals(super.size, buffer.size());
    }

    @Override
    @Action
    public void dequeue() {
        super.dequeue();
        String dequeued = buffer.dequeue();
        assertEquals(super.size, buffer.size());
        assertEquals("A", dequeued);
        if (size == capacity) {
            assertTrue(buffer.isFull());
        } else if (size == 0) {
            assertTrue(buffer.isEmpty());
        }
    }

    public boolean dequeueGuard() {
        return size > 0;
    }

    @Override
    @Action
    public void dequeueFromEmptyBuffer() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            buffer.dequeue();
        });
        assertEquals("Empty ring buffer.", ex.getMessage());
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Override
    @Action
    public void peek() {
        int sizeBefore = super.size;
        super.peek();
        String peeked = buffer.peek();
        assertEquals(sizeBefore, buffer.size());
        assertEquals("A", peeked);
    }

    public boolean peekGuard() {
        return size > 0;
    }

    @Override
    @Action
    public void peekFromEmptyBuffer() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            buffer.peek();
        });
        assertEquals("Empty ring buffer.", ex.getMessage());
    }

    public boolean peekFromEmptyBufferGuard() {
        return size == 0;
    }
}
