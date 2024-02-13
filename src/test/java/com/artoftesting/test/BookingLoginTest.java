package com.artoftesting.test;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.artoftesting.base.TestBase;
import com.artoftesting.base.TestDetail;
import com.artoftesting.pages.BookingPage;
import com.artoftesting.pages.LoginPage;
import com.artoftesting.util.ExcelUtil;


public class BookingLoginTest extends TestBase {

	LoginPage loginPage;
	BookingPage bookingpage;
	//Log4j configuration
	
	
	@TestDetail(testCaseID ="TC001",
			testCaseName ="Booking_Register_Mail",
			author ="Karthick",
			    module ="Booking",
			    category = "SANITY"
			)
	@Test(groups ="SANITY")
	
	public void bookingservice() throws Exception
	{
		
//		loginPage = new LoginPage(driver);
//		loginPage.enterUsername();
//		loginPage.enterPassword();
//		loginPage.clickSignIn();
//		Screenshot("clickSignIn", 0);
		bookingpage = new BookingPage(driver);
//		bookingpage.moveToBookingTab();
//		bookingpage.clickBooking();
  //   	bookingpage.clickMailBooking();
		bookingpage.clickDomesticMailBooking();
		bookingpage.clearOriginPincode();
		bookingpage.enterDestinationPincode();
		bookingpage.enterBOName();
		bookingpage.enterphysicalwt();
		bookingpage.selectbookingtype();
		bookingpage.selectmailservicetype();
		bookingpage.clickpriority();
		bookingpage.selectmailshape();
		bookingpage.enterradius();
		bookingpage.enterheight(
				);
		Thread.sleep(2000);
		bookingpage.clickcalculate();
		Thread.sleep(2000);
		bookingpage.clicknext();
		//Thread.sleep(2000);
	
	}
}
