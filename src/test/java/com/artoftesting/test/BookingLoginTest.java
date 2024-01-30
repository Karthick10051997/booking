package com.artoftesting.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.artoftesting.base.TestBase;
import com.artoftesting.base.TestDetail;
import com.artoftesting.pages.BookingPage;
import com.artoftesting.pages.LoginPage;


public class BookingLoginTest extends TestBase {

//	LoginPage loginPage;
	BookingPage bookingpage;
	//Log4j configuration
	
	
	@TestDetail(testCaseID ="TC_001",
			testCaseName ="Booking_Register_Mail",
			author ="karthick",
			    module ="Booking",
			    category = "sanity"
			)
	@Test(groups ="sanity")
	
	public void bookingservice() throws InterruptedException
	{
		System.out.println("testlogin");
		Screenshot("Login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterUsername();
		loginPage.enterPassword();
		loginPage.clickSignIn();
		Screenshot("Login successful");
		bookingpage = new BookingPage(driver);
		bookingpage.clickBookingModuleTab();
		bookingpage.clickBookingModuleOption();
		bookingpage.clickMailBookingOption();
		bookingpage.clickDomesticMailBookingOption();
		//bookingpage.enterOriginPincode("600019");
		bookingpage.enterDestinationPincode("600119");
		bookingpage.enterPhysicalWeight("100");
		bookingpage.clickSearchServicesButton();
		bookingpage.VerifyAvailableServices();
		Screenshot("Validating");
	}
	

}
