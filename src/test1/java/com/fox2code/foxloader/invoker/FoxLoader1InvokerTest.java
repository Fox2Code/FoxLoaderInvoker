package com.fox2code.foxloader.invoker;

import org.junit.jupiter.api.Test;

/**
 * Test passes if no exception is thrown by the invoker.
 */
public class FoxLoader1InvokerTest {
	@Test
	public void testFoxLoader1() {
		try {
			Main.main(new String[] { "/dev/null", "/dev/null" });
		} catch (Exception e) {
			throw new RuntimeException("Test failed", e);
		}
	}
}
