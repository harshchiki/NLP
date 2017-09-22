package nlp.engine.processors.impl;

import nlp.engine.context.TextProcessingContext;
import nlp.engine.impl.SecurityCode;
import nlp.engine.parsers.SecurityCodeParser;
import nlp.engine.processors.SecurityCodeProcessor;

// to constructed one time
public class SecurityCodeProcessorImpl implements SecurityCodeProcessor {

//	private final TextProcessingContext context;
	private final SecurityCodeParser secCodeParser;
	
	
	public SecurityCodeProcessorImpl(final SecurityCodeParser secCodeParser){
		this.secCodeParser = secCodeParser;
	}
	
	@Override
	public void process(final TextProcessingContext context) {
		
		TokenGenerator tokenGenerator = new TokenGenerator();
		Iterable<SecurityCode> secCodes = secCodeParser.getCodes(context.getLineToProcess());
		
		// currently assume one occurence of the security code identified
		// need to check brahviour when same occurs multiple times
		secCodes.forEach(secCode -> {
			final String token = tokenGenerator.generate();
			context.getSecurityCodeTokens().put(token, secCode);
			context.setLineToProcess(context.getLineToProcess().replace(secCode.getCode(), token));
		});
		
	}
	
	private class TokenGenerator{
		private int index = 0;
		
		String generate(){
			return "secCode_" + (index++);
		}
	}
}
