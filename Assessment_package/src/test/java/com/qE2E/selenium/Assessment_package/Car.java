package com.qE2E.selenium.Assessment_package;

import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Car {
	
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	
	String LoginData = "C:\\Users\\Admin\\Documents\\Assessment\\Car_Details.xlsx";
	
	
	
	

@Before
	public void setUp() {
	
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Documents\\Assessment\\chromedriver.exe");
		driver = new ChromeDriver();
		report = new ExtentReports("C:\\Users\\Admin\\Documents\\Assessment\\Reports.html");
		test = report.startTest("StartingTest");
	}
@After
	public void teardown() throws InterruptedException {
	
		Thread.sleep(20000);
		
		driver.quit();
		report.endTest(test);
		report.flush();

	}
@Test
public void excelTest() throws InterruptedException, IOException {
	
	ExcelUtils.setExcelFile(LoginData, 0);

	

	for (int i = 1; i < ExcelUtils.getExcelWSheet().getPhysicalNumberOfRows(); i++) {
		Thread.sleep(2000);
		
		String numplate = ExcelUtils.getCellData(i, 0);
		String makes = ExcelUtils.getCellData(i, 1);
		String colours = ExcelUtils.getCellData(i, 2);

		driver.get("https://vehicleenquiry.service.gov.uk/");
		test.log(LogStatus.INFO, "navigate to dvla site");

		test.log(LogStatus.INFO, "inputting new numberplate");
		
		WebElement UserElement = (new WebDriverWait(driver, 20)) .until(ExpectedConditions.presenceOfElementLocated(By.id("Vrm"))); 

		UserElement.click();
		UserElement.sendKeys(numplate);
		WebElement Continue = driver.findElement(By.name("Continue"));
		Continue.click();
		
		
		test.log(LogStatus.INFO, "Confirming Make");
		
		Screenshot.takeScreenShot(driver);
		
		WebElement MakeElement = (new WebDriverWait(driver, 20)) .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pr3\"]/div/ul/li[2]/span[2]/strong")));
		Assert.assertEquals(makes,MakeElement.getText());
		
		test.log(LogStatus.INFO, "Confirming Colour");
		
		WebElement ColourElement = (new WebDriverWait(driver, 20)) .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pr3\"]/div/ul/li[3]/span[2]/strong")));
		
		Assert.assertEquals(colours,ColourElement.getText());
		
		ExcelUtils.setCellData("Pass", i, 3);
		
		
	}

	}


		}

	


	


