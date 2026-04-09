package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	@Test
	public void testConstructorWithCapacityOne() {
		RingBuffer<Integer> buffer = new RingBuffer<>(1);
		assertEquals(1, buffer.capacity());
	}

	@Test
	public void testConstructorWithLessThanOneCapacity() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new RingBuffer<>(0);
		});
		assertEquals("Initial capacity is less than one", exception.getMessage());
	}

	@Test
	public void testCorrectCapacitySize() {
		int capacity = 10;
		RingBuffer<Integer> buffer = new RingBuffer<>(capacity);
		assertEquals(capacity, buffer.capacity());
	}

	@Test
	public void testEmptySize() {
		RingBuffer buffer = new RingBuffer(100);
		assertEquals(0, buffer.size());
	}

	@Test
	public void testPartlyFilledSize() {
		int itemsCount = 50;
		RingBuffer<Integer> buffer = new RingBuffer<>(100);
		for(int i = 0; i < itemsCount; i++) {
			buffer.enqueue(i);
		}
		assertEquals(itemsCount, buffer.size());
	}

	@Test
	public void testFullyFilledSize() {
		int itemsCount = 50;
		RingBuffer<Integer> buffer = new RingBuffer<>(50);
		for(int i = 0; i < itemsCount; i++) {
			buffer.enqueue(i);
		}
		assertEquals(itemsCount, buffer.size());
	}

	@Test
	public void testBufferIsEmpty() {
		assertTrue(new RingBuffer<>(100).isEmpty());
	}

	@Test
	public void testBufferIsNotEmpty() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 5; i++) {
			buffer.enqueue(i);
		}
		assertFalse(buffer.isEmpty());
	}

	@Test
	public void testBufferIsFull() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 10; i++) {
			buffer.enqueue(i);
		}
		assertTrue(buffer.isFull());
	}

	@Test
	public void testBufferIsNotFull() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 9; i++) {
			buffer.enqueue(i);
		}
		assertFalse(buffer.isFull());
	}

	@Test
	public void testNormalEnqueueProcess() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 5; i++) {
			buffer.enqueue(i);
		}

		for (int i = 0; i < 5; i++) {
			assertEquals(i, buffer.dequeue());
		}
	}

	@Test
	public void testOverEnqueueProcess() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i <= 10; i++) {
			buffer.enqueue(i);
		}

		int idx = 0;
		for(int val : buffer) {
			if (idx == 0) {
				assertEquals(10, val);
			}  else {
				assertEquals(idx, val);
			}
			idx++;
		}
	}

	@Test
	public void testNormalDequeueProcess() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 5; i++) {
			buffer.enqueue(i);
		}

		for (int i = 0; i < 5; i++) {
			assertEquals(i, buffer.dequeue());
		}
	}

	@Test
	public void testEmptyDequeueProcess() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 3; i++) {
			buffer.enqueue(i);
		}

		for (int i = 0; i < 3; i++) {
			buffer.dequeue();
		}

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			buffer.dequeue();
		});
		assertEquals("Empty ring buffer.", exception.getMessage());
	}

	@Test
	public void testPeekFilledBuffer() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		for (int i = 0; i < 3; i++) {
			buffer.enqueue(i);
		}

		assertEquals(0, buffer.peek());
		buffer.dequeue();
		buffer.dequeue();
		assertEquals(2, buffer.peek());
	}

	@Test
	public void testPeekEmptyBuffer() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			buffer.peek();
		});

		assertEquals("Empty ring buffer.", exception.getMessage());
	}

	@Test
	public void testIteratorHasNextOnEmptyBuffer() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		assertFalse(buffer.iterator().hasNext());
	}

	@Test
	public void testIteratorHasNextOnFilledBuffer() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		buffer.enqueue(1);
		assertTrue(buffer.iterator().hasNext());
	}

	@Test
	public void testIteratorNextThrowsNoSuchElementException() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		Iterator<Integer> it = buffer.iterator();
		assertThrows(NoSuchElementException.class, () -> it.next());
	}

	@Test
	public void testIteratorRemoveThrowsUnsupportedOperationException() {
		RingBuffer<Integer> buffer = new RingBuffer<>(10);
		buffer.enqueue(1);
		Iterator<Integer> it = buffer.iterator();
		assertThrows(UnsupportedOperationException.class, () -> it.remove());
	}

	@Test
	public void testIteratorTraversesAllElements() {
		RingBuffer<Integer> buffer = new RingBuffer<>(5);
		for (int i = 0; i < 5; i++) {
			buffer.enqueue(i);
		}
		int expected = 0;
		for (int val : buffer) {
			assertEquals(expected++, val);
		}
	}

	@Test
	public void testIteratorAfterWrapAround() {
		RingBuffer<Integer> buffer = new RingBuffer<>(5);
		for (int i = 0; i < 5; i++) {
			buffer.enqueue(i);
		}
		buffer.dequeue();
		buffer.dequeue();

		buffer.enqueue(5);
		buffer.enqueue(6);

		int[] expected = { 2, 3, 4, 5, 6 };
		int idx = 0;
		for (int val : buffer) {
			assertEquals(expected[idx++], val);
		}
	}

}
