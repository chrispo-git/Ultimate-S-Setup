package com.example.ultssetup;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDir
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class CopyDir
     */
    private CopyDir()
    {
        // initialise instance variables
        x = 0;
    }/**
     * Copies all files from the source directory to the destination directory.
     * Overwrites existing files and merges directories.
     *
     * @param sourceDir      The source directory path.
     * @param destinationDir The destination directory path.
     * @throws IOException If an I/O error occurs.
     */
    public static void copyDirectory(Path sourceDir, Path destinationDir) throws IOException {
        if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) {
            throw new IllegalArgumentException("Source directory does not exist or is not a directory.");
        }

        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetFile = destinationDir.resolve(sourceDir.relativize(file));
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = destinationDir.resolve(sourceDir.relativize(dir));
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
