package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Locale;

public class ResultadosPesquisaPage extends BasePage {

    private final By cardsArtigos = By.cssSelector("article.post, article.type-post");

    private final By titulosArtigos = By.cssSelector(
        "article.post h2.entry-title a, " +
        "article.type-post h2.entry-title a, " +
        "h2.ast-blog-single-element a"
    );

    private final By containerSemResultados = By.cssSelector(
        ".no-results, section.no-results, .not-found"
    );

    private final By mensagemSemResultados = By.cssSelector(
        ".no-results p, .no-results .page-content p, " +
        ".not-found p, section.no-results p"
    );

    public ResultadosPesquisaPage(WebDriver driver) {
        super(driver);
    }

    public boolean paginaResultadosExibida() {
        return !getCardsArtigos().isEmpty()
            || elementoPresente(containerSemResultados)
            || elementoPresente(mensagemSemResultados);
    }

    public List<WebElement> getCardsArtigos() {
        return driver.findElements(cardsArtigos);
    }

    public boolean possuiResultados() {
        return !getCardsArtigos().isEmpty();
    }

    public List<WebElement> getTitulosArtigos() {
        return driver.findElements(titulosArtigos);
    }

    public boolean titulosContemTermo(String termo) {
        List<WebElement> titulos = getTitulosArtigos();
        if (titulos.isEmpty()) {
            return false;
        }

        String termoBusca = termo == null ? "" : termo.toLowerCase(Locale.ROOT).trim();

        boolean tituloContemTermo = titulos.stream()
            .map(WebElement::getText)
            .map(texto -> texto == null ? "" : texto.toLowerCase(Locale.ROOT).trim())
            .anyMatch(titulo -> titulo.contains(termoBusca));
        if (tituloContemTermo) {
            return true;
        }

        String termoSlug = termoBusca.replace(" ", "-");
        boolean linkContemTermo = titulos.stream()
            .map(el -> el.getAttribute("href"))
            .map(link -> link == null ? "" : link.toLowerCase(Locale.ROOT).trim())
            .anyMatch(link -> link.contains(termoBusca) || link.contains(termoSlug));
        if (linkContemTermo) {
            return true;
        }

        String textoPagina = driver.findElement(By.tagName("body")).getText().toLowerCase(Locale.ROOT).trim();
        return textoPagina.contains(termoBusca);
    }

    public boolean mensagemSemResultadosExibida() {
        return elementoPresente(containerSemResultados) || elementoPresente(mensagemSemResultados);
    }

    public void aguardarCarregamento() {
        wait.until(driver ->
            !driver.findElements(cardsArtigos).isEmpty() ||
            !driver.findElements(containerSemResultados).isEmpty() ||
            !driver.findElements(mensagemSemResultados).isEmpty()
        );
    }
}
