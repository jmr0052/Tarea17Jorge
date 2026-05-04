package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarySeleniumTest {

    private WebDriver driver;
    private String url;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        File file = new File("src/main/resources/index.html");
        url = "file:///" + file.getAbsolutePath().replace("\\", "/");
    }

    @Test
    public void testPageTitleIsCorrect() {
        driver.get(url);
        assertEquals("Library Management System", driver.getTitle());
    }

    @Test
    public void testLoanFormIsPresent() {
        driver.get(url);
        WebElement form = driver.findElement(By.id("loan-form"));
        assertNotNull(form);
    }

    @Test
    public void testMemberIdInputExists() {
        driver.get(url);
        WebElement input = driver.findElement(By.id("memberId"));
        assertTrue(input.isDisplayed());
    }

    @Test
    public void testIsbnInputExists() {
        driver.get(url);
        WebElement input = driver.findElement(By.id("isbn"));
        assertTrue(input.isDisplayed());
    }

    @Test
    public void testSubmitButtonExists() {
        driver.get(url);
        WebElement button = driver.findElement(By.id("submitLoan"));
        assertTrue(button.isDisplayed());
    }

    @Test
    public void testBookListIsPresent() {
        driver.get(url);
        WebElement bookList = driver.findElement(By.id("books"));
        assertNotNull(bookList);
    }

    @Test
    public void testThreeBooksDisplayed() {
        driver.get(url);
        java.util.List<WebElement> books = driver.findElements(By.className("book-item"));
        assertEquals(3, books.size());
    }

    @Test
    public void testMemberSectionIsPresent() {
        driver.get(url);
        WebElement memberSection = driver.findElement(By.id("member-section"));
        assertNotNull(memberSection);
    }

    @Test
    public void testThreeMembersDisplayed() {
        driver.get(url);
        java.util.List<WebElement> members = driver.findElements(By.className("member-item"));
        assertEquals(3, members.size());
    }

    @Test
    public void testMemberIdInputAcceptsText() {
        driver.get(url);
        WebElement input = driver.findElement(By.id("memberId"));
        input.sendKeys("STU001");
        assertEquals("STU001", input.getAttribute("value"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}