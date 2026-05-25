package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AppOrderTest {

    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        // Автоматическая установка драйвера Chrome
        WebDriverManager.chromedriver().setup();

        // Настройка браузера
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        // options.addArguments("--headless"); // Раскомментируйте, если хотите скрытый режим

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void shouldSubmitOrderSuccessfully() {
        // 1. Открываем страницу
        driver.get("http://localhost:9999");

        // 2. Заполняем поле "Фамилия и имя"
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванов Иван");

        // 3. Заполняем поле "Телефон"
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79991234567");

        // 4. Ставим галочку согласия (ИСПРАВЛЕНО!)
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box"))
                .click();

        // 5. Нажимаем кнопку "Продолжить"
        driver.findElement(By.cssSelector("button[type='button']"))
                .click();

        // 6. Проверяем сообщение об успехе
        String successMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']"))
                .getText();

        assertTrue(successMessage.contains("Ваша заявка успешно отправлена"));
    }
}