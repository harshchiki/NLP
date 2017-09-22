package nlp.engine.parsers;


public interface SecurityDescriptionParser {
	Iterable<String> getSecurityDescription(String line);
}
