package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.BaseInfoDescriptor;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.EnglishWishes;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.enums.Verbs;

/**
 * @author Harsh.Chiki
 * Format/Pattern: "Good morning, please may I have your bids for the below"
 * Library: Apache Open NLP
 */
public class NERPatternWithGeneralEnglish1 implements IPattern {

	private final List<String> trainingSetElements;
	private final Map<String, Instrument> instruments;

	public NERPatternWithGeneralEnglish1(final List<String> trainingSetElements,
			final Map<String, Instrument> instruments){
		this.trainingSetElements = trainingSetElements;
		this.instruments = instruments;
	}

	@Override
	public void generate() {
		Arrays.stream(BaseInfoDescriptor.values()).forEach(baseInfo -> {
			Arrays.stream(EnglishWishes.values()).forEach(wish -> {
				Arrays.stream(Verbs.values()).forEach(verb -> {
					final StringBuilder builder = new StringBuilder();
					
					// commas after a wish is handled in the enum value
					builder.append(String.format("<START:WISH> %s <END> please may I have your <START:VERB> %s <END> for the <START:BASE_INFO> %s <END>",
							wish.getWish(),
							verb,
							baseInfo));
					
					addToListIfNotEmpty(builder, trainingSetElements);
				});
			});
		});
	}

}
