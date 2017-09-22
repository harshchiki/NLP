package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Dates;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Quantities;
/**
 * @author Gaurav.Soni
 * Format/Pattern: "XS1590496987 EUR CVALIM 8 1/4 04/12/27 0.5mm TOM-"
 * Library: Apache Open NLP
 */
public class NERPatternIsinDesQty  implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternIsinDesQty(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(Quantities.values()).forEach(qty -> {
			Arrays.stream(Dates.values()).forEach(date -> {
				getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument ->{
					StringBuilder builder = new StringBuilder();

					//append isin
					if(instrument.getIsin() != null && instrument.getIsin().length() > 0) {
						builder.append(String.format("<START:ISIN> %s <END> ", instrument.getIsin()));
					}

					// append desc
					if(instrument.getDescription() != null && instrument.getDescription().length() > 0) {
						builder.append(String.format(" <START:DESC> %s <END> ", instrument.getDescription()));
					}

					builder.append(String.format(" <START:QUANTITY> %s <END> <START:DATE> %s <END> ",
							qty.getDescription(), 
							date.getDescription()));

					if(builder.length() > 0) {
						this.trainingSetElements.add(builder.toString());
					}
				});
			});

		});
	}

}
