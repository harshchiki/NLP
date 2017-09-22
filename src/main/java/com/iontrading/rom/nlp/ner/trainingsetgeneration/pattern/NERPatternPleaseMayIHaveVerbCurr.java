package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Currencies;

/**
 * @author Harsh.Chiki
 * Pattern/Format: "Please may I have <CURRENCY>"
 * Library: Apache Open NLP
 */
public class NERPatternPleaseMayIHaveVerbCurr implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternPleaseMayIHaveVerbCurr(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(Currencies.values()).forEach(currency -> {
			final StringBuilder builder = new StringBuilder();
			
			builder.append(String.format("Please may I <START:VERB> have <END> <START:CURRENCY> %s <END>",
					currency));
			
			addToListIfNotEmpty(builder, trainingSetElements);
		});
	}

}
