package com.fox2code.foxloader.invoker;

import org.junit.jupiter.api.Test;

/**
 * Test passes if no exception is thrown by the invoker.
 */
public class FoxLoader1InvokerTest {
	@Test
	public void test1FoxLoader1() {
		try {
			Main.main(new String[] { "/dev/null", "/dev/null", "false" });
		} catch (Exception e) {
			throw new RuntimeException("Test failed", e);
		}
	}

	@Test
	public void test2FoxLoader1() {
		try {
			Main.main(new String[] { "/dev/null", "/dev/null", "false", "true" });
		} catch (Exception e) {
			throw new RuntimeException("Test failed", e);
		}
	}

	@Test
	public void test3FoxLoader1() {
		try {
			Main.main(new String[] { "/dev/null", "/dev/null", "false", "false" });
		} catch (Exception e) {
			throw new RuntimeException("Test failed", e);
		}
	}

	@Test
	public void test4FoxLoader1() {
		try {
			Main.main(new String[] { "/dev/null", "/dev/null", "false", "TRUE" });
		} catch (Exception e) {
			throw new RuntimeException("Test failed", e);
		}
	}

	@Test
	public void test5FoxLoader1() {
		try {
			Main.main(new String[] { "/dev/null", "/dev/null", "false", "FALSE" });
		} catch (Exception e) {
			throw new RuntimeException("Test failed", e);
		}
	}
}
