package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Dates;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Harsh.Chiki
 * Format/Pattern: "Please may I have your bids 29 Jun 09 Oct 2017"
 * Please may I have your bids <StartDate> <StopDate> 2017
 * Library: Apache Open NLP
 */
public class NERPatternPleaseMayIHaveVerbsDateStartDateStop implements IPattern {

	private final List<String> trainingSetElements;
	
	public NERPatternPleaseMayIHaveVerbsDateStartDateStop(final List<String> trainingSetElements){
		this.trainingSetElements = trainingSetElements;
	}
	
	@Override
	public void generate() {
		Arrays.stream(Verbs.values()).forEach(verb -> {
			Arrays.stream(Dates.values()).forEach(dateStart ->{
				Arrays.stream(Dates.values()).forEach(dateStop ->{
					final StringBuilder builder = new StringBuilder();
					
					builder.append(String.format("Please may I have your <START:VERB> %s <END> <START:STARTDATE> %s <END> <START:STARTDATE> %s <END>",
							verb,
							dateStart.getDescription(),
							dateStop.getDescription()));
					
					addToListIfNotEmpty(builder, trainingSetElements);
				});
			});
		});
	}

}
