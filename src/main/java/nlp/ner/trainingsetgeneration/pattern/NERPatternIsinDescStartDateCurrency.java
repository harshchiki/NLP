package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;
import nlp.ner.trainingsetgeneration.enums.Currencies;
import nlp.ner.trainingsetgeneration.enums.Dates;

/**
 * @author Gaurav.Soni
 * Pattern/Format: "USP7807HAV70 PDVSA 8.500 27OCT20     tom- 0.5M USD"
 * Library: Apache Open NLP
 */
public class NERPatternIsinDescStartDateCurrency implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternIsinDescStartDateCurrency(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(NERLibrary nerLibrary) {
		Arrays.stream(Currencies.values()).forEach(currency -> {
			Arrays.stream(Dates.values()).forEach(date -> {
				getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument ->{

					StringBuilder builder = new StringBuilder();

					//append isin
					if(instrument.getIsin() != null && instrument.getIsin().length() > 0) {
						builder.append(String.format("<START:ISIN> %s <END> ", instrument.getIsin()));
					}

					// append desc
					if(instrument.getDescription() != null && instrument.getDescription().length() > 0) {
						builder.append(String.format(" <START:DESC> %s <END> ", instrument.getDescription()));
					}


					builder.append(String.format(" <START:STARTDATE> %s <END> <START:QUANTITY> 27MM <END> ",date.getDescription(),date.getDescription()));

					// append rate
					builder.append(String.format(" <START:CURRENCY> %s <END> ",currency));

					addToListIfNotEmpty(builder, this.trainingSetElements);
				});

			});
		});
	}

}
