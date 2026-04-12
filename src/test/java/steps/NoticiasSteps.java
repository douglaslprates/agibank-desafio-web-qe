package steps;

import hooks.Hooks;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.NoticiasPage;

import static org.junit.Assert.assertTrue;

public class NoticiasSteps {

    private WebDriver driver;
    private HomePage homePage;
    private NoticiasPage noticiasPage;

    private void inicializarContextoSeNecessario() {
        if (driver != null && homePage != null && noticiasPage != null) {
            return;
        }

        driver = Hooks.getDriver();
        homePage = new HomePage(driver);
        noticiasPage = new NoticiasPage(driver);
    }

    @Quando("o usuario clica no menu Noticias")
    public void usuarioClicaNoMenuNoticias() {
        inicializarContextoSeNecessario();
        homePage.clicarMenuNoticias();
    }

    @Entao("a pagina Noticias do Agibank e exibida")
    public void paginaNoticiasDoAgibankExibida() {
        inicializarContextoSeNecessario();
        assertTrue(
            "Pagina de noticias do Agibank nao foi exibida. URL atual: " + homePage.getUrl(),
            noticiasPage.paginaExibida()
        );
    }
}
