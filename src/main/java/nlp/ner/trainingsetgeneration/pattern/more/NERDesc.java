package nlp.ner.trainingsetgeneration.pattern.more;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;
import nlp.ner.trainingsetgeneration.enums.Currencies;
import nlp.ner.trainingsetgeneration.enums.NERType;
import nlp.ner.trainingsetgeneration.enums.Quantities;
import nlp.ner.trainingsetgeneration.pattern.IPattern;

/**
 * 
 * @author harshchiki
 *
 */
public class NERDesc implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERDesc(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(final NERLibrary nerLibrary) {
		
		
		instruments.values().stream().limit(getRandom10()).forEach(instrument -> {
			Arrays.stream(Currencies.values()).forEach(curr -> {
				Arrays.stream(Quantities.values()).forEach(qty -> {
					final StringBuilder builder = new StringBuilder();
					builder.append("\n");
					
					switch(nerLibrary) {
					case APACHE_OPEN_NLP:
						
						builder.append(getNERPattern(NERLibrary.APACHE_OPEN_NLP,
								new String[] {
										NERType.Desc.toString(), instrument.getDescription()
								}));
						break;
					case STANFORD_CORE_NLP:
						
						
						builder.append(getNERPattern(NERLibrary.STANFORD_CORE_NLP,
								new String[] {instrument.getDescription(), NERType.Desc.toString()}));
						builder.append("\n");
						
						addToListIfNotEmpty(builder, trainingSetElements);
						break;
					}
				});
			});
		});
	}
}
