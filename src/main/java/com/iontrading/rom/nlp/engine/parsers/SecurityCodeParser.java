package com.iontrading.rom.nlp.engine.parsers;

import com.iontrading.rom.nlp.engine.impl.SecurityCode;

public interface SecurityCodeParser {
	Iterable<SecurityCode> getCodes(String line);
}
