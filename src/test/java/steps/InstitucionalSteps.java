package steps;

import hooks.Hooks;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.InstitucionalPage;

import static org.junit.Assert.assertTrue;

public class InstitucionalSteps {

    private WebDriver driver;
    private HomePage homePage;
    private InstitucionalPage oAgibankPage;

    private void inicializarContextoSeNecessario() {
        if (driver != null && homePage != null && oAgibankPage != null) {
            return;
        }

        driver = Hooks.getDriver();
        homePage = new HomePage(driver);
        oAgibankPage = new InstitucionalPage(driver);
    }

    @Quando("o usuario clica no menu O Agibank")
    public void usuarioClicaNoMenuOAgibank() {
        inicializarContextoSeNecessario();
        homePage.clicarMenuOAgibank();
    }

    @Entao("a pagina institucional do Agibank e exibida")
    public void paginaInstitucionalDoAgibankExibida() {
        inicializarContextoSeNecessario();
        assertTrue(
            "Pagina institucional do Agibank nao foi exibida. URL atual: " + homePage.getUrl(),
            oAgibankPage.paginaExibida()
        );
    }
}
