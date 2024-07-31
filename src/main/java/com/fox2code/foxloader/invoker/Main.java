package com.fox2code.foxloader.invoker;

import com.fox2code.foxloader.loader.PreLoader;
import com.fox2code.foxloader.patching.PatchBridge;

import java.io.File;
import java.io.IOException;

public final class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("For FoxLoader 1.0: <in> <out> <unpick> <client>");
            System.out.println("For FoxLoader 2.0+: <in> <out> <unpick>");
            System.exit(0);
            return;
        } else if (args.length < 3) {
            System.out.println("Not enough arguments, require input and output");
            System.exit(-1);
            return;
        }
        File input = new File(args[0]).getAbsoluteFile();
        File output = new File(args[1]).getAbsoluteFile();
        boolean unpick = Boolean.parseBoolean(args[2]);
        if (args.length == 3) {
            PatchBridge.patch(input, output, unpick);
        } else if (args.length == 4) {
            boolean client = Boolean.parseBoolean(args[3]);
            if (unpick) {
                PreLoader.patchDevReIndevForSource(input, output);
            } else {
                PreLoader.patchReIndevForDev(input, output, client);
            }
        } else {
            System.out.println("Too many arguments");
            System.exit(-1);
        }
    }
}