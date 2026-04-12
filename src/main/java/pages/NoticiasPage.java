package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NoticiasPage extends BasePage {

    private final By tituloNoticias = By.cssSelector("h1, .page-title, .entry-title");

    public NoticiasPage(WebDriver driver) {
        super(driver);
    }

    public boolean paginaExibida() {
        wait.until(d -> !d.getCurrentUrl().isBlank());
        String url = getUrl().toLowerCase();
        if (url.contains("noticias") || url.contains("agibank")) {
            return true;
        }

        if (!elementoPresente(tituloNoticias)) {
            return false;
        }

        String titulo = aguardarElementoVisivel(tituloNoticias).getText().toLowerCase();
        return titulo.contains("noticias") || titulo.contains("notícias") || titulo.contains("agibank");
    }
}

