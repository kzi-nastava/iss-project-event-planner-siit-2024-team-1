package com.example.eventplanner.event.e2e;

import com.example.eventplanner.event.Helper;
import com.example.eventplanner.pages.event.AddEventPage;
import com.example.eventplanner.pages.event.EditEventPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditEventTest {
    private static WebDriver driver;
    private EditEventPage editEventPage;

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

        driver.get("http://localhost:4200/home/edit-event/1");
        editEventPage = new EditEventPage(driver);
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
    public void testSetTitle() {
        Assertions.assertTrue(editEventPage.isPageLoaded(), "Edit Event page did not load.");

        // Wait for the title input to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='title']")));

        // Scroll the element into view and ensure no obstruction
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", titleInput);

        // Use Actions to click in case of interference
        Actions actions = new Actions(driver);
        actions.moveToElement(titleInput).click().perform();

        // Set the title
        titleInput.clear();
        titleInput.sendKeys("Test title");
        Assertions.assertEquals("Test title", titleInput.getAttribute("value"), "Title input should have the correct value.");

        Helper.takeScreenshoot(driver, "edit_event_set_title");
    }

    @Test
    public void testSetDescription() {
        Assertions.assertTrue(editEventPage.isPageLoaded(), "Edit Event page did not load.");

        // Wait for the title input to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement descriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='description']")));

        // Scroll the element into view and ensure no obstruction
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", descriptionInput);

        // Use Actions to click in case of interference
        Actions actions = new Actions(driver);
        actions.moveToElement(descriptionInput).click().perform();

        // Set the title
        descriptionInput.clear();
        descriptionInput.sendKeys("Test Desrciption");
        Assertions.assertEquals("Test Desrciption", descriptionInput.getAttribute("value"), "Desrciption input should have the correct value.");

        Helper.takeScreenshoot(driver, "edit_event_set_description");
    }

    @Test
    public void testMaxParticipants() {
        Assertions.assertTrue(editEventPage.isPageLoaded(), "Edit Event page did not load.");

        // Wait for the title input to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement maxParticipantsInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='maxParticipants']")));

        // Scroll the element into view and ensure no obstruction
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", maxParticipantsInput);

        // Use Actions to click in case of interference
        Actions actions = new Actions(driver);
        actions.moveToElement(maxParticipantsInput).click().perform();

        // Set the title
        maxParticipantsInput.clear();
        maxParticipantsInput.sendKeys("200");
        Assertions.assertEquals("200", maxParticipantsInput.getAttribute("value"), "Desrciption input should have the correct value.");

        Helper.takeScreenshoot(driver, "edit_event_set_max_participants");
    }


    @Test
    public void testSubmitEventCreationForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ensure the page loaded correctly
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        Assertions.assertEquals("Edit Event", pageTitle.getText(), "Page did not load correctly.");

        // Fill out the form fields
        WebElement titleInput = driver.findElement(By.cssSelector("input[formcontrolname='title']"));
        titleInput.clear();
        titleInput.sendKeys("Test Event");

        WebElement descriptionTextarea = driver.findElement(By.cssSelector("textarea[formcontrolname='description']"));
        descriptionTextarea.sendKeys("This is a test event description.");

        WebElement maxParticipantsInput = driver.findElement(By.cssSelector("input[formcontrolname='maxParticipants']"));
        maxParticipantsInput.clear();
        maxParticipantsInput.sendKeys("100");

        WebElement cityInput = driver.findElement(By.cssSelector("input[formcontrolname='city']"));
        cityInput.clear();
        cityInput.sendKeys("Test City");

        WebElement streetInput = driver.findElement(By.cssSelector("input[formcontrolname='street']"));
        streetInput.clear();
        streetInput.sendKeys("Test Street");

        WebElement numberInput = driver.findElement(By.cssSelector("input[formcontrolname='number']"));
        numberInput.clear();
        numberInput.sendKeys("123");

        WebElement latitudeInput = driver.findElement(By.cssSelector("input[formcontrolname='latitude']"));
        latitudeInput.clear();
        latitudeInput.sendKeys("10.12345");

        WebElement longitudeInput = driver.findElement(By.cssSelector("input[formcontrolname='longitude']"));
        longitudeInput.clear();
        longitudeInput.sendKeys("20.12345");


        // Locate the "Create Event" button
        WebElement createButton = driver.findElement(By.xpath("//p-button//button[normalize-space()='EDIT EVENT']"));

// Click the "Create Event" button
        createButton.click();

        // Wait for navigation to the "home/my_events" page
        wait.until(ExpectedConditions.urlContains("home/my_events"));

// Assert that the URL is correct
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("home/my_events"), "The user was not redirected to the 'home/my_events' page.");

        Helper.takeScreenshoot(driver, "edit_event_submit");
    }

    @Test
    public void testSetAddress() {
        Assertions.assertTrue(editEventPage.isPageLoaded(), "Edit Event page did not load.");

        // Wait for the city input to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Test City field
        WebElement cityInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='city']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityInput);
        Actions actions = new Actions(driver);
        actions.moveToElement(cityInput).click().perform();
        cityInput.clear();
        cityInput.sendKeys("New York");
        Assertions.assertEquals("New York", cityInput.getAttribute("value"), "City input should have the correct value.");

        // Test Street field
        WebElement streetInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='street']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", streetInput);
        actions.moveToElement(streetInput).click().perform();
        streetInput.clear();
        streetInput.sendKeys("5th Avenue");
        Assertions.assertEquals("5th Avenue", streetInput.getAttribute("value"), "Street input should have the correct value.");

        // Test Number field
        WebElement numberInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='number']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", numberInput);
        actions.moveToElement(numberInput).click().perform();
        numberInput.clear();
        numberInput.sendKeys("123");
        Assertions.assertEquals("123", numberInput.getAttribute("value"), "Number input should have the correct value.");

        // Test Latitude field
        WebElement latitudeInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='latitude']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", latitudeInput);
        actions.moveToElement(latitudeInput).click().perform();
        latitudeInput.clear();
        latitudeInput.sendKeys("40.7128");
        Assertions.assertEquals("40.7128", latitudeInput.getAttribute("value"), "Latitude input should have the correct value.");

        // Test Longitude field
        WebElement longitudeInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[formcontrolname='longitude']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", longitudeInput);
        actions.moveToElement(longitudeInput).click().perform();
        longitudeInput.clear();
        longitudeInput.sendKeys("-74.0060");
        Assertions.assertEquals("-74.0060", longitudeInput.getAttribute("value"), "Longitude input should have the correct value.");

        Helper.takeScreenshoot(driver, "edit_event_set_address");
    }



    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
