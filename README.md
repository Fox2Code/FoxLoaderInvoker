# FoxLoaderInvoker

Backward compatible invoker to patch ReIndev with FoxLoader in a develomment environment from an external source.

## Usage

### For FoxLoader 1.0:

`java -cp "FoxLoaderInvoker.jar:FoxLoader-common.jar:asm.jar" com.fox2code.foxloader.invoker.Main <input> <output> <unpick> <client>`

Note: Replace `:` with `;` on Windows  
Note²: All ASM libs are needed.  

### For FoxLoader 2.0+:

`java -cp "FoxLoaderInvoker.jar:FoxLoader-loader.jar:asm.jar" com.fox2code.foxloader.invoker.Main <input> <output> <unpick>`

Note: Replace `:` with `;` on Windows  
Note²: All ASM libs are needed.  

### Special note

For FoxLoader 2.0+ you can use FoxLoader directly without FoxLoaderInvoker by doing:

`java -cp "FoxLoader-loader.jar:asm.jar" com.fox2code.foxloader.patching.PatchBridge <input> <output> <unpick>`

I will try to keep this API future-proof in FoxLoader itself
