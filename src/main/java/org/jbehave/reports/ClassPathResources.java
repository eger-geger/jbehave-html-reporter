package org.jbehave.reports;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;

import static org.apache.commons.io.FilenameUtils.getName;

public class ClassPathResources {

    private final Reflections reflections;

    public ClassPathResources(String packageName) {
        this.reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(new ResourcesScanner()));
    }

    public ClassPathResources copy(String fileNameRegexp, Path target) throws IOException {
        Set<String> resources = reflections.getResources(Pattern.compile(fileNameRegexp));

        Files.createDirectories(target);

        for (String resource : resources) {
            Files.copy(getClass().getClassLoader().getResourceAsStream(resource), target.resolve(getName(resource)));
        }

        return this;
    }

}
