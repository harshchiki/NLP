package com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.beust.jcommander.internal.Lists;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.Instrument;

public interface IPattern {
	void generate();

	default void addToListIfNotEmpty(final StringBuilder builder,
			final List<String> list){
		/*
		 * new line need not be added, since at the time of training set generation
		 * new lines are inserted after every list item.
		 */
		if(builder != null && builder.length() > 0 && list != null){
			list.add(builder.toString());
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
}
