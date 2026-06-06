package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Задание №1: генератор паролей calculator.net и вызов заданий №2 и №3.
 * Путь к chromedriver: системное свойство {@code webdriver.chrome.driver}
 * или переменная окружения {@code CHROME_DRIVER_PATH}; если не заданы,
 * используется Selenium Manager (автозагрузка драйвера).
 */
public class App {

    public static void main(String[] args) {
        configureChromeDriver();
        ChromeOptions options = new ChromeOptions();
        if ("true".equalsIgnoreCase(System.getenv("CI"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        WebDriver webDriver = new ChromeDriver(options);
        try {
            runTask1(webDriver);
            Task2.run(webDriver);
            Task3.run(webDriver);
        } finally {
            webDriver.quit();
        }
    }

    private static void configureChromeDriver() {
        String path = System.getProperty("webdriver.chrome.driver");
        if (path == null || path.trim().isEmpty()) {
            path = System.getenv("CHROME_DRIVER_PATH");
        }
        if (path != null && !path.trim().isEmpty()) {
            System.setProperty("webdriver.chrome.driver", path.trim());
        }
    }

    private static void runTask1(WebDriver webDriver) {
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
            String password = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#resultid div.verybigtext b"))).getText();
            System.out.println("Задание 1 — сгенерированный пароль:");
            System.out.println(password);
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}
