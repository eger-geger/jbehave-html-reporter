package org.jbehave.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jbehave.core.reporters.StepdocReporter;
import org.jbehave.core.steps.Stepdoc;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang.StringUtils.splitByCharacterTypeCamelCase;

public class HTMLStepdocReporter implements StepdocReporter {

    private static final String DATA_FILE_NAME = "stepdocs.json";

    private final ObjectMapper objectMapper;

    private final Path outputPath;

    public HTMLStepdocReporter(Path outputPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);

        this.objectMapper = objectMapper;
        this.outputPath = outputPath;
    }

    public void stepdocs(List<Stepdoc> stepdocs, List<Object> stepsInstances) {
        List<Map> steps = new ArrayList<>();

        for (Stepdoc stepdoc : stepdocs) {
            steps.add(convertStepDoc(stepdoc));
        }

        try {
            new ClassPathResources("org.jbehave.reports")
                    .copy(".*\\.css", outputPath.resolve("css"))
                    .copy(".*\\.js", outputPath.resolve("js"))
                    .copy("index.html", outputPath);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to copy resources", ex);
        }

        try (Writer wr = createJsonWriter()) {
            objectMapper.writer().withDefaultPrettyPrinter().writeValue(wr, steps);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to write step data");
        }
    }

    public void stepdocsMatching(String stepAsString, List<Stepdoc> matching, List<Object> stepsIntances) {
    }

    private Writer createJsonWriter() throws IOException {
        return Files.newBufferedWriter(outputPath.resolve(DATA_FILE_NAME),
                Charset.forName("UTF-8"));
    }

    private Map<String, Object> convertStepDoc(Stepdoc stepdoc) {
        HashMap<String, Object> stepMap = new HashMap<>();

        stepMap.put("type", stepdoc.getStepType());
        stepMap.put("pattern", stepdoc.getPattern());
        stepMap.put("method", stepdoc.getMethod().getName());
        stepMap.put("library", join(splitByCharacterTypeCamelCase(stepdoc
                .getMethod().getDeclaringClass().getSimpleName()), ' '));

        return stepMap;
    }
}
