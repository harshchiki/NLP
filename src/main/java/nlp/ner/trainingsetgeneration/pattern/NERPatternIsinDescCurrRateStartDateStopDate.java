package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.Dates;
import nlp.ner.trainingsetgeneration.enums.Rates;

/**
 * @author Gaurav.Soni
 * Format/Pattern: HCC-ABCD  <START:ISIN> DE0001142644 <END> <START:DESC> DB 2 02/17/21 <END> <START:CURRENCY> EUR <END>    <START:RATE> 1.41% <END>  <START:STARTDATE> 23/May/16 <END> <START:STOPDATE> 28/May/18 <END>
 * Library: Apache Open NLP
 */
public class NERPatternIsinDescCurrRateStartDateStopDate implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternIsinDescCurrRateStartDateStopDate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate() {
		Arrays.stream(Rates.values()).forEach(rate -> {
			Arrays.stream(Dates.values()).forEach(date -> {			
				getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
					final StringBuilder builder = new StringBuilder();
					
					builder.append(String.format("HCC-ABCD <START:ISIN> %s <END> ", instrument.getIsin()));
					
					if(instrument.getDescription() != null && instrument.getDescription().length() > 0){
						builder.append(String.format("<START:DESC> %s <END> ", instrument.getDescription()));
					}
					
					if(instrument.getCurrentStr() != null && instrument.getCurrentStr().length() > 0){
						builder.append(String.format("<START:CURRENCY> %s <END> ", instrument.getCurrentStr()));
					}
					
					// TODO: better management of start date and stop date to be done. As of now, they will be same values
					builder.append(String.format("<START:RATE> %s <END> <START:STARTDATE> %s <END> <START:STOPDATE> %s <END> ",
							rate.getDescription(),
							date.getDescription(), 
							date.getDescription()));
					
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
			});
		});
		
	}

}
