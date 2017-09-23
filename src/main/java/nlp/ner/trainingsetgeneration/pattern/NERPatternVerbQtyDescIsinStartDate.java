package nlp.ner.trainingsetgeneration.pattern;

import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;

/**
 * @author Harsh.Chiki
 * Format/Pattern: "BID - 37,000,000  US CONV GILT  RegS 1.5 [ DE0001142644 ] S/D 21/"
 * Library: Apache Open NLP
 */
public class NERPatternVerbQtyDescIsinStartDate implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternVerbQtyDescIsinStartDate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate(NERLibrary nerLibrary) {
		
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument ->{
			final StringBuilder builder = new StringBuilder();
			builder.append("<START:VERB> BID <END> - <START:QTY> 37,000,000 <END> ");
			
			//append desc
			if(instrument.getDescription() != null && instrument.getInstrumentID().length() > 0) {
				builder.append(String.format("<START:DESC> %s <END> ", instrument.getDescription()));
			}

			// append isin
			if(instrument.getIsin() != null && instrument.getIsin().length() > 0) {
				builder.append(String.format("[ <START:ISIN> %s <END> ]", instrument.getIsin()));
			}

			builder.append(" S/D <START:STARTDATE> 25/ <END> <START:RATE> 3% <END> ");
			
			addToListIfNotEmpty(builder, this.trainingSetElements);
		});
	}

}
