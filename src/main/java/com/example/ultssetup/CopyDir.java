package com.example.ultssetup;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

public class CopyDir
{
    private CopyDir()
    {
    }/**
     * Copies all files from the source directory to the destination directory.
     * Overwrites existing files and merges directories.
     *
     * @param sourceDir      The source directory path.
     * @param destinationDir The destination directory path.
     * @throws IOException If an I/O error occurs.
     */
    public static void copyDirectory(Path sourceDir, Path destinationDir) throws IOException {
        copyDirectory(sourceDir, destinationDir, file -> {});
    }

    /**
     * Same as copyDirectory, but calls onFile with each file before it is copied.
     * Stops at the first file that fails to copy.
     */
    public static void copyDirectory(Path sourceDir, Path destinationDir, Consumer<Path> onFile) throws IOException {
        if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) {
            throw new IOException("Source directory does not exist or is not a directory: " + sourceDir);
        }

        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                onFile.accept(file);
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
