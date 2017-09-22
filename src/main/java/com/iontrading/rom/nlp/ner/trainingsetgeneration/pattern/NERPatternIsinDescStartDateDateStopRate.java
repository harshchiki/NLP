package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Dates;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Rates;
/**
 * @author Gaurav.Soni
 * Pattern/Format: "1898  GB00B128DP45  UKT 4 1/4 12/07/46   4/20/2017  7/20/2017       0.7"
 * Library: Apache Open NLP
 */
public class NERPatternIsinDescStartDateDateStopRate implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternIsinDescStartDateDateStopRate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(Rates.values()).forEach(rate -> {
			Arrays.stream(Dates.values()).forEach(date -> {
				getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument ->{
					
					StringBuilder builder = new StringBuilder();
					
					//append isin
					if(instrument.getIsin() != null && instrument.getIsin().length() > 0) {
						builder.append(String.format("1929 <START:ISIN> %s <END> ", instrument.getIsin()));
					}
					
					// append desc
					if(instrument.getDescription() != null && instrument.getDescription().length() > 0) {
						builder.append(String.format(" <START:DESC> %s <END> ", instrument.getDescription()));
					}
					
					
					builder.append(String.format(" <START:STARTDATE> %s <END> <START:STOPDATE> %s <END> ",date.getDescription(),date.getDescription()));
					
					// append rate
					builder.append(String.format("<START:RATE> %s <END> ", rate.getDescription()));
					
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
				
			});
			
		});
	}

}
