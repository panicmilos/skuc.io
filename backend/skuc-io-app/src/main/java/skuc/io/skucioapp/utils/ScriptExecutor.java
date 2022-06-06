package skuc.io.skucioapp.utils;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class ScriptExecutor {

    public static void Execute(String command) {
        var cmdLine = CommandLine.parse(command);
        var defaultExecutor = new DefaultExecutor();
        defaultExecutor.setExitValue(0);
        try {
            defaultExecutor.execute(cmdLine);
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
    }
    
}
