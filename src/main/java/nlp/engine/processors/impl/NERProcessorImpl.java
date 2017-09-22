package nlp.engine.processors.impl;

import java.util.Collection;

import nlp.engine.context.TextProcessingContext;
import nlp.engine.processors.NERProcessor;
import nlp.ner.INamedEntityRecognizer;
import nlp.ner.NERResult;
import nlp.ner.impl.NamedEntityRecognizer;

public class NERProcessorImpl implements NERProcessor {

	private final INamedEntityRecognizer namedEntityRecognizer = new NamedEntityRecognizer();
	
	@Override
	public void process(final TextProcessingContext context) {
		Collection<NERResult<?>> nerResults = this.namedEntityRecognizer.performNER(context.getLineToProcess());
		context.setNERResults(nerResults);
	}

}
