package org.jbehave.reports;

import java.nio.file.Path;

public class ReportConfig {

    private static ReportConfig instance;

    private Path outputPath;

    private ReportConfig(){}

    public Path getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    public static ReportConfig getInstance(){
        if(instance == null){
            instance = new ReportConfig();
        }
        return instance;
    }

}
