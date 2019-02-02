package operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadObject {
	Properties p = new Properties();
	
	public Properties getObjectRepository() throws IOException{
		
		File file = new File(System.getProperty("user.dir")+"\\src\\objects.properties");
		FileInputStream stream = new FileInputStream(file);
		p.load(stream);
		return p;
		
	}

}
