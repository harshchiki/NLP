package nlp.engine.parsers;

import nlp.engine.impl.SecurityCode;

public interface SecurityCodeParser {
	Iterable<SecurityCode> getCodes(String line);
}
