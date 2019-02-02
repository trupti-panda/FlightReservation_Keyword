package operation;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class UIOperation {
	
	WebDriver driver;
	private Logger logger = Logger.getLogger("flightReservation");
	public UIOperation(WebDriver driver){
		
		this.driver = driver;
		
	}
	
	
	public String perform(Properties p, String operation,String objectType,String objectName,String value){
		String status = "Pass";
		try{
		
		switch(operation.toUpperCase()){
		case "GOTOURL":
			logger.info("Invoking Application Unser Test by using URL : "+p.getProperty(objectName));
			String url = p.getProperty(objectName);
			driver.get(url);
			driver.manage().window().maximize();
			logger.info("Invoked Application Unser Test by using URL : "+p.getProperty("url"));
			break;
		case "VERIFYTITLE":
			logger.info("Verifying Title : "+value);
			if(!driver.getTitle().equals(value)){
				logger.error("Status -- Failed | #Expected Title : "+value+" | #Actual Title : "+driver.getTitle());
				status = "Fail";
			}
			logger.info("Verified Title : "+value);
			break;
		case "TYPE":
			logger.info("Entering '"+value+"' in the '"+objectName+"' edit field.");
			WebElement elemntToBeTyped = driver.findElement(this.getObject(p,objectType,objectName));
			highlightElement(elemntToBeTyped);
			elemntToBeTyped.sendKeys(value);
			logger.info("Entered '"+value+"' in the '"+objectName+"' edit field.");
			break;
		case "CLICK":
			logger.info("Clicking on '"+objectName+"'");
			WebElement elementToBeClicked = driver.findElement(this.getObject(p, objectType, objectName));
			highlightElement(elementToBeClicked);
			elementToBeClicked.click();
			logger.info("Clicked on '"+objectName+"'");
			break;
		case "SELECT":
			logger.info("Selecting '"+value+"' from the '"+objectName+"' drop down.");
			WebElement elemntToBeSelected = driver.findElement(this.getObject(p,objectType,objectName));
			highlightElement(elemntToBeSelected);
			Select select = new Select(elemntToBeSelected);
			select.selectByVisibleText(value);
			logger.info("Selected '"+value+"' from the '"+objectName+"' drop down.");
			break;
		case "VERIFYELEMENTPRESENT":
			logger.info("Verifying presence of '"+objectName+" on web page.");
			if(!driver.findElement(this.getObject(p, objectType, objectName)).isDisplayed()){
				logger.error("Status -- Failed | '"+objectName+" was not found on web page.");
				status = "Fail";
			}else{
				highlightElement(driver.findElement(this.getObject(p, objectType, objectName)));
			}
			logger.info("Verified presence of '"+objectName+" on web page.");
			break;
		case "VERIFYTEXT":
			logger.info("Verifying Text : "+value);
			highlightElement(driver.findElement(this.getObject(p, objectType, objectName)));
			String expText = driver.findElement(this.getObject(p, objectType, objectName)).getText();
			if(!expText.contains(value)){
				logger.error("Status -- Failed | '"+expText+" was not found on web page.");
				status = "Fail";
			}
			break;
		default:
			logger.error("Status -- Failed | Wrong Operation Type");
			status = "Fail";
		}
		}catch(Exception ex){
			logger.error("Status -- Failed | Excepion occurred due to : "+ex.getMessage());
			status = "Fail";
		}
		
		return status;
	}
	
	private void highlightElement(WebElement element){
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow;border: 2px solid red;');", element);
		
	}


	private By getObject(Properties p, String objectType, String objectName) {
		if(objectType.equals("ID")){
			return By.id(p.getProperty(objectName));
		}else if(objectType.equals("NAME")){
			return By.name(p.getProperty(objectName));
		}else if(objectType.equals("XPATH")){
			return By.xpath(p.getProperty(objectName));
		}else if(objectType.equals("CSS")){
			return By.cssSelector(p.getProperty(objectName));
		}else if(objectType.equals("CLASSNAME")){
			return By.className(p.getProperty(objectName));
		}else if(objectType.equals("LINKTEXT")){
			return By.linkText(p.getProperty(objectName));
		}else if(objectType.equals("PARTIALLINKTEXT")){
			return By.partialLinkText(p.getProperty(objectName));
		}else{
			return By.tagName(p.getProperty(objectName));
		}
	}
}
