package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;
import nlp.ner.trainingsetgeneration.enums.Currencies;
import nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Harsh.Chiki
 * Pattern/Format: "<CURRENCY> Please <VERB>"
 * Library: Apache Open NLP
 */

// TODO: redundant class. Remove
public class NERPatternCurrencyPleaseVerb implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternCurrencyPleaseVerb(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(NERLibrary nerLibrary) {
		Arrays.stream(Currencies.values()).forEach(currency -> {
			Arrays.stream(Verbs.values()).forEach(verb -> {
				final StringBuilder builder = new StringBuilder();
				
				builder.append(String.format("<START:CURRENCY> %s <END> Please <START:VERB> %s <END",
						currency,
						verb));
				
				addToListIfNotEmpty(builder, trainingSetElements);
			});
		});
	}

}
