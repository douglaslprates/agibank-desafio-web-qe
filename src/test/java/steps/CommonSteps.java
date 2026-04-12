package steps;

import hooks.Hooks;
import pages.HomePage;
import io.cucumber.java.pt.Dado;
import org.openqa.selenium.WebDriver;
import pages.ResultadosPesquisaPage;

import static org.junit.Assert.assertTrue;

public class CommonSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ResultadosPesquisaPage resultadosPage;

    public CommonSteps() {
        this.driver = Hooks.getDriver();
        this.homePage = new HomePage(driver);
        this.resultadosPage = new ResultadosPesquisaPage(driver);
    }

    @Dado("que o usuario acessa a home do Blog do Agi")
    public void usuarioAcessaHomeBlogAgi() {
        //inicializarContextoSeNecessario();
        homePage.abrir();
        assertTrue(
                "URL da home incorreta: " + driver.getCurrentUrl(),
                driver.getCurrentUrl().contains("blog.agibank.com.br")
        );
    }
}