package com.artoftesting.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.Test;

import com.artoftesting.base.TestBase;
import com.artoftesting.pages.BookingPage;
import com.artoftesting.pages.LoginPage;


public class BookingLoginTest extends TestBase {

//	LoginPage loginPage;
	BookingPage bookingpage;
	//Log4j configuration
	private static final Logger log = LogManager.getLogger(SauceDemoLoginTest.class);
	
	@Test(testName = "Calculate Booking service")
	public void loginTest() throws InterruptedException{
		
		log.info("Verifying successful login.");
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterUsername();
		loginPage.enterPassword();
		loginPage.clickSignIn();
		bookingpage = new BookingPage(driver);
		bookingpage.clickBookingModuleTab();
		bookingpage.clickBookingModuleOption();
		bookingpage.clickMailBookingOption();
		bookingpage.clickDomesticMailBookingOption();
		bookingpage.enterOriginPincode("600019");
		bookingpage.enterDestinationPincode("600119");
		bookingpage.enterPhysicalWeight("100");
		bookingpage.clickSearchServicesButton();
		bookingpage.VerifyAvailableServices();
		
		
	}
}
