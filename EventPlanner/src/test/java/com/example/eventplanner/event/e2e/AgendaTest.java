package com.example.eventplanner.event.e2e;

import com.example.eventplanner.event.Helper;
import com.example.eventplanner.pages.event.AddActivityPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class AgendaTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
    }

    @BeforeEach
    public void setup() {
        // First, login before each test
        login("johndoe@gmail.com", "sifra");  // Replace with valid credentials

        driver.get("http://localhost:4200/home/agenda/1");
    }

    private void login(String email, String password) {
        driver.get("http://localhost:4200"); // Replace with your login page URL

        // Wait for the email field to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formcontrolname='email']")));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formcontrolname='password']")));
        WebElement signInButton = driver.findElement(By.cssSelector("p-button[label='SIGN IN']"));

        // Fill in the login credentials
        emailField.sendKeys(email);
        passwordField.sendKeys(password);

        // Submit the login form
        signInButton.click();

        // Wait for the element visible after successful login (for example, the "See All" button)
        WebElement seeAllButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p-button[label='See All']")));
        Assertions.assertTrue(seeAllButton.isDisplayed(), "Login failed: 'See All' button not visible.");
    }

    @Test
    public void testAgendaPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the page to load and the table to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p-table")));

        // Validate the table caption
        WebElement caption = driver.findElement(By.cssSelector("p-table .flex h2"));
        Assertions.assertTrue(caption.getText().contains("Agenda For Event:"), "Caption does not contain the expected text.");

        // Validate the table headers
        List<WebElement> headers = driver.findElements(By.cssSelector("p-table thead th"));
        Assertions.assertEquals(7, headers.size(), "Table should have 7 columns.");
        Assertions.assertEquals("Title", headers.get(0).getText(), "First header should be 'Title'.");
        Assertions.assertEquals("Address", headers.get(1).getText(), "Second header should be 'Address'.");
        Assertions.assertEquals("Description", headers.get(2).getText(), "Third header should be 'Description'.");
        Assertions.assertEquals("Start", headers.get(3).getText(), "Fourth header should be 'Start'.");
        Assertions.assertEquals("End", headers.get(4).getText(), "Fifth header should be 'End'.");
        Assertions.assertEquals("Edit", headers.get(5).getText(), "Sixth header should be 'Edit'.");
        Assertions.assertEquals("Delete", headers.get(6).getText(), "Seventh header should be 'Delete'.");

        // Validate rows in the table
        List<WebElement> rows = driver.findElements(By.cssSelector("p-table tbody tr"));
        Assertions.assertFalse(rows.isEmpty(), "Table should have at least one row.");

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.cssSelector("td"));
            Assertions.assertEquals(7, columns.size(), "Each row should have 7 columns.");

            // Example: Validate the "Title" column is not empty
            Assertions.assertFalse(columns.get(0).getText().isEmpty(), "Title column should not be empty.");

            // Example: Validate the "Address" column
            Assertions.assertTrue(columns.get(1).getText().contains(","), "Address column should contain a comma.");
        }

        // Test the "Edit" button
        WebElement addButton = driver.findElement(By.xpath("//button[contains(@class, 'p-button') and contains(@class, 'p-button-icon-only')]//span[contains(@class, 'pi-plus')]"));
        Assertions.assertNotNull(addButton, "Add button should exist in the first row.");
        addButton.click();

        // Validate the URL changes to the edit page
        wait.until(ExpectedConditions.urlContains("home/agenda/1/add"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("add"), "URL should contain 'add'.");

        driver.navigate().to("http://localhost:4200/home/agenda/1");

        // Test the "Edit" button
        WebElement editButton = driver.findElement(By.xpath("//button[contains(@class, 'p-button') and contains(@class, 'p-button-icon-only')]//span[contains(@class, 'pi-pencil')]"));
        Assertions.assertNotNull(editButton, "Edit button should exist in the first row.");
        editButton.click();

        // Validate the URL changes to the edit page
        wait.until(ExpectedConditions.urlContains("home/agenda/edit/1"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("edit"), "URL should contain 'edit'.");

        driver.navigate().to("http://localhost:4200/home/agenda/1");

//        WebElement deleteButton = driver.findElement(By.xpath("//button[contains(@class, 'p-button') and contains(@class, 'p-button-icon-only')]//span[contains(@class, 'pi-trash')]"));
//        Assertions.assertNotNull(deleteButton, "Delete button should exist in the first row.");
//        deleteButton.click();
//
//        Assertions.assertTrue(rows.isEmpty(), "Table should have 0 rows.");

        Helper.takeScreenshoot(driver, "agenda_table");
    }
}