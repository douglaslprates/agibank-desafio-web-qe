package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

import java.nio.charset.StandardCharsets;

public class Hooks {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    @Before(order = 0)
    public void inicializarDriver() {
        String browser = getConfig("browser", "BROWSER", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(getConfig("headless", "HEADLESS", "true"));

        DRIVER.set(DriverFactory.createDriver(browser, headless));
        DRIVER.get().manage().window().maximize();
    }

    @After(order = 0)
    public void encerrarDriver(Scenario scenario) {
        WebDriver driver = DRIVER.get();

        if (scenario.isFailed() && driver != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot da falha");
            } catch (Exception e) {
                scenario.attach(
                    ("Nao foi possivel capturar screenshot: " + e.getMessage()).getBytes(StandardCharsets.UTF_8),
                    "text/plain",
                    "Erro ao capturar screenshot"
                );
            }
        }

        if (driver != null) {
            DriverFactory.quitDriver(driver);
            DRIVER.remove();
        }
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver nao foi inicializado para este cenario.");
        }
        return driver;
    }

    private String getConfig(String systemProperty, String envVar, String defaultValue) {
        String fromProperty = System.getProperty(systemProperty);
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }

        String fromEnv = System.getenv(envVar);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }

        return defaultValue;
    }
}
