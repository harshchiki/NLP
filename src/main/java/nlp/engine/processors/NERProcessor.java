package nlp.engine.processors;

import nlp.engine.context.TextProcessingContext;

public interface NERProcessor {
	void process(TextProcessingContext context);
}
