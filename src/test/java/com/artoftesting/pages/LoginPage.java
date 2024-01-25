package com.artoftesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
WebDriver driver;
	
	@FindBy(xpath ="/html/body/section/div/div/div[2]/div/div/form/div[1]/div/input")
	WebElement username_Textbox;
	
	@FindBy(xpath="/html/body/section/div/div/div[2]/div/div/form/div[2]/div/input")
	WebElement password_Textbox;
	
	@FindBy(xpath="/html/body/section/div/div/div[2]/div/div/form/button")
	WebElement signIn_Button;
    

	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(driver, this);
	}
	
	public void enterUsername() throws InterruptedException
	{
		username_Textbox.sendKeys("Karthick");
		Thread.sleep(5);
	}
	
	public void enterPassword()
	{
		password_Textbox.sendKeys("pass123");
	}
	
	public void clickSignIn()
	{
		signIn_Button.click();
	}
	
}
