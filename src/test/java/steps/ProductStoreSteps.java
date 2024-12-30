package steps;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import elements.ProductStore;
import framework.core.ExtentReportWrapper;
import framework.messages.Messages;
import org.assertj.core.api.Assertions;

public class ProductStoreSteps {
    private final Page page;
    private final ExtentReportWrapper report;
    private final ExtentTest test;

    public ProductStoreSteps(Page page, ExtentReportWrapper report, ExtentTest test) {
        this.page = page;
        this.report = report;
        this.test = test;
    }

    public void clickOnIconHome() {
        clickOnButton(ProductStore.btn_IconHome);
    }

    public void clickOnButton(String selector) {
        try {
            page.click(selector);
            report.log(test, "pass", Messages.ACTION_PERFORMED_SUCCESS + " Clicked on: " + selector);
        } catch (Exception e) {
            report.log(test, "fail", Messages.ACTION_NOT_PERFORMED + " Failed to click on: " + selector);
            throw new RuntimeException(e);
        }
    }

    public void validatePageTitleEquals(String expected) {
        try {
            String actual = page.title();
            Assertions.assertThat(actual)
                    .as(Messages.ACTION_IN_PROGRESS + " Validating page title.")
                    .isEqualTo(expected)
                    .withFailMessage("Expected title: '%s' but got: '%s'", expected, actual);
            report.log(test, "pass", Messages.ACTION_PERFORMED_SUCCESS + " Validated title: " + actual);
        } catch (AssertionError e) {
            report.log(test, "fail", Messages.ACTION_PERFORMED_WITH_ERROR + " " + e.getMessage());
            throw e;
        }
    }
}
