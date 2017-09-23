package nlp.ner.trainingsetgeneration.pattern.more;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;
import nlp.ner.trainingsetgeneration.enums.Duration;
import nlp.ner.trainingsetgeneration.enums.NERType;
import nlp.ner.trainingsetgeneration.enums.Quantities;
import nlp.ner.trainingsetgeneration.pattern.IPattern;

public class NERQtyDescTenor implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERQtyDescTenor(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate(final NERLibrary nerLibrary) {
		instruments.values().stream().limit(getRandom10()).forEach(instrument -> {
			Arrays.stream(Quantities.values()).forEach(qty -> {
				Arrays.stream(Duration.values()).forEach(tenor -> {
					final StringBuilder builder = new StringBuilder();
					
					
					switch(nerLibrary) {
					case APACHE_OPEN_NLP:
						
						builder.append(getNERPattern(nerLibrary,
								new String[] {
										NERType.Qty.toString(), qty.getDescription(),
										NERType.Desc.toString(), instrument.getDescription(),
										NERType.Tenor.toString(), tenor.toString()	
								}));
						break;
					case STANFORD_CORE_NLP:
						
						builder.append(getNERPattern(nerLibrary,
								new String[]{
										qty.getDescription(), NERType.Qty.toString(),
										instrument.getDescription(), NERType.Desc.toString(),
										tenor.toString(), NERType.Tenor.toString()
								}));
						
						builder.append("\n");
						break;
					}
					
					addToListIfNotEmpty(builder, trainingSetElements);
				});
			});
		});
	}
}
