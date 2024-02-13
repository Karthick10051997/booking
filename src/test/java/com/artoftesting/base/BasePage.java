package com.artoftesting.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public class BasePage extends TestBase {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
     /**
      * This function is used to get data 
      * @return
      */
	public static HashMap<String, String> testcasedata() 
	{    String Path ="C:\\Users\\acer\\git\\booking\\Resource\\TabbedForm.xlsx";
		TestBase testbase = new TestBase();
		String testcaseid =testbase.tcdetail();
		String[][] excelDataArray = null;
		int testcaseid_column =0;
		HashMap<String, String> excel_data = new HashMap<>();
		try {
			
			FileInputStream ExcelFile = new FileInputStream(Path);
	
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet("Sheet1");

			int numOfColumns = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
			int numOfRows = ExcelWSheet.getPhysicalNumberOfRows();
			
			excelDataArray = new String[numOfRows][numOfColumns];
			
			for (int i= 0 ; i < numOfRows; i++) {

				for (int j=0; j < numOfColumns; j++) 
				{
					try
					{
					excelDataArray[i][j] = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
					}
					catch(NullPointerException e)
					{
						excelDataArray[i][j] = "ADD DATA TO EXCEL";
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		for(int i =0;i<excelDataArray.length-1;i++)
    	{
			if(testcaseid_column==1)
				break;
    		for(int j =0;j<excelDataArray[0].length;j++)
    		{
    			 if(testcaseid.equalsIgnoreCase(excelDataArray[i][j]))
    			 {
    					 testcaseid_column =j;	 
    					 break;
    			 }
    			 
    						    	
    		}
    		
    	}
		for(int z =0;z<=excelDataArray.length-1;z++)
		{
			String key = excelDataArray[z][0];
            String value = excelDataArray[z][testcaseid_column];
            if(value=="ADD DATA TO EXCEL")
            {
            	System.out.println("Please enter the data in excel for"+key);
            	Assert.fail();
            	
            }

            excel_data.put(key, value);
		}
		return excel_data;
		
	}
     

 	/**
      * Clicks on the provided WebElement after ensuring it is displayed and clickable.
      *
      * @param element WebElement to click
      */
     public void clickOnElement(WebElement element) {
         element.isDisplayed(); // Check if element is displayed
         waitForClickable(element, Duration.ofSeconds(10)); // Wait for element to be clickable
         element.click(); // Click on the element
     }

     /**
      * Clears the existing text from the WebElement, enters the provided text,
      * and waits for the element to be visible for a specified duration.
      *
      * @param element       WebElement to enter text into
      * @param timeoutSeconds Timeout duration in seconds for the element to be visible
      * @param text          Text to enter into the WebElement
      */
     public void enterText(WebElement element, int timeoutSeconds, String text) {
         waitForVisible(element, Duration.ofSeconds(timeoutSeconds)); // Wait for element to be visible
         element.clear(); // Clear existing text
         element.sendKeys(text); // Enter new text
     }

     /**
      * Retrieves the visible text from the provided WebElement.
      *
      * @param element WebElement to retrieve text from
      * @return Text of the WebElement
      */
     public String getText(WebElement element) {
         return element.getText(); // Get text from the element
     }

     /**
      * Clears the text from the provided WebElement.
      * If the regular clear() method fails, attempts to clear using keyboard shortcuts.
      *
      * @param element WebElement to clear text from
      */
     public void clearText(WebElement element) {
         try {
             element.clear(); // Clear text from the element
         } catch (WebDriverException e) {
             // If element.clear() fails, attempt to clear using keyboard shortcuts
             element.sendKeys(Keys.CONTROL + "a"); // Select all text
             element.sendKeys(Keys.DELETE); // Delete selected text
         }
     }

     /**
      * Checks whether the provided WebElement is displayed on the page.
      *
      * @param element WebElement to check
      * @return true if the element is displayed, false otherwise
      */
     public boolean isDisplayed(WebElement element) {
         return element.isDisplayed(); // Check if element is displayed
     }

     /**
      * Checks whether the provided WebElement is enabled.
      *
      * @param element WebElement to check
      * @return true if the element is enabled, false otherwise
      */
     public boolean isEnabled(WebElement element) {
         return element.isEnabled(); // Check if element is enabled
     }

     /**
      * Checks whether the provided WebElement is selected.
      *
      * @param element WebElement to check
      * @return true if the element is selected, false otherwise
      */
     public boolean isSelected(WebElement element) {
         return element.isSelected(); // Check if element is selected
     }

     /**
      * Selects an option from the provided dropdown WebElement by visible text.
      *
      * @param dropdown Dropdown WebElement
      * @param option   Option to select by visible text
      */
     public void selectOptionFromDropdown(WebElement dropdown, String option) {
         Select dropdownSelect = new Select(dropdown);
         dropdownSelect.selectByVisibleText(option); // Select option from dropdown
     }

     /**
      * Retrieves the visible text of all options within the provided dropdown WebElement.
      *
      * @param dropdown Dropdown WebElement
      * @return List of text of all options
      */
     public List<String> getDropdownOptions(WebElement dropdown) {
         Select dropdownSelect = new Select(dropdown);
         List<WebElement> options = dropdownSelect.getOptions();
         List<String> optionTexts = new ArrayList<>();
         for (WebElement option : options) {
             optionTexts.add(option.getText()); // Get text of each option
         }
         return optionTexts;
     }

     // Utility methods for waiting for elements to be in certain states (not provided in the original code)
     private void waitForVisible(WebElement element, Duration timeout) {
     	WebDriverWait wait = new WebDriverWait(driver, timeout);
         wait.until(ExpectedConditions.visibilityOf(element));
     }

     private void waitForClickable(WebElement element, Duration timeout) {
     	WebDriverWait wait = new WebDriverWait(driver, timeout);
         wait.until(ExpectedConditions.elementToBeClickable(element));
     }

 	    /**
 	     * Accepts the alert and returns its text.
 	     *
 	     * @return The text of the alert that was accepted.
 	     */
 	    public String handleAlertAccept() {
 	        Alert alert = driver.switchTo().alert();
 	        String alertText = alert.getText();
 	        alert.accept();
 	        return alertText;
 	    }

 	    
 	    /**
 	     * Dismisses the alert and returns its text.
 	     *
 	     * @return The text of the alert that was dismissed.
 	     */
 	    public String handleAlertDismiss() {
 	        Alert alert = driver.switchTo().alert();
 	        String alertText = alert.getText();
 	        alert.dismiss();
 	        return alertText;
 	    }



 	    /**
 	     * Returns the current URL.
 	     *
 	     * @return The current URL.
 	     */
 	    public void navigateTo(String url) {
 	        driver.get(url);
 	    }

 	    /**
 	     * Returns the current URL.
 	     *
 	     * @return The current URL.
 	     */
 	    public String getCurrentUrl() {
 	        return driver.getCurrentUrl();
 	    }

 	    
 	    /**
 	     * Refreshes the current page.
 	     */
 	    public void refreshPage() {
 	        driver.navigate().refresh();
 	    }

 	    /**
 	     * Navigates back in the browser history.
 	     */
 	    public void navigateBack() {
 	        driver.navigate().back();
 	    }

 	    /**
 	     * Navigates forward in the browser history.
 	     */
 	    public void navigateForward() {
 	        driver.navigate().forward();
 	    }

 	    /**
 	     * Maximizes the current window.
 	     */
 	    public void maximizeWindow() {
 	        driver.manage().window().maximize();
 	    }

 	    

 	    /**
 	     * Pauses the execution for the specified number of seconds.
 	     *
 	     * @param seconds The number of seconds to wait.
 	     */
 	    public void waitFor(int seconds) {
 	        try {
 	            Thread.sleep(seconds * 1000);
 	        } catch (InterruptedException e) {
 	            e.printStackTrace();
 	        }
 	    }

 	    /**
 	     * Waits for the element to be clickable within the specified timeout.
 	     *
 	     * @param element The WebElement to wait for.
 	     * @param timeout The duration to wait for the element to be clickable.
 	     */
 	    public void waitForElementToBeClickable(WebElement element, Duration timeout) {
 	        new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
 	    }

 	    /**
 	     * Waits for the element to be visible within the specified timeout.
 	     *
 	     * @param element The WebElement to wait for.
 	     * @param timeout The duration to wait for the element to be visible.
 	     */
 	    private static void waitForElementToBeVisible(WebElement element, Duration timeout) {
 	        WebDriverWait wait = new WebDriverWait(driver, timeout);
 	        wait.until(ExpectedConditions.visibilityOf(element));
 	    }

 	    /**
 	     * Waits for the element to be present within the specified timeout.
 	     *
 	     * @param element The WebElement to wait for.
 	     * @param timeout The duration to wait for the element to be present.
 	     */
 	    public void waitForElementToBePresent(WebElement element, Duration timeout) {
 	        new WebDriverWait(driver, timeout)
 	                .until(ExpectedConditions.visibilityOf(element));
 	    }


 	    /**
 	     * Waits until the given condition is true within the specified timeout.
 	     *
 	     * @param condition The condition to wait for.
 	     * @param timeout   The duration to wait for the condition to be true.
 	     */
 	    public void waitForCondition(ExpectedCondition<?> condition, Duration timeout) {
 	        new WebDriverWait(driver, timeout).until(condition);
 	    }

 	    
 	    /**
 	     * Scrolls to the top of the page.
 	     */
 	    public void scrollToTop() {
 	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
 	    }

 	    /**
 	     * Scrolls to the bottom of the page.
 	     */
 	    public void scrollToBottom() {
 	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
 	    }

 	    /**
 	     * Scrolls to the specified WebElement.
 	     *
 	     * @param element The WebElement to scroll to.
 	     */
 	    public void scrollToElement(WebElement element) {
 	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
 	    }

 	    /**
 	     * Scrolls horizontally by the specified pixels.
 	     *
 	     * @param pixels The number of pixels to scroll horizontally.
 	     */
 	    public void scrollHorizontally(int pixels) {
 	        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + pixels + ",0)");
 	    }

 	    /**
 	     * Scrolls vertically by the specified pixels.
 	     *
 	     * @param pixels The number of pixels to scroll vertically.
 	     */
 	    public void scrollVertically(int pixels) {
 	        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ")");
 	    }

 	    
 	    /**
 	     * Scrolls to the specified coordinates.
 	     *
 	     * @param x The x-coordinate to scroll to.
 	     * @param y The y-coordinate to scroll to.
 	     */
 	    public void scrollToCoordinates(int x, int y) {
 	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(" + x + "," + y + ")");
 	    }

 	    
 	    
 	    /**
 	     * Switches to the frame at the specified index.
 	     *
 	     * @param frameIndex The index of the frame to switch to.
 	     */
 	    public void switchToFrame(int frameIndex) {
 	        driver.switchTo().frame(frameIndex);
 	    }

 	    
 	    /**
 	     * Switches to the frame with the specified name or ID.
 	     *
 	     * @param frameNameOrId The name or ID of the frame to switch to.
 	     */
 	    public void switchToFrame(String frameNameOrId) {
 	        driver.switchTo().frame(frameNameOrId);
 	    }
 	    
 	    /**
 	     * Switches to the specified frame element.
 	     *
 	     * @param frameElement The frame WebElement to switch to.
 	     */
 	    public void switchToFrame(WebElement frameElement) {
 	        driver.switchTo().frame(frameElement);
 	    }

 	    
 	    /**
 	     * Accepts the current alert.
 	     */
 	    public void acceptAlert() {
 	        driver.switchTo().alert().accept();
 	    }

 	    /**
 	     * Dismisses the current alert.
 	     */
 	    public void dismissAlert() {
 	        driver.switchTo().alert().dismiss();
 	    }

 	    
 	    /**
 	     * Switches to the window with the specified handle.
 	     *
 	     * @param windowHandle The handle of the window to switch to.
 	     */
 	    public void switchToWindow(String windowHandle) {
 	        driver.switchTo().window(windowHandle);
 	    }

 	    /**
 	     * Closes the current window and switches back to the original window.
 	     */
 	    public void closeCurrentWindowAndSwitchBack() {
 	        String currentWindowHandle = driver.getWindowHandle();
 	        driver.close();
 	        driver.switchTo().window(currentWindowHandle);
 	    }

 	    /**
 	     * Switches to the tab at the specified index.
 	     *
 	     * @param tabIndex The index of the tab to switch to.
 	     */
 	    public void switchToTab(int tabIndex) {
 	        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
 	        driver.switchTo().window(tabs.get(tabIndex));
 	    }

 	    
 	    /**
 	     * Asserts that the page title matches the expected title.
 	     *
 	     * @param expectedTitle The expected title of the page.
 	     */
 	    public void assertPageTitle(String expectedTitle) {
 	        Assert.assertEquals(expectedTitle, driver.getTitle());
 	    }

 	    /**
 	     * Asserts that the element is present and displayed.
 	     *
 	     * @param element The WebElement to be asserted.
 	     */
 	    public void assertElementPresent(WebElement element) {
 	        Assert.assertTrue(element.isDisplayed());
 	    }

 	    
 	    /**
 	     * Asserts that the text of the element matches the expected text.
 	     *
 	     * @param element      The WebElement to be asserted.
 	     * @param expectedText The expected text of the element.
 	     */
 	    public void assertElementText(WebElement element, String expectedText) {
 	        Assert.assertEquals(expectedText, element.getText());
 	    }

 	    
 	    /**
 	     * Asserts that the attribute value of the element matches the expected value.
 	     *
 	     * @param element        The WebElement to be asserted.
 	     * @param attributeName  The name of the attribute.
 	     * @param expectedValue  The expected value of the attribute.
 	     */
 	    public void assertElementAttributeValue(WebElement element, String attributeName, String expectedValue) {
 	        Assert.assertEquals(expectedValue, element.getAttribute(attributeName));
 	    }

 	    /**
 	     * Asserts that the element is enabled.
 	     *
 	     * @param element The WebElement to be asserted.
 	     */
 	    public void assertElementEnabled(WebElement element) {
 	        Assert.assertTrue(element.isEnabled());
 	    }

 	    /**
 	     * Asserts that the element is selected.
 	     *
 	     * @param element The WebElement to be asserted.
 	     */
 	    public void assertElementSelected(WebElement element) {
 	        Assert.assertTrue(element.isSelected());
 	    }

 	    /**
 	     * Asserts that the count of elements matching the locator matches the expected count.
 	     *
 	     * @param locator      The By locator to find the elements.
 	     * @param expectedCount The expected count of elements.
 	     */
 	    public void assertElementCount(By locator, int expectedCount) {
 	        Assert.assertEquals(expectedCount, driver.findElements(locator).size());
 	    }

 	    /**
 	     * Asserts that no elements matching the locator are present.
 	     *
 	     * @param locator The By locator to find the elements.
 	     */
 	    public void assertElementNotPresent(By locator) {
 	        Assert.assertFalse(driver.findElements(locator).size() > 0);
 	    }

 	    /**
 	     * Asserts that the current URL contains the expected URL.
 	     *
 	     * @param expectedURL The expected URL.
 	     */
 	    public void assertURLContains(String expectedURL) {
 	        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
 	    }

 	    
 	    /**
 	     * Asserts that the expected text is present in the page source.
 	     *
 	     * @param expectedText The expected text to be present in the page source.
 	     */
 	    public void assertTextPresentInPageSource(String expectedText) {
 	        Assert.assertTrue(driver.getPageSource().contains(expectedText));
 	    }

 	    
 	    /**
 	     * Checks if the text of a WebElement contains a specific substring.
 	     *
 	     * @param element The WebElement to check.
 	     * @param text    The substring to search for.
 	     * @return {@code true} if the text of the WebElement contains the specified substring, {@code false} otherwise.
 	     */
 	    public boolean doesTextContain(WebElement element, String text) {
 	        return element.getText().contains(text);
 	    }

 	    /**
 	     * Checks if a specific option text is present within the options of a dropdown WebElement.
 	     *
 	     * @param dropdown    The dropdown WebElement.
 	     * @param optionText  The text of the option to search for.
 	     * @return {@code true} if the option text is present in the dropdown, {@code false} otherwise.
 	     */
 	    public boolean isOptionPresentInDropdown(WebElement dropdown, String optionText) {
 	        Select dropdownSelect = new Select(dropdown);
 	        for (WebElement option : dropdownSelect.getOptions()) {
 	            if (option.getText().equals(optionText)) {
 	                return true;
 	            }
 	        }
 	        return false;
 	    }

 	    /**
 	     * Checks if specific data is present within a table represented by a WebElement.
 	     *
 	     * @param table         The WebElement representing the table.
 	     * @param expectedData  The data to search for within the table.
 	     * @return {@code true} if the data is present in the table, {@code false} otherwise.
 	     */
 	    public boolean isDataPresentInTable(WebElement table, String expectedData) {
 	        List<WebElement> rows = table.findElements(By.tagName("tr"));
 	        for (WebElement row : rows) {
 	            List<WebElement> columns = row.findElements(By.tagName("td"));
 	            for (WebElement column : columns) {
 	                if (column.getText().equals(expectedData)) {
 	                    return true;
 	                }
 	            }
 	        }
 	        return false;
 	    }

 	    /**
 	     * Waits for a WebElement to become visible within a specified timeout duration.
 	     *
 	     * @param element  The WebElement to wait for visibility.
 	     * @param timeout  The maximum time to wait for the element to become visible.
 	     * @return {@code true} if the element becomes visible within the specified timeout, {@code false} otherwise.
 	     */
 	    public boolean waitForElementVisibility(WebElement element, Duration timeout) {
 	        try {
 	            waitForElementToBeVisible(element, timeout);
 	            return true;
 	        } catch (TimeoutException e) {
 	            return false;
 	        }
 	
 	    }

}
