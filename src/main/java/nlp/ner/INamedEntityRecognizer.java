package nlp.ner;

import java.util.Collection;

public interface INamedEntityRecognizer {
	Collection<NERResult<?>> performNER(String tokens);
}
