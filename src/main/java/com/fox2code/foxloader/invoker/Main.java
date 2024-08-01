package com.fox2code.foxloader.invoker;

import java.io.File;
import java.lang.reflect.Method;

public final class Main {
	private static Class<?> PreLoader;
	private static Method PreLoader$patchDevReIndevForSource, PreLoader$patchReIndevForDev;
	private static Class<?> PatchBridge;
	private static Method PatchBridge$patch;

	static {
		try {
			PreLoader = java.lang.Class.forName("com.fox2code.foxloader.loader.PreLoader");
			PreLoader$patchDevReIndevForSource = PreLoader.getDeclaredMethod(
					"patchDevReIndevForSource", File.class, File.class);
			PreLoader$patchReIndevForDev = PreLoader.getDeclaredMethod(
					"patchReIndevForDev", File.class, File.class, boolean.class);
		} catch (ClassNotFoundException ignored) {
			try {
				PatchBridge = java.lang.Class.forName("com.fox2code.foxloader.patching.PatchBridge");
				PatchBridge$patch = PatchBridge.getDeclaredMethod(
						"patch", File.class, File.class, boolean.class);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Found neither PreLoader nor PatchBridge. Is FoxLoader on the classpath?", e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Found PatchBridge but not patch method.", e);
			}
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Found PreLoader but not patch methods.", e);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			showUsage();
			System.exit(0);
			return;
		} else if (args.length < 3) {
			showUsage();
			throw new IllegalArgumentException("Not enough arguments, requires at least input and output");
		}

		File input = new File(args[0]).getAbsoluteFile();
		File output = new File(args[1]).getAbsoluteFile();
		boolean unpick = Boolean.parseBoolean(args[2]);

		if (args.length == 3 && PatchBridge != null && PatchBridge$patch != null) {
			System.out.println("Patching with FoxLoader 2");
			PatchBridge$patch.invoke(PatchBridge, input, output, unpick);
			return;
		}

		if (args.length > 4) {
			showUsage();
			throw new IllegalArgumentException("Too many arguments");
		}

		if (PreLoader != null) {
			if (unpick && PreLoader$patchDevReIndevForSource != null) {
				System.out.println("Patching for source with FoxLoader 1");
				PreLoader$patchDevReIndevForSource.invoke(input, output);
				return;
			} else if (PreLoader$patchReIndevForDev != null) {
				boolean client = args.length == 4 && Boolean.parseBoolean(args[3]);
				System.out.println("Patching for dev with FoxLoader 1");
				PreLoader$patchReIndevForDev.invoke(PreLoader, input, output, client);
				return;
			}
		}

		throw new RuntimeException("FOXLOADER PATCHING FAILED");
	}

	private static void showUsage() {
		System.out.println("For FoxLoader 1.0: <in> <out> <unpick> <client>");
		System.out.println("For FoxLoader 2.0+: <in> <out> <unpick>");
	}
}
