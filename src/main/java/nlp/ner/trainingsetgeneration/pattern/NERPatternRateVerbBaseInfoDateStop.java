package nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.BaseInfoDescriptor;
import nlp.ner.trainingsetgeneration.enums.Dates;
import nlp.ner.trainingsetgeneration.enums.Rates;
import nlp.ner.trainingsetgeneration.enums.Verbs;


/**
 * @author Gaurav.Soni
 * <START:RATE> 15% <END> <START:VERB> offered <END> the <START:COMMON_INFO> following <END> AAA/AA on <START:DATESTOP> open <END> for <START:COMMON_INFO> these <END>
 * Library: Apache Open NLP
 */
public class NERPatternRateVerbBaseInfoDateStop implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternRateVerbBaseInfoDateStop(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate() {
		Arrays.stream(Rates.values()).forEach(rate -> {
			Arrays.stream(BaseInfoDescriptor.values()).forEach(baseInforDescriptor ->{
				Arrays.stream(Verbs.values()).forEach(verb -> {
					Arrays.stream(Dates.values()).forEach(date -> {
						Arrays.stream(BaseInfoDescriptor.values()).forEach(secondBaseInfo ->{
							final StringBuilder builder = new StringBuilder();
							
							builder.append(String.format("<START:RATE> %s <END> <START:VERB> %s <END> the <START:BASE_INFO> %s <END> AAA/AA on <START:DATESTOP> %s <END> for <START:BASE_INFO> %s <END> ",
									rate.getDescription(),
									verb,
									baseInforDescriptor,
									date.getDescription(),
									secondBaseInfo));
							
							addToListIfNotEmpty(builder, this.trainingSetElements);
						});
					});
				});
			});
		});
	}

}
