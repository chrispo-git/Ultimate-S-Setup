package com.example.ultssetup;
import java.io.File;

/**
 * Deletes the contents of directories.
 */
public class DirectoryDeleter
{
    private DirectoryDeleter()
    {
    }

    /**
     * Deletes everything inside the given directory, but not the directory itself. Failed deletes are ignored.
     */
    public static void deleteDirectory(File file)
    {
        if (!(file.isFile()) && !(file.isDirectory())) return;
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}
