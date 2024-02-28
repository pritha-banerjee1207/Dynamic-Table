package Caw_studio_Maven;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DynamicTableTest {
	private WebDriver driver;
	private String url = "https://testpages.herokuapp.com/styled/tag/dynamic-table.html";
	
	
	private String jsonData = "[{\"name\":\"Bob\",\"age\":20,\"gender\":\"male\"},{\"name\":\"George\",\"age\":42,\"gender\":\"male\"},{\"name\":\"Sara\",\"age\":42,\"gender\":\"female\"},{\"name\":\"Conor\",\"age\":40,\"gender\":\"male\"},{\"name\":\"Jennifer\",\"age\":42,\"gender\":\"female\"}]";

	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\User\\eclipse-workspace\\Maven_Pro_Table\\Driver\\geckodriver.exe");
		 driver = new FirefoxDriver();
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	     driver.get(url);

	}

	@Test
	public void testTable() {
		WebElement tableDataButton = driver.findElement(By.xpath("/html/body/div[1]/div[3]/details"));
		tableDataButton.click();

		WebElement inputTextBox = driver.findElement(By.xpath("//*[@id=\"jsondata\"]"));
		inputTextBox.clear();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		inputTextBox.sendKeys(jsonData);
		
		WebElement refreshButton = driver.findElement(By.xpath("//*[@id=\"refreshtable\"]"));
		refreshButton.click();

		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='dynamictable']/tbody/tr"));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
			System.out.println(i);
			String name = columns.get(0).getText();
			int age = Integer.parseInt(columns.get(1).getText());
			String gender = columns.get(2).getText();

			// Retrieve the data from jsonData and assert with UI data
			String[] parts = jsonData.split(",");
			String expectedName = parts[i].split(":")[1].replace("\"", "").trim();
			int expectedAge = Integer.parseInt(parts[i + 1].split(":")[1].replace("\"", "").trim());
			String expectedGender = parts[i + 2].split(":")[1].replace("\"", "").trim();
            
            
			Assert.assertEquals(expectedName, name);
			Assert.assertEquals(expectedAge, age);
			Assert.assertEquals(expectedGender, gender); 

			i += 2; 
		}
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
