package nlp.engine.processors.impl;

import java.util.concurrent.atomic.AtomicInteger;

import nlp.engine.context.TextProcessingContext;
import nlp.engine.parsers.SecurityDescriptionParser;
import nlp.engine.processors.SecurityDescriptionProcessor;

public class SecurityDescriptionProcessorImpl implements SecurityDescriptionProcessor {

	private final SecurityDescriptionParser secDescriptionParser;
	public SecurityDescriptionProcessorImpl(final SecurityDescriptionParser secDescriptionParser){
		this.secDescriptionParser=secDescriptionParser;
	}
	
	@Override
	public void process(TextProcessingContext context) {
		
		TokenGenerator tokenGenerator = new TokenGenerator();
		Iterable<String> secDescs = secDescriptionParser.getSecurityDescription(context.getLineToProcess());
		
		// currently assume one occurence of the security code identified
		// need to check behaviour when same occurs multiple times
		secDescs.forEach(secDesc -> {
			final String token = tokenGenerator.generate();
			context.getSecurityDescTokens().put(token, secDesc);
			context.setLineToProcess(context.getLineToProcess().replace(secDesc, token));
		});
		
		// REPLACEMENT
		context.getSecurityDescTokens().forEach((securityDescription,replacerToken)->{
			String lineToProcess=context.getLineToProcess().replace(securityDescription, replacerToken);
			System.out.println("\n\nReplaced \n"+securityDescription+"\nwith\n"+replacerToken);
			context.setLineToProcess(lineToProcess);
		});
	}
	
	private class TokenGenerator{
		private AtomicInteger index = new AtomicInteger(0);
		
		String generate(){
			return "secDesc_" + (index.getAndIncrement());
		}
	}
	
}
