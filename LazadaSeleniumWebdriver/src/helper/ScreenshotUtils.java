package helper;

import java.io.*;

import org.apache.commons.io.*;
import org.openqa.selenium.*;

public class ScreenshotUtils {
	
	WebDriver driver;
	
	public ScreenshotUtils(WebDriver mainDriver) {
		driver = mainDriver;
	}
	
	public void CaptureMethod(String source) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(source));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
