package nlp.ner.trainingsetgeneration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Reads data from RefData_Data_TSV file(RefData's data) and returns a map with Instrument which can be used for training set generation.
 * @author Gaurav.Soni
 */
public class InstrumentsDataUtil {
	private static final String INSTRUMENT_TSV_FILE="RefData_Data_TSV.txt";

	public static final Map<String, Instrument> ALL_INSTRUMENTS;
	
	static{
		ALL_INSTRUMENTS=getInstrumentsData();
	}
	
	private static Map<String, Instrument> getInstrumentsData(){
		final Map<String, Instrument >instruments = new HashMap<>();
		int count = 0;

		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader(INSTRUMENT_TSV_FILE));
			String line = "";
			while((line = reader.readLine()) != null){
				if(count > 0) {
					final String[] values = line.split("\t");
					final String instrumentId = values[0];
					final String isin = values[1];
					final String desc = values[2];
					final String currencyStr = values[3];
					final String date = values[4];
					instruments.put(instrumentId, new Instrument(instrumentId, isin, desc, currencyStr, date));
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		finally{
			try {
				if(reader!=null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instruments;
	}
}
