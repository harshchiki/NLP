package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Currencies;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Harsh.Chiki
 * Pattern/Format: "<CURRENCY> Please <VERB>"
 * Library: Apache Open NLP
 */
public class NERPatternCurrPleaseVerb implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternCurrPleaseVerb(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(Currencies.values()).forEach(currency -> {
			Arrays.stream(Verbs.values()).forEach(verb -> {
				final StringBuilder builder = new StringBuilder();
				
				builder.append(String.format("<START:CURRENCY> %s <END> Please <START:VERB> %s <END>",
						currency,
						verb));
				
				addToListIfNotEmpty(builder, trainingSetElements);
			});
		});
	}

}
