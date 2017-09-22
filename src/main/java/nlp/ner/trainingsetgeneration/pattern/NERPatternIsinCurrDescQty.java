package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.Currencies;
import nlp.ner.trainingsetgeneration.enums.Dates;
import nlp.ner.trainingsetgeneration.enums.Quantities;
/**
 * @author Gaurav.Soni
 *Pattern/Format: "XS1523250295 EUR EMN 1 7/8 11/23/26    0.5mm 19/4-"
 * Library: Apache Open NLP
 */
public class NERPatternIsinCurrDescQty implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternIsinCurrDescQty(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(Quantities.values()).forEach(qty -> {
			Arrays.stream(Currencies.values()).forEach(currency -> {
				Arrays.stream(Dates.values()).forEach(date -> {
					getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument ->{

						StringBuilder builder = new StringBuilder();

						//append isin
						if(instrument.getIsin() != null && instrument.getIsin().length() > 0) {
							builder.append(String.format("<START:ISIN> %s <END> ", instrument.getIsin()));
						}

						//append curr
						builder.append(String.format("<START:CURRENCY> %s <END> ", currency));

						// append desc
						if(instrument.getDescription() != null && instrument.getDescription().length() > 0) {
							builder.append(String.format(" <START:DESC> %s <END> ", instrument.getDescription()));
						}

						builder.append(String.format(" <START:QUANTITY> %s <END> <START:DATE> %s <END> ",
								qty.getDescription(),
								date.getDescription()));

						if(builder.length() > 0) {
							this.trainingSetElements.add(builder.toString());
						}
					});

				});
			});
		});


	}

}
