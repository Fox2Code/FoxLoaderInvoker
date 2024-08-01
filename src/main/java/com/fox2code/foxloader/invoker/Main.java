package com.fox2code.foxloader.invoker;

import java.io.File;
import java.lang.reflect.*;

public final class Main {
    private static Method PreLoader$patchDevReIndevForSource, PreLoader$patchReIndevForDev;
    private static Method PatchBridge$patch;

    static {
        try {
            Class<?> PreLoader = Class.forName("com.fox2code.foxloader.loader.PreLoader");
			PreLoader$patchDevReIndevForSource = PreLoader.getDeclaredMethod(
					"patchDevReIndevForSource", File.class, File.class);
			PreLoader$patchReIndevForDev = PreLoader.getDeclaredMethod(
					"patchReIndevForDev", File.class, File.class, boolean.class);
		} catch (ClassNotFoundException ignored) {
            try {
                Class<?> PatchBridge = Class.forName("com.fox2code.foxloader.patching.PatchBridge");
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
            System.out.println("For FoxLoader 1.0: <in> <out> <unpick> <client>");
            System.out.println("For FoxLoader 2.0+: <in> <out> <unpick>");
            System.exit(0);
            return;
        } else if (args.length < 3) {
            throw new IllegalArgumentException("Not enough arguments, requires at least input and output");
        }

        File input = new File(args[0]).getAbsoluteFile();
        File output = new File(args[1]).getAbsoluteFile();

        boolean unpick = Boolean.parseBoolean(args[2]);

        if (args.length == 3 && PatchBridge$patch != null) {
            PatchBridge$patch.invoke(input, output, unpick);
        } else if (args.length == 4) {
            boolean client = Boolean.parseBoolean(args[3]);
            if (unpick && PreLoader$patchDevReIndevForSource != null) {
                PreLoader$patchDevReIndevForSource.invoke(input, output);
            } else if (PreLoader$patchReIndevForDev != null) {
                PreLoader$patchReIndevForDev.invoke(input, output, client);
            }
        } else {
            throw new IllegalArgumentException("Too many arguments");
        }
    }
}
