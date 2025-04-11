package MiniProject;

import java.io.IOException;   
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelSearch {
    // WebDriver instance to control the browser
    public static WebDriver driver;
    // Base URL of the website to be tested
    String baseUrl = "https://www.trivago.in/";
    // Path to the Excel file for storing hotel data
    static String excelFilePath = System.getProperty("user.dir") + "\\ExcelData\\HotelData.xlsx";
    // Instances of utility classes for screenshots and Excel operations
    static ScreenShots ss = new ScreenShots();
    static ExcelUtility eu = new ExcelUtility(excelFilePath);

    // Method to set up the WebDriver based on the browser parameter
    public static void setupWebDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    // Method to read data from the Excel file
    public static void readExcelData() throws IOException {
        eu.readExcelData();
    }

    // Method to open the website
    public static void openWebsite() {
        driver.get("https://www.trivago.in/");
    }

    // Method to search for a city
    public static void searchCity() throws InterruptedException {
        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"input-auto-complete\"]"));
        searchField.sendKeys("Mumbai");
        Thread.sleep(2000); // Wait for suggestions to load
        searchField.click();
        ss.takeScreenshot("SearchCity", driver);
    }

    // Method to select check-in and check-out dates
    public static void checkInOut() throws InterruptedException {
        String checkin_month = "July";
        String checkin_year = "2025";
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[1]/div[2]/div/div/fieldset/button[1]")).click();
        // Loop to navigate to the correct month and year
        while (true) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement currentDisplayElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='__next']/div[1]/div[2]/section[1]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[1]/h3")));
            String currentDisplay = currentDisplayElement.getText();
            String[] current = currentDisplay.split(" ");
            String currentMonth = current[0];
            String currentYear = current[1];
            if (currentMonth.equals(checkin_month) && currentYear.equals(checkin_year)) {
                break;
            }
            driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[1]/div[2]/div/div[2]/div/div/div/div[2]/div/button[2]")).click();
//            Thread.sleep(3000);
        }

        // Select check-in and check-out dates
        driver.findElement(By.xpath("//button[@data-testid=\"valid-calendar-day-2025-07-27\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"valid-calendar-day-2025-07-28\"]")).click();
        ss.takeScreenshot("CheckInOut", driver);
    }

    // Method to select guests and rooms
    public static void guestsAndRooms() throws InterruptedException {
        driver.findElement(By.cssSelector("button[data-testid='search-form-guest-selector']")).click();
        @SuppressWarnings("deprecation")
        int a = Integer.parseInt(driver.findElement(By.xpath("(//input[@class=\"h1ZWRl\"])[1]")).getAttribute("value"));
        // Loop to set the number of adults to 1
        while (a > 1) {
            driver.findElement(By.xpath("//button[@data-testid='adults-amount-minus-button']")).click();
            a--;
        }
        driver.findElement(By.xpath("//button[normalize-space()='Apply']")).click();
        ss.takeScreenshot("GuestsAndRooms", driver);
    }

    // Method to perform the search
    public static void search() throws InterruptedException {
        driver.findElement(By.xpath("//button[@class='_3tjlp_']")).click();
        Thread.sleep(2000); // Wait for search results to load
        ss.takeScreenshot("Search", driver);
    }

    // Method to sort the search results
    public static void sortBy() throws InterruptedException {
        WebElement sort = driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div/div/div/div/div[1]/div/button/span/span[2]"));
        sort.click();
        WebElement rating = driver.findElement(By.xpath("//input[@value='3']"));
        rating.click();
        driver.findElement(By.xpath("//button[normalize-space()='Apply']")).click();
        ss.takeScreenshot("SortBy", driver);
    }

    // Method to verify hotel details and write to Excel file
    public static void verifyHotelDetails() throws InterruptedException, IOException {
        Thread.sleep(2000); // Ensure search results load

        // Find elements for hotel names, prices, and ratings
        List<WebElement> hotelNames = driver.findElements(By.cssSelector("span[itemprop='name']")); // Hotel name
        List<WebElement> hotelPrices = driver.findElements(By.cssSelector("div[data-testid='recommended-price']")); // Hotel price
        List<WebElement> hotelRatings = driver.findElements(By.cssSelector("span[itemprop='ratingValue']")); // Hotel rating

        // Check if elements are found
        if (hotelNames.isEmpty() || hotelPrices.isEmpty() || hotelRatings.isEmpty()) {
            System.out.println("No hotels, prices, or ratings found. Please check the selectors or wait time.");
            return;
        }

        // Store hotel data in a 2D array
        String[][] hotelData = new String[5][3];
        for (int i = 0; i < 5; i++) {
            hotelData[i][0] = hotelNames.get(i).getText();
            hotelData[i][1] = hotelPrices.get(i).getText();
            hotelData[i][2] = hotelRatings.get(i).getText();
        }

        // Write hotel data to Excel file
        eu.writeHotelData(hotelData);
        ss.takeScreenshot("HotelVerification", driver);
    }

    // Method to quit the browser
    public static void quitBrowser() {
        driver.quit();
    }
}