package nlp.engine.context.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nlp.engine.context.TextProcessingContext;
import nlp.engine.impl.SecurityCode;
import nlp.ner.NERResult;

public class TextProcessingContextImpl implements TextProcessingContext {
	private String lineToProcess;
	private final Map<String, SecurityCode> securityCodeTokens = new HashMap<>();
	private final Map<String,String> securityDescTokens = new HashMap<>();
	private Collection<NERResult<?>> nerResults;

	public TextProcessingContextImpl(final String lineToProcess){
		this.lineToProcess=lineToProcess;
	}

	@Override
	public String getLineToProcess() {
		return lineToProcess;
	}

	@Override
	public Map<String, SecurityCode> getSecurityCodeTokens() {
		return securityCodeTokens;
	}

	@Override
	public Map<String, String> getSecurityDescTokens() {
		return securityDescTokens;
	}
	
	@Override
	public void  setLineToProcess(String lineToProcess) {
		this.lineToProcess=lineToProcess;
	}

	@Override
	public Collection<NERResult<?>> getNERResults() {
		return this.nerResults; 
	}

	@Override
	public void setNERResults(Collection<NERResult<?>> nerResults) {
		this.nerResults = nerResults;
	}
}
