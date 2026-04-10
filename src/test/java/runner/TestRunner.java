package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue     = {"steps", "hooks"},
    plugin   = {
        "pretty",
        "html:target/cucumber-reports/relatorio.html",
        "json:target/cucumber-reports/relatorio.json"
    }
)
public class TestRunner {}
