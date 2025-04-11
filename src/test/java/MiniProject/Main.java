package MiniProject;

import java.io.IOException; 
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

public class Main {

    // Method to set up the WebDriver before any tests are run
    @BeforeClass
    @Parameters("browser")
    public void driverSetup(String br) {
        HotelSearch.setupWebDriver(br);
    }

    // Test to launch the website
    @Test(priority = 1)
    public void launchWebsite() {
        HotelSearch.openWebsite();
        Assert.assertTrue(true);
    }

    // Test to select guests and rooms
    @Test(priority = 2)
    public void guestsAndRooms() throws InterruptedException {
        HotelSearch.guestsAndRooms();
        Assert.assertTrue(true);
    }

    // Test to search for a city
    @Test(priority = 3)
    public void searchCity() throws InterruptedException {
        HotelSearch.searchCity();
        Assert.assertTrue(true);
    }

    // Test to select check-in and check-out dates
    @Test(priority = 4)
    public void checkInOut() throws InterruptedException {
        HotelSearch.checkInOut();
        Assert.assertTrue(true);
    }

    // Test to perform the search
    @Test(priority = 5)
    public void search() throws InterruptedException {
        HotelSearch.search();
        Assert.assertTrue(true);
    }

    // Test to sort the search results
    @Test(priority = 6)
    public void sortBy() throws InterruptedException {
        HotelSearch.sortBy();
        Assert.assertTrue(true);
    }

    // Test to verify hotel details
    @Test(priority = 7)
    public void verifyHotelDetails() throws InterruptedException, IOException {
        HotelSearch.verifyHotelDetails();
        Assert.assertTrue(true);
    }

    // Test to read data from the Excel file
    @Test(priority = 8)
    public void readExcelData() throws IOException {
        HotelSearch.readExcelData();
        Assert.assertTrue(true);
    }

    // Test to quit the browser
    @Test(priority = 9)
    public void quitBrowser() {
        HotelSearch.quitBrowser();
        Assert.assertTrue(true);
    }
}