package framework.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExtentReportWrapper {
    private static final Logger log = LoggerFactory.getLogger(ExtentReportWrapper.class);

    private final ExtentReports extent;
    private final ConcurrentHashMap<String, ExtentTest> testMap = new ConcurrentHashMap<>();
    private final ExecutorService writerService = Executors.newSingleThreadExecutor();

    public ExtentReportWrapper(String reportPath) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public synchronized ExtentTest startTest(String testName, String description) {
        return startTest(testName, description, "web");
    }

    public synchronized ExtentTest startTest(String testName, String description, String type) {
        ExtentTest test = extent.createTest(testName, description);
        switch (type.toLowerCase()) {
            case "api": {
                test.assignCategory("API");
                break;
            }
            case "mobile": {
                test.assignCategory("MOBILE");
                break;
            }
            default: {
                test.assignCategory("WEB");
            }
        }
        testMap.put(testName, test);
        log.debug("Started test: {} - {}", testName, description);
        return test;
    }

    public synchronized void log(ExtentTest test, String status, String details) {
        if (test == null) {
            log.error("Test not found: {}. Skipping log.", test.getModel().getName());
            return;
        }
        switch (status.toLowerCase()) {
            case "pass":
                test.pass(details);
                break;
            case "fail":
                test.fail(details);
                break;
            case "info":
                test.info(details);
                break;
            default:
                test.info("Unknown status: " + details);
        }
        log.info("Logged step for test {}: [{}] {}", test.getModel().getName(), status, details);

        scheduleFlush();
    }

    public synchronized void updateProgress(String testName, int progress) {
        ExtentTest test = testMap.get(testName);
        if (test != null) {
            test.assignCategory("Progress: " + progress + "%");
            log.info("Updated progress for test {}: {}%", testName, progress);
            scheduleFlush();
        }
    }

    private void scheduleFlush() {
        writerService.submit(() -> {
            synchronized (extent) {
                extent.flush();
                log.debug("Report flushed to disk.");
            }
        });
    }

    public synchronized void finalizeReport() {
        log.info("Finalizing report...");
        testMap.forEach((testName, test) -> {
            log.info("Test {}: {}", testName, test.getStatus());
        });
        writerService.shutdown();
        extent.flush();
    }


}
