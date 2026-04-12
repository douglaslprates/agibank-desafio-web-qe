package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser, boolean headless) {
        try {
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
                    opts.addArguments(
                        "--lang=pt-BR",
                        "--disable-notifications",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--disable-extensions",
                        "--disable-popup-blocking",
                        "--remote-allow-origins=*",
                        "--window-size=1920,1080",
                        "--remote-debugging-port=0"
                    );
                    if (headless) {
                        opts.addArguments("--headless");
                    }
                    yield new ChromeDriver(opts);
                }
            };
        } catch (RuntimeException e) {
            throw new IllegalStateException(
                "Falha ao criar driver para browser '" + browser + "' (headless=" + headless + ").",
                e
            );
        }
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
