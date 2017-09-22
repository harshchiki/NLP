package com.iontrading.rom.nlp.ner.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;

import com.iontrading.rom.nlp.ner.INamedEntityRecognizer;
import com.iontrading.rom.nlp.ner.NERResult;
import com.iontrading.rom.nlp.ner.NERUtils;
import com.iontrading.rom.nlp.ner.models.CustomModels;
import com.iontrading.rom.nlp.ner.models.StandardModels;


public class NamedEntityRecognizer implements INamedEntityRecognizer {
	// TODO: Ideally this should be injected, using ION 2.0 guidelines
	private final TokenNameFinderModel tokenNameFinderModel = CustomModels.TOKEN_NAME_FINDER_MODEL;
	private final Tokenizer tokenizer = StandardModels.TOKENIZER;
	private final NameFinderME nameFinder = new NameFinderME(this.tokenNameFinderModel);
//	private final ILogger logger;
	
	// TODO: This needs to be injected
//	public NamedEntityRecognizer(final ILogger logger) {
//		this.logger = logger;
//	}

	// TODO: Add logging
	@Override
	public Collection<NERResult<?>> performNER(final String sentence) {
		final Collection<NERResult<?>> nerResults = new LinkedList<>();
		String[] tokens = tokenizer.tokenize(sentence);
		Arrays.stream(this.nameFinder.find(tokens)).forEach(span -> nerResults.add(NERUtils.parseNERResultFromSpan(tokens, span)));
		return nerResults;
	}

}
