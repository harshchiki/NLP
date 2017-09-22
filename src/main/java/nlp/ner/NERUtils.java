package nlp.ner;

import opennlp.tools.util.Span;

public class NERUtils {
	// Span from Apache Open NLP
	public static NERResult<String> parseNERResultFromSpan(final String[] tokens, final Span span){
		final int start = span.getStart();
		final int end = span.getEnd();
		final String type = span.getType();
		
		StringBuilder valueBuilder = new StringBuilder();
		
		//TODO: validation on tokens array bounds
		for(int i = start;i<end;i++){
			valueBuilder.append(tokens[i]);
		}
		
		// TODO: validation on result
		return new NERResult<String>(type, valueBuilder.toString());		
	}
}
