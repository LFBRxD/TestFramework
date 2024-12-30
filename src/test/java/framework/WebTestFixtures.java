package framework;

import com.microsoft.playwright.*;
import framework.core.ExtentReportWrapper;
import framework.core.ReadProperties;
import framework.core.TestReport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class WebTestFixtures extends Hooks {
    private static final Logger log = LoggerFactory.getLogger(WebTestFixtures.class);

    private static final ThreadLocal<Playwright> playwrightThreadLocal = ThreadLocal.withInitial(Playwright::create);
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    protected ExtentReportWrapper report = TestReport.getInstance().getReport();

    @BeforeEach
    void setup() {
        Playwright playwright = playwrightThreadLocal.get();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(ReadProperties.getInstance().isHeadless());

        Browser browser;
        switch (ReadProperties.getInstance().getBrowser().toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(options);
                break;
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "safari":
                browser = playwright.webkit().launch(options);
                break;
            case "edge":
                browser = playwright.chromium().launch(options.setChannel("msedge"));
                break;
            case "chrome":
            default:
                browser = playwright.chromium().launch(options.setChannel("chrome"));
                break;
        }

        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        page.navigate(ReadProperties.getInstance().getDefaulWEBtUrl());

        browserThreadLocal.set(browser);
        contextThreadLocal.set(context);
        pageThreadLocal.set(page);
    }

    @AfterEach
    void teardown() {
        try {
            Page page = pageThreadLocal.get();
            BrowserContext context = contextThreadLocal.get();
            Browser browser = browserThreadLocal.get();
            Playwright playwright = playwrightThreadLocal.get();

            if (page != null) page.close();
            if (context != null) context.close();
            if (browser != null) browser.close();
            playwrightThreadLocal.remove();
            contextThreadLocal.remove();
            browserThreadLocal.remove();
            pageThreadLocal.remove();
        } catch (Exception e) {
            log.error("Erro durante o teardown: ", e);
        }
    }

    protected Page getPage() {
        return pageThreadLocal.get();
    }

    protected BrowserContext getContext() {
        return contextThreadLocal.get();
    }

    protected Browser getBrowser() {
        return browserThreadLocal.get();
    }

    protected Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }
}
