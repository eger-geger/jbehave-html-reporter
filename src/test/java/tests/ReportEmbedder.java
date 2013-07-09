package tests;

import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.reports.HTMLStepdocReporter;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ReportEmbedder extends ConfigurableEmbedder {

    public ReportEmbedder() {
        configuration().useStepdocReporter(new HTMLStepdocReporter(Paths.get("target/step-report")));
    }

    @Override
    public void run() throws Throwable {
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new AbstractStepsFactory(configuration()) {

            protected List<Class<?>> stepsTypes() {
                return Collections.<Class<?>>singletonList((Class<?>) TestSteps.class);
            }

            public Object createInstanceOfType(Class<?> type) {
                try {
                    return type.newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

    }
}
