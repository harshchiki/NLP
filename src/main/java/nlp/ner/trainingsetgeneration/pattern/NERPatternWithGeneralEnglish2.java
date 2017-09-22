package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.Currencies;
import nlp.ner.trainingsetgeneration.enums.Dates;

/**
 * @author Harsh.Chiki
 * Format/Pattern: "out of today on open in $ w/ros"
 * 
 * This is only to parse some information from this sentence.
 * Though there is no indicator of base info here, there has to be some
 * other way we distinguish this as something that adds to the base information.
 * 
 * Library: Apache Open NLP
 */
public class NERPatternWithGeneralEnglish2 implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternWithGeneralEnglish2(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(Currencies.values()).forEach(currency -> {
			Arrays.stream(Dates.values()).forEach(dateStart -> {
				Arrays.stream(Dates.values()).forEach(dateStop -> {
					final StringBuilder builder = new StringBuilder();

					builder.append(String.format("out of <START:DATESTART> %s <END> on <START:DATESTOP> %s <END> in <START:CURRENCY> %s <END> w/ros",
							dateStart.getDescription(),
							dateStop.getDescription(),
							currency));

					addToListIfNotEmpty(builder, trainingSetElements);
				});
			});
		});
	}

}
