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
public class NERDescIsinQty implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	

	public NERDescIsinQty(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(final NERLibrary nerLibrary) {
		instruments.values().stream().limit(getRandom10()).forEach(instrument -> {
			Arrays.stream(Quantities.values()).forEach(qty -> {
				final StringBuilder builder = new StringBuilder();
				
				
				switch(nerLibrary) {
				case APACHE_OPEN_NLP:
					
					builder.append(
//							getRandomDescription(nerLibrary)
							"<START:"+NERType.Desc+"> "+getSecurityDescriptionToken()+" <END>"
							);
					
					builder.append(getNERPattern(nerLibrary,
							new String[] {
//									NERType.Desc.toString(), instrument.getDescription(),
//									NERType.Desc.toString(), getRandomDescription(nerLibrary),
									NERType.ISIN.toString(), instrument.getIsin(),
									NERType.Qty.toString(), getSecurityDescriptionToken()//qty.getDescription()
							}));
					break;
				case STANFORD_CORE_NLP:
					
					builder.append(getNERPattern(nerLibrary,
							new String[] {
//									instrument.getDescription(), NERType.Desc.toString(),
									getRandomDescription(nerLibrary), NERType.Desc.toString(),
									instrument.getIsin(), NERType.ISIN.toString(),
									qty.getDescription(), NERType.Desc.toString()
							}));
					
					builder.append("\n");
					break;
				}
				

				addToListIfNotEmpty(builder, trainingSetElements);
			});
		});
	}
}
