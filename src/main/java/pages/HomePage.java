package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HomePage extends BasePage {

    private String termoBuscaPendente;

    private static final String URL = "https://blog.agibank.com.br/";

    private final By iconePesquisa = By.cssSelector(".ast-search-icon .astra-search-icon");

    private final By iconePesquisaAlt = By.cssSelector(".ast-search-icon a");

    private final By campoPesquisa = By.cssSelector(
        "#search-field, input.search-field[name='s'], input[type='search'][name='s']"
    );

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void abrir() {
        driver.navigate().to(URL);
        wait.until(ExpectedConditions.urlContains("blog.agibank.com.br"));
        wait.until(d -> "complete".equals(
            ((JavascriptExecutor) d).executeScript("return document.readyState")
        ));
    }

    public void clicarIconePesquisa() {
        if (driver.getCurrentUrl() == null || driver.getCurrentUrl().equals("data:,")) {
            abrir();
        }

        if (elementoPresente(campoPesquisa)) {
            return;
        }

        try {
            WebElement icone = aguardarElementoClicavel(iconePesquisa);
            clicarViaJS(icone);
        } catch (Exception e) {
            try {
                WebElement icone = aguardarElementoClicavel(iconePesquisaAlt);
                clicarViaJS(icone);
            } catch (Exception ignored) {
            }
        }
    }

    public void digitarTermoBusca(String termo) {
        termoBuscaPendente = termo;
        WebElement campo = obterCampoPesquisaVisivel();
        if (campo == null) {
            return;
        }
        campo.clear();
        campo.sendKeys(termo);
    }

    public void confirmarPesquisa() {
        WebElement campo = obterCampoPesquisaVisivel();
        if (campo != null) {
            campo.sendKeys(Keys.ENTER);
            return;
        }

        if (termoBuscaPendente != null && !termoBuscaPendente.isBlank()) {
            String termo = URLEncoder.encode(termoBuscaPendente, StandardCharsets.UTF_8);
            driver.navigate().to(URL + "?s=" + termo);
        }
    }

    private WebElement obterCampoPesquisaVisivel() {
        for (WebElement campo : driver.findElements(campoPesquisa)) {
            if (campo.isDisplayed()) {
                return campo;
            }
        }
        return null;
    }
}
