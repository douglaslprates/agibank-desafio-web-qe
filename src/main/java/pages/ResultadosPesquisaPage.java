package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;

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
        return getUrl().contains("?s=");
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

        String termoBusca = normalizarTexto(termo);
        return titulos.stream()
            .map(el -> normalizarTexto(el.getText()))
            .anyMatch(titulo -> titulo.contains(termoBusca));
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

    private String normalizarTexto(String valor) {
        String corrigido = corrigirMojibake(valor);
        String semAcento = Normalizer.normalize(corrigido, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");
        return semAcento.toLowerCase().trim();
    }

    private String corrigirMojibake(String valor) {
        if (valor == null) {
            return "";
        }
        if (valor.contains("Ãƒ") || valor.contains("Ã‚")) {
            return new String(valor.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return valor;
    }
}
