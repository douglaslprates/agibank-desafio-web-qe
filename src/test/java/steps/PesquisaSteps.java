package steps;

import hooks.Hooks;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.ResultadosPesquisaPage;

import static org.junit.Assert.assertTrue;

public class PesquisaSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ResultadosPesquisaPage resultadosPage;

    @Before(order = 1)
    public void inicializarPages() {
        driver = Hooks.getDriver();
        homePage = new HomePage(driver);
        resultadosPage = new ResultadosPesquisaPage(driver);
        homePage.abrir();
    }

    @Dado("que o usuario esta na pagina inicial do Blog do Agi")
    public void usuarioNaPaginaInicial() {
        homePage.abrir();
        assertTrue(
            "URL incorreta: " + driver.getCurrentUrl(),
            driver.getCurrentUrl().contains("blog.agibank.com.br")
        );
    }

    @Quando("o usuario clica no icone de pesquisa")
    public void clicarIconePesquisa() {
        homePage.clicarIconePesquisa();
    }

    @E("digita o termo de busca {string}")
    public void digitarTermoBusca(String termo) {
        homePage.digitarTermoBusca(termo);
    }

    @E("confirma a pesquisa pressionando Enter")
    public void confirmarPesquisa() {
        homePage.confirmarPesquisa();
        resultadosPage.aguardarCarregamento();
    }

    @Entao("a pagina de resultados e exibida")
    public void paginaResultadosExibida() {
        assertTrue(
            "Pagina de resultados nao foi carregada.",
            resultadosPage.paginaResultadosExibida()
        );
    }

    @E("pelo menos um artigo e listado nos resultados")
    public void peloMenosUmArtigoListado() {
        assertTrue(
            "Nenhum artigo encontrado nos resultados.",
            resultadosPage.possuiResultados()
        );
    }

    @E("os artigos exibidos sao relacionados ao termo {string}")
    public void artigosRelacionadosAoTermo(String termo) {
        assertTrue(
            "Nenhum titulo contem o termo '" + termo + "'.",
            resultadosPage.titulosContemTermo(termo)
        );
    }

    @E("uma mensagem de nenhum resultado encontrado e exibida")
    public void mensagemNenhumResultado() {
        assertTrue(
            "Mensagem de 'nenhum resultado' nao encontrada.",
            resultadosPage.mensagemSemResultadosExibida()
        );
    }

    @E("nenhum card de artigo e listado na pagina")
    public void nenhumCardListado() {
        assertTrue(
            "Esperado 0 artigos, mas encontrou: " + resultadosPage.getCardsArtigos().size(),
            resultadosPage.getCardsArtigos().isEmpty()
        );
    }
}
