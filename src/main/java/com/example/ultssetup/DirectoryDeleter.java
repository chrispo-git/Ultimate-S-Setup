package com.example.ultssetup;
import java.io.File;

/**
 * Write a description of class DirectoryDeleter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DirectoryDeleter
{
    private DirectoryDeleter()
    {
    }

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
