package framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestReport {
    private static final Logger log = LoggerFactory.getLogger(TestReport.class);
    private static ExtentReportWrapper extent;
    private static TestReport instance;

    private TestReport() {
        log.debug("Initializing TestReport...");
        if (extent == null) {
            extent = new ExtentReportWrapper("reports/report_" + System.currentTimeMillis());
            log.debug("New ExtentReportWrapper instance created.");
        }
    }

    public static synchronized TestReport getInstance() {
        if (instance == null) {
            log.debug("Creating new TestReport instance.");
            instance = new TestReport();
        }
        return instance;
    }

    public ExtentReportWrapper getReport() {
        return extent;
    }
}
