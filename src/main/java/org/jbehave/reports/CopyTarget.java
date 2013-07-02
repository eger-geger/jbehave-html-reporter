package org.jbehave.reports;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyTarget {

    private final Path target;

    public CopyTarget(Path target) {
        this.target = target;
    }

    public CopyTarget copyPath(final Path source) throws IOException, URISyntaxException {
        final Path copy = target.resolve(source.getFileName());

        Files.createDirectories(copy);

        Files.copy(source, copy, StandardCopyOption.REPLACE_EXISTING);

        if(Files.isDirectory(source)){
            Files.walkFileTree(source, new FileVisitor<Path>() {

                private void copyRelative(Path path) throws IOException {
                    Files.copy(path, copy.resolve(source.relativize(path)), StandardCopyOption.REPLACE_EXISTING);
                }

                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    copyRelative(dir);
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copyRelative(file);
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

            });
        }

        return this;
    }

    public CopyTarget copyClasspathResource(String path) throws IOException, URISyntaxException {
        return copyPath(Paths.get(getClass().getResource(path).toURI()));
    }
}
