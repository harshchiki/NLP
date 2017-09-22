package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Quantities;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Gaurav.Soni
 * Pattern: Please
 * 			BID 5,000,000 T 0 5/8 02/15/43
 * Library: Apache Open NLP
 *
 */
public class NERPatternPleaseVerbQtyDesc implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternPleaseVerbQtyDesc(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			Arrays.stream(Verbs.values()).forEach(verb -> {
				Arrays.stream(Quantities.values()).forEach(quantity -> {
					final StringBuilder builder = new StringBuilder();
					builder.append(String.format("Please\n <START:VERB> %s <END> <START:QTY> %s <END> <START:DESC> %s <END> ",
							verb,
							quantity.getDescription(),
							instrument.getDescription()
							));
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
			});
		});
	}

}
