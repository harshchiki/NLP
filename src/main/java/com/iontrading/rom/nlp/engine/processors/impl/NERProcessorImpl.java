package com.iontrading.rom.nlp.engine.processors.impl;

import java.util.Collection;

import com.iontrading.rom.nlp.engine.context.TextProcessingContext;
import com.iontrading.rom.nlp.engine.processors.NERProcessor;
import com.iontrading.rom.nlp.ner.INamedEntityRecognizer;
import com.iontrading.rom.nlp.ner.NERResult;
import com.iontrading.rom.nlp.ner.impl.NamedEntityRecognizer;

public class NERProcessorImpl implements NERProcessor {

	private final INamedEntityRecognizer namedEntityRecognizer = new NamedEntityRecognizer();
	
	@Override
	public void process(final TextProcessingContext context) {
		Collection<NERResult<?>> nerResults = this.namedEntityRecognizer.performNER(context.getLineToProcess());
		context.setNERResults(nerResults);
	}

}
