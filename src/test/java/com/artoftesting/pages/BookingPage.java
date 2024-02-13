package com.artoftesting.pages;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.artoftesting.base.BasePage;


public class BookingPage extends BasePage {

	WebDriver driver;
	HashMap<String, String> data = new HashMap();

	public BookingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		data = testcasedata();
	}

	@FindBy(xpath = ("/html/body/div/header/nav/div/ul[1]/div[9]/div[1]/span"))
	WebElement Booking_Tab;

	@FindBy(xpath = "//a[text()='Booking']")
	WebElement Booking_Option;

	@FindBy(xpath = "//p[text()='Domestic Mail Booking']")
	WebElement domesticMailBooking_option;

	@FindBy(xpath = "//h1[text()='Mail Booking']")
	WebElement MailBooking_option;

	@FindBy(id = "originPincode")
	WebElement originPincodeclr_textbox;

	@FindBy(id = "destinationPincode")
	WebElement DestinationPincode_textbox;

	@FindBy(id = "boName")
	WebElement boname_textbox;

	@FindBy(id = "physicalWeight")
	WebElement physicalwt_textbox;

	@FindBy(xpath = ("//*[@id=\"content\"]/div/div[2]/div[2]/div[1]/form/div[1]/div/div[2]/div/div[1]/div/div[1]/select"))
	WebElement selectbookingtype_Dropdown;

	@FindBy(xpath = ("//*[@id=\"content\"]/div/div[2]/div[2]/div[1]/form/div[1]/div/div[2]/div/div[1]/div/div[2]/select"))
	WebElement selectmailservicetype_Dropdown;

	@FindBy(id = "priority")
	WebElement priority_checkbox;

	@FindBy(xpath = ("//*[@id=\"content\"]/div/div[2]/div[2]/div[1]/form/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div/div[1]/select"))
	WebElement selectmailshape_Dropdown;

	@FindBy(id = "radius")
	WebElement radius_textbox;

	@FindBy(id = "height")
	WebElement height_textbox;

	@FindBy(xpath = ("/html/body/div/div/div/div[1]/div/div[2]/div[2]/div[1]/form/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div/div[5]/button"))
	WebElement calculate_button;

	@FindBy(xpath = ("//button[text()='Next']"))
	WebElement next_button;

	public void moveToBookingTab() {
		Booking_Tab.click();
	}

	public void clickBooking() {
		Booking_Option.click();
	}

	public void clickMailBooking() {
		MailBooking_option.click();
	}

	public void clickDomesticMailBooking() {
		logger("Landing Page", 0,true);
		domesticMailBooking_option.click();
	}

	public void clearOriginPincode() {
		
		logger("clearOriginPincode",0,false);
		originPincodeclr_textbox.clear();
		originPincodeclr_textbox.sendKeys(Keys.CONTROL, Keys.chord("a"));
		originPincodeclr_textbox.sendKeys(Keys.BACK_SPACE);
		originPincodeclr_textbox.sendKeys(data.get("DomesticBooking_Orgin_Pincode"));

	}

	public void enterDestinationPincode() {
		logger("enterDestinationPincode",0,false);
		DestinationPincode_textbox.sendKeys(data.get("DomesticBooking_Destination_Pincode"));

	}

	public void enterBOName() {
		boname_textbox.sendKeys(data.get("DomesticBooking_BOName"));
	}

	public void enterphysicalwt() {
		physicalwt_textbox.sendKeys(data.get("DomesticBooking_Weight"));
	}

	public void selectbookingtype() {
		Select dropdown = new Select(selectbookingtype_Dropdown);
		dropdown.selectByValue(data.get("DomesticBooking_BookingType"));

	}

	public void selectmailservicetype() {
		Select dropdown = new Select(selectmailservicetype_Dropdown);
		dropdown.selectByValue(data.get("DomesticBooking_ServiceType"));

	}

	public void clickpriority() {
		// TODO Auto-generated method stub
		priority_checkbox.click();
	}

	public void selectmailshape() {
		Select dropdown = new Select(selectmailshape_Dropdown);
		dropdown.selectByVisibleText(data.get("DomesticBooking_selectmailshape"));

	}

	public void enterradius() {
		logger("Radius",0,false);
		radius_textbox.sendKeys(data.get("DomesticBooking_radius"));
	}

	public void enterheight() {
		logger("Height",0,false);
		height_textbox.sendKeys(data.get("DomesticBooking_height"));

	}

	public void clickcalculate() {
		calculate_button.click();
		logger("clickcalculate", 0,true);

	}

	public void clicknext() throws InterruptedException {
//	JavascriptExecutor js = (JavascriptExecutor) driver;
//	js.executeScript("window.scrollBy(0,)", "");
		scrollVertically(400);
 
		next_button.click();

	}

}
