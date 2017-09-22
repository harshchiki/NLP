package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.Currencies;

/**
 * @author Gaurav.Soni
 * <START:ISIN> NL0000103349 <END> <START:CURRENCY> AUD <END> <START:DESC> NETHER 7.5 01/15/23 <END> 
 * Library: Apache Open NLP
 */
public class NERPatternIsinCurrDesc implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternIsinCurrDesc(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate() {
		Arrays.stream(Currencies.values()).forEach(currency ->{
			instruments.values().forEach(instrument -> {
				final StringBuilder builder = new StringBuilder();
				
				builder.append(String.format("<START:ISIN> %s <END> <START:CURRENCY> %s <END> <START:DESC> %s <END> ", instrument.getInstrumentID(),
						instrument.getCurrentStr(), 
						instrument.getDescription()));
				
				addToListIfNotEmpty(builder, this.trainingSetElements);
			});			
		});
		
	}

}
