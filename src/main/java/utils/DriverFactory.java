package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser, boolean headless) {
        return switch (browser) {
            case "firefox" -> {
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) {
                    opts.addArguments("-headless");
                }
                yield new FirefoxDriver(opts);
            }
            default -> {
                ChromeOptions opts = new ChromeOptions();
                opts.addArguments("--lang=pt-BR", "--disable-notifications", "--no-sandbox", "--disable-dev-shm-usage");
                if (headless) {
                    opts.addArguments("--headless=new");
                }
                yield new ChromeDriver(opts);
            }
        };
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
