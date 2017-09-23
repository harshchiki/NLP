package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;
import nlp.ner.trainingsetgeneration.enums.Quantities;
import nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Gaurav.Soni
 * Pattern: Please
 * 			AUST CPN 0% JUL25 BID 5,000,000
 * Library: Apache Open NLP
 *
 */
public class NERPatternPleaseDescVerbQty implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternPleaseDescVerbQty(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(NERLibrary nerLibrary) {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			Arrays.stream(Verbs.values()).forEach(verb -> {
				Arrays.stream(Quantities.values()).forEach(quantity -> {
					final StringBuilder builder = new StringBuilder();
					builder.append(String.format("Please\n <START:DESC> %s <END> <START:VERB> %s <END> <START:QTY> %s <END>",
							instrument.getDescription(),
							verb,
							quantity.getDescription()
							));
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
			});
		});
	}

}
