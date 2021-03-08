package rpa.ml.extractor.constants;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigEnum {
	public void loadConfig() {
		
		try (InputStream fileInput = new FileInputStream("resources/config.properties")) {
			
			Properties properties = new Properties();
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		 
	}
}
