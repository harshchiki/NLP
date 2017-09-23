package nlp.stanford.ner.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StanfordNERProperties {
	private final String PROPERTIES_FILE_NAME;

	public StanfordNERProperties() {
		PROPERTIES_FILE_NAME = "stanford_ner.properties";
	}

	public StanfordNERProperties(final String propertiesFileName) {
		PROPERTIES_FILE_NAME = propertiesFileName;
	}

	public Properties getProperties() {
		FileReader reader;
		Properties p=new Properties();  
		try {
			reader = new FileReader(PROPERTIES_FILE_NAME);
			p.load(reader);
		} catch (FileNotFoundException e1) {
		}  catch(IOException e) {

		}

		return p;
	}
}
