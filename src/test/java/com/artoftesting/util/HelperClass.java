package com.artoftesting.util;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HelperClass {
	public WebDriver driver;
	
	public HelperClass(WebDriver driver)
	{
		this.driver =driver;
	
	}
	
	
	/**
	 * This method will be used to validate based on text displayed on screen
	 * @param Text - Text that need to be verified
	 * @param element - Element for which text will be displayed 
	 */
	public void Validation(String Text,WebElement element)
	{
		String Actual_Text = null;
		try
		{
		 Actual_Text = element.getText();
		}
		catch(Exception e)
		{
			System.out.println("Please Check the WebElement");
			e.printStackTrace();
		}
		Assert.assertEquals(Actual_Text,Text);
		
	}
	/**
	 * This method will be used to select option in Dropdown by value
	 * @param element WebElement of DropDown need to be passed
	 * @param value  Pass the value of option need to be selected in Dropdown
	 */
	public void DropDownByValue(WebElement element, String value)
	{
		Select select = new Select(element);
		select.selectByValue(value);
	}
	
	/**
	 * This method can be used if datepicker Input as HTML Tag.
	 * @param element  WebElement of Date need to be passed
	 * @param value   Date which has to be select pass Example :06-02-2024
	 */
	
	public void datepicker(WebElement element, String value)
	{
		element.click();
		element.sendKeys(value);
	}
	
	/**
	 * This method is used to accept the alert
	 */
	public void alertaccept()
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		
		      alert.accept();	
	}
	

	/**
	 * This method is used to dismiss the alert
	 */
	public void alertdismiss()
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		
		      alert.dismiss();	
	}
	
	

}
