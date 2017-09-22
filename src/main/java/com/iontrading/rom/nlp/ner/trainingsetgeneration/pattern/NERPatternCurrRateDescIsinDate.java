package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Currencies;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Dates;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Rates;

/**
 * @author Gaurav.Soni
 * Example: USD 20.00 ERSTAA 1 5/8 02/21/19 XS1568004060 13/4-18/4
 * Pattern: CURR RATE DESC ISIN STARTDATE STOPDATE
 * Library: Apache Open NLP
 *
 */
public class NERPatternCurrRateDescIsinDate implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternCurrRateDescIsinDate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate() {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			Arrays.stream(Currencies.values()).forEach(currency -> {
				Arrays.stream(Rates.values()).forEach(rate -> {
					Arrays.stream(Dates.values()).forEach(dateStart ->{
							final StringBuilder builder = new StringBuilder();
							
							builder.append(String.format("<START:CURRENCY> %s <END> <START:RATE> %s <END> <START:DESC> %s <END> <START:ISIN> %s <END> <START:DATE> %s <END>",
									currency,
									rate.getDescription(),
									instrument.getDescription(),
									instrument.getIsin(),
									dateStart.getDescription()
									));
							
							addToListIfNotEmpty(builder, this.trainingSetElements);
							
						});
					});
				});
			});
	}

}
