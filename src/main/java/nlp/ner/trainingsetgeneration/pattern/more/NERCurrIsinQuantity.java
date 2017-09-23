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
public class NERCurrIsinQuantity implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERCurrIsinQuantity(final List<String> trainingSetElements,
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
										NERType.Currency.toString(), curr.toString(),
										NERType.ISIN.toString(), instrument.getIsin(),
										NERType.Qty.toString(), qty.getDescription()
								}));
						break;
					case STANFORD_CORE_NLP:
						
						
						builder.append(getNERPattern(NERLibrary.STANFORD_CORE_NLP,
								new String[]{curr.toString(), NERType.Currency.toString(),
								instrument.getIsin(), NERType.ISIN.toString(),
								qty.getDescription(), NERType.Qty.toString()}));
						builder.append("\n");
						
						addToListIfNotEmpty(builder, trainingSetElements);
						break;
					}
				});
			});
		});
	}
}
