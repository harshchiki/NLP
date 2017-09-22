package com.iontrading.rom.nlp.engine.context;

import java.util.Collection;
import java.util.Map;

import com.iontrading.rom.nlp.engine.impl.SecurityCode;
import com.iontrading.rom.nlp.ner.NERResult;

public interface TextProcessingContext {
	
	String getLineToProcess();
	
	void setLineToProcess(String lineToProcess);
	
	void setNERResults(Collection<NERResult<?>> nerResults);

	Map<String, SecurityCode> getSecurityCodeTokens();

	Map<String, String> getSecurityDescTokens();
	
	Collection<NERResult<?>> getNERResults();
}
