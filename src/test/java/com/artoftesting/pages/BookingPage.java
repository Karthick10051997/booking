package com.artoftesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class BookingPage {

WebDriver driver;

public BookingPage(WebDriver driver) {
	this.driver = driver;
    PageFactory.initElements(driver, this);
}
	
	@FindBy(xpath ="//div[text()='Booking Module']")
	WebElement bookingModule_Tab;
	
	@FindBy(xpath ="//a[text()='Booking Module']")
	WebElement bookingModule_Option;
	
	@FindBy(xpath ="//h1[text()='Mail Booking']")
	WebElement mailBooking_option;
	
	@FindBy(xpath ="//h1[text()='Domestic Mail Booking']")
	WebElement domesticMailBooking_option;
	
	@FindBy(id ="originPincode")
	WebElement orginPincode_textbox;
	
	@FindBy(id ="destinationPincode")
	WebElement destinationPincode_textbox;
	
	@FindBy(id="physicalWeight")
	WebElement pysicalWeight_textbox;
	
	@FindBy(xpath ="(//div[@class='relative flex items-center'])[5]/button")
	WebElement searchServices_button;
	
	@FindBy(xpath ="(//div[@role='table']/div)[2]/div[1]/div/div")
	WebElement availableService_table;
	
	

	
	
	
	public void clickBookingModuleTab()
	{
		bookingModule_Tab.click();
	}
	
	public void clickBookingModuleOption()
	{
		bookingModule_Option.click();
	}
	
	public void clickMailBookingOption()
	{
		mailBooking_option.click();
	}
	
	public void clickDomesticMailBookingOption()
	{
		domesticMailBooking_option.click();
	}
	
	
	public void enterOriginPincode(String pin)
	{
		orginPincode_textbox.sendKeys(pin);
	}
	
	public void enterDestinationPincode(String pin)
	{
		destinationPincode_textbox.sendKeys(pin);
	}
	
	public void enterPhysicalWeight(String weight)
	{
		pysicalWeight_textbox.sendKeys(weight);
	}
	
	public void clickSearchServicesButton()
	{
		searchServices_button.click();
	}
	
	public void VerifyAvailableServices()
	{
		String Actual_text =availableService_table.getText();
		System.out.println(Actual_text);
		Assert.assertEquals(Actual_text, "Speed Post");
	}
	
}
