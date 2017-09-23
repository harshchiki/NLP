package nlp.ner.trainingsetgeneration.pattern;

import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;

/**
 * @author Harsh.Chiki
 * Format/Pattern: <START:ISIN> US912810EK08 <END> <START:QUANTITY> 3,200,200 <END> <START:DESC> FRTR 2.1 07/25/23 <END>
 * Library: Apache Open NLP
 */
public class NERPatternIsinQtyDesc implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternIsinQtyDesc(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate(NERLibrary nerLibrary) {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			final StringBuilder builder = new StringBuilder();
			
			builder.append(String.format("<START:ISIN> %s <END> <START:QUANTITY> 3,200,200 <END> ", instrument.getIsin()));
			
			if(instrument.getDescription() != null && instrument.getDescription().length() > 0){
				builder.append(String.format("<START:DESC> %s <END>", instrument.getDescription()));
			}
			
			
			addToListIfNotEmpty(builder, this.trainingSetElements);
		});
	}

}
