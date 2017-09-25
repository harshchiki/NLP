package nlp.ner.trainingsetgeneration.pattern;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.beust.jcommander.internal.Lists;

import nlp.ner.trainingsetgeneration.Instrument;
import nlp.ner.trainingsetgeneration.NERLibrary;

public interface IPattern {
	void generate(NERLibrary nerLibrary);

	default void addToListIfNotEmpty(final StringBuilder builder,
			final List<String> trainingSetElements){
		/*
		 * new line need not be added, since at the time of training set generation
		 * new lines are inserted after every list item.
		 */
		if(builder != null && builder.length() > 0 && trainingSetElements != null){
			trainingSetElements.add(builder.toString());
		}
	}

	/*
	 * Currently, we plan to shuffle the collection of all instruments and get first k, for each pattern. This is for wider distribution of instruments.
	 * 
	 * The down side is, Performance. If that is an impediment, we can for once have the instruments shuffled, and pass the first k to all the patterns. 
	 * The same set of instruments would then be used in all the patterns.
	 */
	default Collection<Instrument> getFirstKInstrumentsAfterShuffling(final Collection<Instrument> allInstruments, int k){
		
		List<Instrument> lstInstruments = Lists.newArrayList(allInstruments);
		Collections.shuffle(lstInstruments);		
		lstInstruments = lstInstruments.stream().limit(2).collect(Collectors.toList());
		return lstInstruments;
	}
	
	default int getRandom10() {
		// should return values {1,2,3,4,5,6,7,8,9,10}
		return ((int) Math.random())%10 + 1;
	}
	
	default String getNERPattern(final NERLibrary nerLibrary, String... values) {
		StringBuilder builder = new StringBuilder();
		
		switch(nerLibrary) {
		case APACHE_OPEN_NLP:
			StringBuilder formatPart1 = new StringBuilder();
			for(int i = 0;i<values.length/2;i++) {
				formatPart1.append("<START:%s> %s <END> "); // ends with an additional space
			}
			
			builder.append(String.format(formatPart1.toString(), values));
			break;
		case STANFORD_CORE_NLP:
			for(int i = 0;i<values.length;i++) {
				builder.append(values[i]);
				if(i%2 == 1) {
					builder.append("\n"); // new line for next annotation
				}else {
					builder.append("\t"); // tab space between value and type
				}
			}
			
			builder.append("\n");
			break;
		}
		return builder.toString();
	}
	
	default String getRandomDescription(NERLibrary library){
		final String[] apacheDescriptions = new String[]{
				"<START:DESC_PART> BUND <END> <START:DESC_PART> CPN <END> <START:DESC_PART> 07/23 <END>",

				"<START:DESC_PART> UKTI <END> <START:DESC_PART> 1 <END> <START:DESC_PART> 1/4 <END> <START:DESC_PART> 11/27 <END> ",

				"<START:DESC_PART> UK <END> <START:DESC_PART> WAR <END> <START:DESC_PART> LOAN <END> <START:DESC_PART> 3 <END> <START:DESC_PART> ½ <END> <START:DESC_PART> 29/12/4 <END>",

				"<START:DESC_PART> BUND <END> <START:DESC_PART> CPN <END> <START:DESC_PART> JUL23 <END>",

				"<START:DESC_PART> UKTI <END> <START:DESC_PART> 1 <END> <START:DESC_PART> 1/4 <END> <START:DESC_PART> 11/27<END>",

				"<START:DESC_PART> BNG <END> <START:DESC_PART> 5.375 <END> <START:DESC_PART> 06/21 <END>",

				"<START:DESC_PART> RESF <END> <START:DESC_PART> 5.25 <END> <START:DESC_PART> 12/28 <END>",

				"<START:DESC_PART> UKTI <END> <START:DESC_PART> 1 <END> <START:DESC_PART> 1/4 <END> <START:DESC_PART> 11/22/2027 <END>",
				
				"<START:DESC_PART> T <END> <START:DESC_PART> 6.0 <END> <START:DESC_PART> 02/15/26 <END>",

				"<START:DESC_PART> T <END> <START:DESC_PART> 3.875 <END> <START:DESC_PART> 08/15/40 <END> <START:DESC_PART> 1wk <END>",

				"<START:DESC_PART> DBR <END> <START:DESC_PART> 4.75 <END> <START:DESC_PART> 07/40 <END>"
		};
		
		final String[] stanfordDescriptions = new String[]{
				
		};
		
		switch(library){
		case APACHE_OPEN_NLP:
			return apacheDescriptions[((int) Math.random())%(apacheDescriptions.length)];
		case STANFORD_CORE_NLP:
			return stanfordDescriptions[((int) Math.random())%(stanfordDescriptions.length)];
		}
		
		return "";
		
	}
	
	default String getSecurityDescriptionToken() {
		return "secDesc_"+getRandom10();
	}
}
