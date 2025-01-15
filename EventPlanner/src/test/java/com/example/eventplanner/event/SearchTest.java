package com.example.eventplanner.event;

import com.example.eventplanner.pages.FilterPanel;
import com.example.eventplanner.pages.SearchPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchTest {
    private static WebDriver driver;
    private SearchPage searchPage;

    @BeforeAll
    public static void setupClass() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void setup() {
        driver.get("http://localhost:4200/home/search");
        searchPage = new SearchPage(driver);
    }

    @Test
    public void testEventPagination() {
        int initialCount = searchPage.getEventCount();
        searchPage.nextPage();

        int nextPageCount = searchPage.getEventCount();
        Assertions.assertTrue(nextPageCount >= 0, "Next page should display valid number of events");
        Helper.takeScreenshoot(driver, "next_page");

        searchPage.prevPage();
        int prevPageCount = searchPage.getEventCount();
        Assertions.assertEquals(initialCount, prevPageCount, "Previous page should return to initial state");
        Helper.takeScreenshoot(driver, "prev_page");
    }

    @Test
    public void testEventSortingByTitle() {
        int initialEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, initialEvents, "Initial events should be present");
        searchPage.selectEventSortOption("title");
        int titleSortedEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, titleSortedEvents, "Events should be present after title sorting");
        Assertions.assertTrue(searchPage.areEventsSortedBy("title"),
                "Events should be sorted by title in ascending order");
        Helper.takeScreenshoot(driver, "sort_by_title");
    }

    @Test
    public void testEventSortingPersistsAcrossPages() {
        int initialEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, initialEvents, "Initial events should be present");
        searchPage.selectEventSortOption("title");
        int titleSortedEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, titleSortedEvents, "Events should be present after title sorting");
        Assertions.assertTrue(searchPage.areEventsSortedBy("title"),
                "Events should be sorted by title in ascending order");
        Helper.takeScreenshoot(driver, "sort_by_title_page1");
        searchPage.nextPage();
        titleSortedEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, titleSortedEvents, "Events should be present after title sorting");
        Assertions.assertTrue(searchPage.areEventsSortedBy("title"),
                "Events should be sorted by title in ascending order");
        Helper.takeScreenshoot(driver, "sort_by_title_page1");

    }

    @Test
    public void testEventSortingByDescription() {
        int initialEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, initialEvents, "Initial events should be present");
        searchPage.selectEventSortOption("description");
        int titleSortedEvents = searchPage.getEventCount();
        Assertions.assertNotEquals(0, titleSortedEvents, "Events should be present after description sorting");
        Assertions.assertTrue(searchPage.areEventsSortedBy("description"),
                "Events should be sorted by description in ascending order");
        Helper.takeScreenshoot(driver, "sort_by_description");
    }



    @Test
    public void testEventSearchDescription() {
        searchPage.enterSearchTerm("Latest in digital security and protection");

        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount == 1, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventSearchDescription");

    }

    @Test
    public void testEventSearchTitle() {
        searchPage.enterSearchTerm("Startup Networking Night");

        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount == 1, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventSearchTitle");

    }

    @Test
    public void testEventSearchCity() {
        searchPage.enterSearchTerm("Novi Sad");

        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount == 3, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventSearchCity");

    }

    @Test
    public void testEventSearchStreet() {
        searchPage.enterSearchTerm("Stevana Dejanova");

        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount == 1, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventSearchStreet");

    }

    @Test
    public void testEventFilteringCity() {
        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventCity("Novi Sad");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount > 0, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventFilteringCity");

    }

    @Test
    public void testEventFilteringNoEvents() {
        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.toggleFilterType("Events");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(0, eventCount, "Events should not be displayed");
        Helper.takeScreenshoot(driver,"testEventFilteringNoEvents");

    }

    @Test
    public void testEventFilteringWithSearchTerm() {
        FilterPanel filterPanel = searchPage.openFilters();
        searchPage.enterSearchTerm("tech");
        filterPanel.setEventType("Social Gathering");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(1, eventCount, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventFilteringWithSearchTerm");
    }

    @Test
    public void testEventFilteringWithCalendar() {
        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventDate("11/03/2024 - 12/31/2024");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(3, eventCount, "Filtered events should be displayed");
        Helper.takeScreenshoot(driver,"testEventFilteringWithCalendar");
    }


    @Test
    public void testCombinedSearchAndFilterByType() {
        searchPage.enterSearchTerm("Cybersecurity");

        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventType("Tech Conference");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(1, eventCount, "Should find tech conference");
        Helper.takeScreenshoot(driver, "combined_search_filter_type");
    }

    @Test
    public void testCombinedSearchAndFilterByDateRange() {
        searchPage.enterSearchTerm("networking");

        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventDate("10/01/2024 - 12/31/2024");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(1, eventCount, "Should find networking events in specified date range");
        Helper.takeScreenshoot(driver, "combined_search_filter_date");
    }

    @Test
    public void testCombinedSearchAndMultipleFilters() {
        searchPage.enterSearchTerm("tech");

        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventType("Tech Conference");
        filterPanel.setEventCity("Novi Sad");
        filterPanel.setEventDate("11/01/2024 - 12/31/2024");
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(1, eventCount, "Should find tech conferences in Novi Sad within date range");
        Helper.takeScreenshoot(driver, "combined_search_multiple_filters");
    }

    @Test
    public void testNoMatchingEvents() {
        searchPage.enterSearchTerm("nonexistent event");

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(0, eventCount, "Should find no events for non-existent search term");
        Helper.takeScreenshoot(driver, "no_matching_events_search");
    }

    @Test
    public void testNoMatchingEventsWithFilters() {
        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventType("Tech Conference");
        filterPanel.setEventCity("Paris"); // Assuming no events in Paris
        filterPanel.applyFilters();

        int eventCount = searchPage.getEventCount();
        Assertions.assertEquals(0, eventCount, "Should find no events matching filter criteria");
        Helper.takeScreenshoot(driver, "no_matching_events_filters");
    }

    @Test
    public void testResetFilters() {
        // First apply multiple filters
        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventType("Conference");
        filterPanel.setEventCity("Novi Sad");
        filterPanel.setEventDate("11/01/2024 - 12/31/2024");
        filterPanel.applyFilters();

        // Take screenshot of filtered results
        Helper.takeScreenshoot(driver, "before_reset_filters");

        int filteredCount = searchPage.getEventCount();

        // Reset filters
        filterPanel.resetFilters();
        filterPanel.applyFilters();

        // Take screenshot after reset
        Helper.takeScreenshoot(driver, "after_reset_filters");

        int resetCount = searchPage.getEventCount();

        Assertions.assertTrue(resetCount > filteredCount,
                "Event count should increase after resetting filters");
    }

    @Test
    public void testPartialTextSearch() {
        searchPage.enterSearchTerm("tech");  // Should match "Technology", "TechConf" etc.

        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount > 0, "Should find events with partial text match");
        Helper.takeScreenshoot(driver, "partial_text_search");
    }

    @Test
    public void testCaseSensitiveSearch() {
        String searchTerm = "STARTUP NETWORKING NIGHT";
        searchPage.enterSearchTerm(searchTerm.toLowerCase());
        int lowerCaseCount = searchPage.getEventCount();

        searchPage.enterSearchTerm(searchTerm.toUpperCase());
        int upperCaseCount = searchPage.getEventCount();

        Assertions.assertEquals(lowerCaseCount, upperCaseCount,
                "Search should be case insensitive");
        Helper.takeScreenshoot(driver, "case_sensitive_search");
    }

    @Test
    public void testResetFiltersWithActiveSearch() {
        // Apply search and filters
        searchPage.enterSearchTerm("tech");

        FilterPanel filterPanel = searchPage.openFilters();
        filterPanel.setEventType("Conference");
        filterPanel.applyFilters();

        Helper.takeScreenshoot(driver, "before_reset_with_search");

        filterPanel.resetFilters();
        filterPanel.applyFilters();

        Helper.takeScreenshoot(driver, "after_reset_with_search");

        // Verify that search results are still filtered by search term
        // but not by the reset filters
        int eventCount = searchPage.getEventCount();
        Assertions.assertTrue(eventCount > 0,
                "Should still show results matching search term after filter reset");
    }

    @AfterAll
    public static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
}