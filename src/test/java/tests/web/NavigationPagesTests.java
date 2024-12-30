package tests.web;

import elements.ProductStore;
import framework.WebTestFixtures;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.ProductStoreSteps;

public class NavigationPagesTests extends WebTestFixtures {
    private static final Logger log = LoggerFactory.getLogger(NavigationPagesTests.class);

    @Test
    void navigateToPageHome() {
        var test = report.startTest("navigateToPageHome", "Navega para a página inicial");
        var storePage = new ProductStoreSteps(getPage(), report, test);
        var expected = "STORE";

        try {
            storePage.clickOnIconHome();
            storePage.validatePageTitleEquals(expected);
            logResults("pass", "Navegou para a página inicial com sucesso.");
        } catch (Exception e) {
            logResults("fail", "Erro ao navegar para a página inicial: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void navigateToPageAbout() {
        var test = report.startTest("navigateToPageAbout", "Navega para a página Sobre");
        var storePage = new ProductStoreSteps(getPage(), report, test);
        var expected = "About Us";

        try {
            storePage.clickOnButton(ProductStore.btn_About);
            storePage.validatePageTitleEquals(expected);
            logResults("pass", "Navegou para a página Sobre com sucesso.");
        } catch (Exception e) {
            logResults("fail", "Erro ao navegar para a página Sobre: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void navigateToPageContact() {
        var test = report.startTest("navigateToPageContact", "Navega para a página Contato");
        var storePage = new ProductStoreSteps(getPage(), report, test);
        var expected = "Contact Us";

        try {
            storePage.clickOnButton(ProductStore.btn_Contact);
            storePage.validatePageTitleEquals(expected);
            logResults("pass", "Navegou para a página Contato com sucesso.");
        } catch (Exception e) {
            logResults("fail", "Erro ao navegar para a página Contato: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void navigateToPageCart() {
        var test = report.startTest("navigateToPageCart", "Navega para a página Carrinho");
        var storePage = new ProductStoreSteps(getPage(), report, test);
        var expected = "Your Cart";

        try {
            storePage.clickOnButton(ProductStore.btn_Cart);
            storePage.validatePageTitleEquals(expected);
            logResults("pass", "Navegou para a página Carrinho com sucesso.");
        } catch (Exception e) {
            logResults("fail", "Erro ao navegar para a página Carrinho: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void navigateToPageLogin() {
        var test = report.startTest("navigateToPageLogin", "Navega para a página Login");
        var storePage = new ProductStoreSteps(getPage(), report, test);
        var expected = "Login";

        try {
            storePage.clickOnButton(ProductStore.btn_Login);
            storePage.validatePageTitleEquals(expected);
            logResults("pass", "Navegou para a página Login com sucesso.");
        } catch (Exception e) {
            logResults("fail", "Erro ao navegar para a página Login: " + e.getMessage());
            throw e;
        }
    }

    private void logResults(String status, String message) {
        switch (status.toLowerCase()) {
            case "pass":
                log.info(message);
                break;
            case "fail":
                log.error(message);
                break;
            case "info":
            default:
                log.info(message);
        }
    }
}
