package nlp.ner.trainingsetgeneration.pattern;

import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;

/**
 * @author Harsh.Chiki
 * <START:ISIN> NL0000103349 <END> <START:DESC> NETHER 7.5 01/15/23 <END>
 * Library: Apache Open NLP
 */
public class NERPatternIsinDesc implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternIsinDesc(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate(NERLibrary nerLibrary) {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			final StringBuilder builder = new StringBuilder();
			
			builder.append(String.format("<START:ISIN> %s <END> <START:DESC> %s <END> ", instrument.getInstrumentID(), instrument.getDescription()));
			
			addToListIfNotEmpty(builder, this.trainingSetElements);
		});
	}

}
