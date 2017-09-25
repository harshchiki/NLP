package nlp.ner.trainingsetgeneration.pattern.more;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;
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
public class NERIsinQuantityVerbDescStartDateEndDate implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERIsinQuantityVerbDescStartDateEndDate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(final NERLibrary nerLibrary) {
		instruments.values().stream().limit(getRandom10()).forEach(instrument -> {
			Arrays.stream(Quantities.values()).forEach(qty -> {
				Arrays.stream(Verbs.values()).forEach(verb -> {
					Arrays.stream(Dates.values()).forEach(startDate -> {
						Arrays.stream(Dates.values()).forEach(endDate -> {
							final StringBuilder builder = new StringBuilder();
							
							
							switch(nerLibrary) {
							case APACHE_OPEN_NLP:
								
								builder.append(getNERPattern(nerLibrary,
										new String[] {
												NERType.ISIN.toString(), instrument.getIsin(),
												NERType.Qty.toString(), qty.getDescription(),
												NERType.Verb.toString(), verb.toString()
//												NERType.Desc.toString(), instrument.getDescription(),
										}));
								
								builder.append(getRandomDescription(nerLibrary));
								
								builder.append(getNERPattern(nerLibrary,
										new String[] {
										NERType.StartDate.toString(), startDate.getDescription(),
										NERType.EndDate.toString(), endDate.getDescription()
								}));
								
								break;
							case STANFORD_CORE_NLP:
								
								builder.append(getNERPattern(nerLibrary,
										new String[] {
												instrument.getIsin(), NERType.ISIN.toString(),
//												qty.getDescription(), NERType.Qty.toString(),
												getRandomDescription(nerLibrary), NERType.Qty.toString(),
												verb.toString(), NERType.Verb.toString(),
												instrument.getDescription(), NERType.Desc.toString(),
												startDate.getDescription(), NERType.StartDate.toString(),
												endDate.getDescription(), NERType.EndDate.toString()
										}));
								
								builder.append("\n");
								break;
							}
							
							
							
							addToListIfNotEmpty(builder, trainingSetElements);
						});
					});
				});
			});
		});
		
	}
}
