package implementations.Compiler;

import java.io.File;
import java.io.FilenameFilter;

public class JackFileFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        if (name.endsWith(".jack")) {
            return true;
        }

        return false;
    }
}
