package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Dates;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Quantities;

/**
 * @author Gaurav.Soni
 * <START:OTHER> ***Looking for <END> <START:ISIN> DE0001142644 <END> <START:DESC> DB 2 02/17/21 <END>  <START:QTY> 5.3mm <END> <START:STARTDATE> today <END>
 * Library: Apache Open NLP
 */
public class NERPatternLookingForIsinDescQtyStartDate implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;
	
	public NERPatternLookingForIsinDescQtyStartDate(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}
	
	@Override
	public void generate() {
		Arrays.stream(Dates.values()).forEach(date -> {
			Arrays.stream(Quantities.values()).forEach(qty -> {
				getFirstKInstrumentsAfterShuffling(instruments.values(),5).forEach(instrument -> {
					final StringBuilder builder = new StringBuilder();
					
					builder.append(String.format("<START:OTHER> ***Looking for <END> <START:ISIN> %s <END> <START:QUANTITY> %s <END> <START:STARTDATE> %s <END> ", instrument.getIsin(),
							qty.getDescription(),
							date));
					
					addToListIfNotEmpty(builder, this.trainingSetElements);
				});
			});
		});
	}

}
