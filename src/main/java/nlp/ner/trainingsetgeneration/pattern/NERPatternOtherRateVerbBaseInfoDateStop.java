package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.internal.Lists;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.BaseInfoDescriptor;
import nlp.ner.trainingsetgeneration.enums.Dates;
import nlp.ner.trainingsetgeneration.enums.Rates;
import nlp.ner.trainingsetgeneration.enums.Verbs;




/**
 * @author Harsh.Chiki
 * Pattern: $$ 1% offered the following AAA/AA on open
 * OTHER OTHER VERB the BASEINFO AAA/AA on DATESTOP
 * OTHER VERB the BASEINFO AAA/AA on DATESTOP
 * Library: Apache Open NLP
 *
 */
public class NERPatternOtherRateVerbBaseInfoDateStop implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternOtherRateVerbBaseInfoDateStop(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
			Arrays.stream(Rates.values()).forEach(rate -> {
				Arrays.stream(Verbs.values()).forEach(verb -> {
					Arrays.stream(BaseInfoDescriptor.values()).forEach(baseInfo -> {
						Arrays.stream(Dates.values()).forEach(date -> {
							final StringBuilder builder = new StringBuilder();
							builder.append(String.format("<START:OTHER> $ <END> <START:OTHER> $ <END> <START:RATE> %s <END> <START:VERB> %s <END> the <START:BASE_INFO> %s <END> AAA/AA on <START:DATESTOP> %s <END>",
									rate.getDescription(),
									verb,
									baseInfo,
									date.getDescription()
									));
							
							addToListIfNotEmpty(builder, this.trainingSetElements);
						});
					});
				});
			});

		});
	}

}
