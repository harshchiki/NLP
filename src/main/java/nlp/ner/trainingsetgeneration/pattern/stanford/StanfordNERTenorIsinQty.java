package nlp.ner.trainingsetgeneration.pattern.stanford;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.enums.Duration;
import nlp.ner.trainingsetgeneration.enums.NERType;
import nlp.ner.trainingsetgeneration.enums.Quantities;
import nlp.ner.trainingsetgeneration.pattern.IPattern;

public class StanfordNERTenorIsinQty implements IPattern{
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public StanfordNERTenorIsinQty(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		instruments.values().stream().limit(getRandom10()).forEach(instrument -> {
			Arrays.stream(Quantities.values()).forEach(qty -> {
				Arrays.stream(Duration.values()).forEach(tenor -> {
					final StringBuilder builder = new StringBuilder();
					builder.append("\n");
					
					builder.append(String.format("%s %s\n", tenor, NERType.Tenor));
					builder.append(String.format("%s %s\n", instrument.getIsin(), NERType.ISIN));
					builder.append(String.format("%s %s\n", qty.getDescription(), NERType.Qty));
					
					builder.append("\n");
					addToListIfNotEmpty(builder, trainingSetElements);
				});
			});
		});
	}
}
