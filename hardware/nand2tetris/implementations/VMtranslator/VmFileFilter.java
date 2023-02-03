package implementations.VMtranslator;

import java.io.File;
import java.io.FilenameFilter;

public class VmFileFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        if (name.endsWith(".vm")) {
            return true;
        }

        return false;
    }
}
