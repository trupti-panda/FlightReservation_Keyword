package testcases;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import excelInputAndOutput.ExcelInteraction;
import operation.ReadObject;
import operation.UIOperation;
import utility.Constant;
import utility.ReportGenerator;

public class ExecuteTest {
	
	ExcelInteraction excel;
	UIOperation operation;
	ReadObject object;
	ReportGenerator report;
	WebDriver driver;
	String status;
	Properties allObjects;
	@Parameters({"browser"})
	@BeforeClass
	public void setUp(String browser) throws IOException{
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", Constant.chromeDriverPath);
			driver = new ChromeDriver();
		}else if(browser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", Constant.geckoDriverPath);
			driver = new FirefoxDriver();
		}else if(browser.equals("ie")){
			System.setProperty("webdriver.ie.driver", Constant.ieDriverPath);
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		operation = new UIOperation(driver);
		excel = new ExcelInteraction();
		object = new ReadObject();
		allObjects = object.getObjectRepository();
		report = new ReportGenerator();
		
		// Delete Previous Test Execution Report
		File file = new File(Constant.textReportPath);
		if(file.exists()){
			file.delete();
		}
		
		// Add System Information To the Report
		
		report.generateReport("********************************************************************************");
		report.generateReport("Project Name : Flight Reservation");
		report.generateReport("Browser : "+browser);
		report.generateReport("OS : "+System.getProperty("os.name"));
		report.generateReport("Java Version : "+System.getProperty("java.version"));
		report.generateReport("User : "+System.getProperty("user.name"));
		InetAddress myHost = InetAddress.getLocalHost();
		report.generateReport("Host : "+myHost.getHostName());
		report.generateReport("********************************************************************************");
	}
	
	@Test
	public void executeTest(){
		try {
			Sheet sheet = excel.getSheet(Constant.filePath, Constant.fileName);
			int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
			for(int i=1;i<=rowCount;i++){
				Row row = sheet.getRow(i);
				if(row.getCell(0).getStringCellValue().length() == 0){
					status = operation.perform(allObjects, row.getCell(1).getStringCellValue().trim(), row.getCell(2).getStringCellValue().trim(), row.getCell(3).getStringCellValue().trim(), row.getCell(4).getStringCellValue().trim());
					report.generateReport(row.getCell(1).getStringCellValue().trim()+"---"+row.getCell(2).getStringCellValue().trim()+"---"+row.getCell(3).getStringCellValue().trim()+row.getCell(4).getStringCellValue().trim()+"---"+status);
					Cell cell = row.createCell(5);
					cell.setCellValue(status);
					CellStyle style = excel.getCellStyle(status);
					cell.setCellStyle(style);
				}else{
					report.generateReport("=============================================================================================================================================================================================");
					report.generateReport("New TestCase : "+row.getCell(0).getStringCellValue().trim());
					report.generateReport("=============================================================================================================================================================================================");
					Cell cell = row.createCell(5);
					CellStyle style = excel.getCellStyle("");
					cell.setCellStyle(style);
				}
			}
			
			excel.generateExcelReport(Constant.excelReportPath);
			excel.closeInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void closeDriver(){
		driver.quit();
	}

}
