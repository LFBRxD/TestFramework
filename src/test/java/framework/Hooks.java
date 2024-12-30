package framework;

import framework.core.TestReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static Logger log = LoggerFactory.getLogger(Hooks.class);

    @BeforeAll
    public static void frameSetup() {
        TestReport.getInstance();
    }


    @AfterAll
    public static void frameTearDown() {
        TestReport.getInstance().getReport().finalizeReport();
    }

}
