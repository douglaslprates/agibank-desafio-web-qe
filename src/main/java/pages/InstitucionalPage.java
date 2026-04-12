package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InstitucionalPage extends BasePage {

    private final By tituloOAgibank = By.cssSelector("header a[href*='/institucional/']");

    public InstitucionalPage(WebDriver driver) {
        super(driver);
    }

    public boolean paginaExibida() {
        wait.until(d -> !d.getCurrentUrl().isBlank());
        String url = getUrl().toLowerCase();
        if (url.contains("institucional") || url.contains("agibank")) {
            return true;
        }

        if (!elementoPresente(tituloOAgibank)) {
            return false;
        }

        String titulo = aguardarElementoVisivel(tituloOAgibank).getText().toLowerCase();
        return titulo.contains("agibank");
    }
}
