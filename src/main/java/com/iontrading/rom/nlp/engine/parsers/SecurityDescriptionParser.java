package com.iontrading.rom.nlp.engine.parsers;


public interface SecurityDescriptionParser {
	Iterable<String> getSecurityDescription(String line);
}
