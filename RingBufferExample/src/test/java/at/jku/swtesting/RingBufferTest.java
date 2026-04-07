package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	@Test
	public void testConstructorWithCapacityOne() {
		RingBuffer<Integer> buffer = new RingBuffer<>(1);
		assertEquals(1, buffer.capacity());
	}

}
