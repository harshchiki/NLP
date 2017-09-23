package nlp.ner.trainingsetgeneration.pattern.stanford;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.Dates;
import nlp.ner.trainingsetgeneration.enums.NERType;
import nlp.ner.trainingsetgeneration.enums.Quantities;
import nlp.ner.trainingsetgeneration.enums.Verbs;
import nlp.ner.trainingsetgeneration.pattern.IPattern;

/**
 * 
 * @author harshchiki
 *
 */
public class StanfordNERIsinQuantityVerbDescStartDateEndDate implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public StanfordNERIsinQuantityVerbDescStartDateEndDate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		instruments.values().stream().limit(getRandom10()).forEach(instrument -> {
			Arrays.stream(Quantities.values()).forEach(qty -> {
				Arrays.stream(Verbs.values()).forEach(verb -> {
					Arrays.stream(Dates.values()).forEach(startDate -> {
						Arrays.stream(Dates.values()).forEach(endDate -> {
							final StringBuilder builder = new StringBuilder();
							
							builder.append("\n");
							
							builder.append(String.format("%s %s\n", instrument.getIsin(), NERType.ISIN));
							builder.append(String.format("%s %s\n", qty.getDescription(), NERType.Qty));
							builder.append(String.format("%s %s\n", verb, NERType.Verb));
							builder.append(String.format("%s %s\n", instrument.getDescription(), NERType.Desc));
							builder.append(String.format("%s %s\n", startDate.getDescription(), NERType.StartDate));
							builder.append(String.format("%s %s", endDate.getDescription(), NERType.EndDate));
							
							
							builder.append("\n");
							
							addToListIfNotEmpty(builder, trainingSetElements);
						});
					});
				});
			});
		});
		
	}
}
