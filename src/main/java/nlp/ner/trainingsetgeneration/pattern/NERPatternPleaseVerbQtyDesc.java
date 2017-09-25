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
	public void generate(NERLibrary nerLibrary) {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			Arrays.stream(Verbs.values()).forEach(verb -> {
				Arrays.stream(Quantities.values()).forEach(quantity -> {
					final StringBuilder builder = new StringBuilder();
					builder.append(String.format("Please\n <START:VERB> %s <END> <START:QTY> %s <END> %s ",
							verb,
							quantity.getDescription(),
//							instrument.getDescription()
							getRandomDescription(NERLibrary.APACHE_OPEN_NLP)
							));
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
			});
		});
	}

}
