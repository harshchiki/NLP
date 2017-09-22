package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Quantities;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Gaurav.Soni
 * Pattern: Please Bid 
 * 			US912834EV64 20mm
 * 			US92922F2Q02 15mm
 * 			US92927BAB80 40mm
 * Library: Apache Open NLP
 *
 */
public class NERMultiLinePatternPleaseVerbIsinQty implements IPattern {

	
	
	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERMultiLinePatternPleaseVerbIsinQty(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {


		List<List<Instrument>> instrumentPartitionList=Lists.partition(new LinkedList<Instrument>(instruments.values()), 3);

		instrumentPartitionList.stream().forEach(instrumentList->{
			Arrays.stream(Verbs.values()).forEach(verb -> {
				Arrays.stream(Quantities.values()).forEach(quantity -> {
					final StringBuilder builder = new StringBuilder();
					builder.append(String.format("Please <START:VERB> %s <END> ",verb));
					instrumentList.stream().forEach(instrument->{
						
						builder.append(String.format("\n <START:ISIN> %s <END> <START:QTY> %s <END>",
								instrument.getIsin(),
								quantity.getDescription()
								));
				
						addToListIfNotEmpty(builder, this.trainingSetElements);
					});

				});		
			});
		});
	}

	
}

















