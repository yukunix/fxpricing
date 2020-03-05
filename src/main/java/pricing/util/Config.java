package pricing.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	public static final Properties getProp(String configFile) throws IOException {
		InputStream is = Config.class.getResourceAsStream(configFile);
		Properties prop = new Properties();
		prop.load(is);
		return prop;
	}

}
