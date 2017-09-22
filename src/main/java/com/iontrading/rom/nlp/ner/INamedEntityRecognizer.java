package com.iontrading.rom.nlp.ner;

import java.util.Collection;

public interface INamedEntityRecognizer {
	Collection<NERResult<?>> performNER(String tokens);
}
