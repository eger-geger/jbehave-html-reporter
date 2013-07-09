package tests;

import org.junit.Test;

public class ReportGenerationTests {

    @Test
    public void testReport() {
        new ReportEmbedder().configuredEmbedder().reportStepdocs();
    }

}
