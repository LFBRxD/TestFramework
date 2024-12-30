package framework;

import framework.core.ExtentReportWrapper;
import framework.core.ReadProperties;
import framework.core.TestReport;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ApiTestFixtures extends Hooks {
    private static final Logger log = LoggerFactory.getLogger(ApiTestFixtures.class);


    protected ExtentReportWrapper report = TestReport.getInstance().getReport();

    @BeforeEach
    void setup() {
        RestAssured.baseURI = ReadProperties.getInstance().getDefaulAPItUrl();
    }

    @AfterEach
    void teardown() {

    }

}
