package nlp.engine.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import nlp.engine.NLPEngine;
import nlp.engine.context.TextProcessingContext;
import nlp.engine.context.impl.TextProcessingContextImpl;
import nlp.engine.processors.NERProcessor;
import nlp.engine.processors.SecurityCodeProcessor;
import nlp.engine.processors.SecurityDescriptionProcessor;
import nlp.ner.NERResult;

public class NLPEngineImpl implements NLPEngine {

	private final SecurityCodeProcessor secCodeProcessor;
	private final SecurityDescriptionProcessor secDescriptionProcessor;
	private final NERProcessor nerProcessor;
	
	public NLPEngineImpl(final SecurityCodeProcessor secCodeProcessor, 
			final SecurityDescriptionProcessor secDescriptionProcessor,
			final NERProcessor nerProcessor) {
		this.secCodeProcessor = secCodeProcessor;
		this.secDescriptionProcessor = secDescriptionProcessor;
		this.nerProcessor = nerProcessor;
	}
	
	@Override
	public void process(final String lineToProcess) {
		// context creation
		TextProcessingContext textProcessingContext = new TextProcessingContextImpl(lineToProcess);
		
		this.secCodeProcessor.process(textProcessingContext);
		this.secDescriptionProcessor.process(textProcessingContext);
		this.nerProcessor.process(textProcessingContext);
		
		
//		logNERResultsOnConsole(lineToProcess, textProcessingContext);
		replaceTokensInNERResults(textProcessingContext);
		logNERResultsOnConsole(lineToProcess, textProcessingContext);
		
//		logNERResults(lineToProcess, textProcessingContext);
	}

	private void logNERResultsOnConsole(final String lineToProcess,
			TextProcessingContext textProcessingContext) {
		//ParsingResults
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("\n");
		builder.append("NER Parsing Output\n");
		builder.append("line ------>   "+lineToProcess+"\n");
		
    	Collection<NERResult<?>> nerResults = textProcessingContext.getNERResults();
		if(nerResults.size() > 0){
    		nerResults.stream().forEach(nerResult -> {
    			builder.append("nerToken -> " + nerResult+"\n");    			
    		});
    	}else{
    		builder.append("NO RESULTS FROM NER");
    	}
		
		
		// write to file start
		appendToFile(builder.toString());
		
		
		// write to file end
		
	}
	
	// should not be part of NLP engine
	private void replaceTokensInNERResults(TextProcessingContext context){		
		context.getNERResults().forEach(nerResult -> {
			String nerResValue = nerResult.getValue().get().toString();
			if(nerResValue.startsWith("secCode")){
				String replacementText = context.getSecurityCodeTokens().get(nerResValue).getCode();
				Optional<?> op = Optional.of(replacementText);
				nerResult.setValue(op);
			}
			
			if(nerResValue.startsWith("secDesc")){
				String replacementText = context.getSecurityDescTokens().get(nerResValue);
				Optional<?> op = Optional.of(replacementText);
				nerResult.setValue(op);
			}
		});
	}
	
	
	private void appendToFile(String t){
		try {
			File f = new File("ParsingResults.txt");
			BufferedWriter bw =new BufferedWriter(new FileWriter(f.getAbsoluteFile(), true));
			bw.write(t);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void logNERResults(final String lineToProcess,
			TextProcessingContext textProcessingContext) {
//		this.logger.action("NER Parsing Output").token("line", lineToProcess);
//    	Collection<NERResult<?>> nerResults = textProcessingContext.getNERResults();
//		if(nerResults.size() > 0){
//    		nerResults.stream().forEach(nerResult -> {
//    			this.logger
//    			.token("nerToken", nerResult);
//    		});
//    	}else{
//    		this.logger.token("nerToken","NO RESULTS FROM NER");
//    	}
//    	logger.end();
	}
}
