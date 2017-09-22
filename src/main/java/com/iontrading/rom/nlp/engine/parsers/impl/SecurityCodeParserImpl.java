package com.iontrading.rom.nlp.engine.parsers.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.iontrading.rom.nlp.engine.impl.SecurityCode;
import com.iontrading.rom.nlp.engine.parsers.SecurityCodeParser;

public class SecurityCodeParserImpl implements SecurityCodeParser{
	private final Collection<String> securityCodes;
	
	public SecurityCodeParserImpl(Collection<SecurityCode> securityCodes){
		this.securityCodes = new HashSet<>();
		securityCodes.forEach(code -> {
			this.securityCodes.add(code.getCode());
		});
		
	}

	@Override
	public Iterable<SecurityCode> getCodes(final String line) {
		return parse(line);
	}

	private Iterable<SecurityCode> parse(final String line){
		// this has to be a list, and not a set,
		// since an instrument can occur multiple times in a line.
		
		Set<SecurityCode> listOfCodes = new HashSet<>();
		
		Arrays.stream(line.split(" ")).forEach(token -> {
			if(this.securityCodes.contains(token)){
				listOfCodes.add(new SecurityCode(token));
			}
		});
		
		
		return listOfCodes;
	}
}
