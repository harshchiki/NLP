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
 * 			BID UK WAR LOAN 3 ½ 29/12/4 GB0009386284 5m
 * Library: Apache Open NLP
 *
 */
public class NERPatternPleaseVerbDescIsinQty implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternPleaseVerbDescIsinQty(final List<String> trainingSetElements,
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
					builder.append(String.format("Please\n <START:VERB> %s <END> <START:DESC> %s <END> <START:ISIN> %s <END> <START:QTY> %s <END>",
							verb,
							instrument.getDescription(),
							instrument.getIsin(),
							quantity.getDescription()
							));
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
			});
		});
	}

}
