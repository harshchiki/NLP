package com.iontrading.rom.nlp.engine.processors;

import com.iontrading.rom.nlp.engine.context.TextProcessingContext;

public interface NERProcessor {
	void process(TextProcessingContext context);
}
