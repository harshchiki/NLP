package com.iontrading.rom.nlp.engine.impl;

import java.util.Collection;
import java.util.HashSet;

import com.iontrading.rom.nlp.engine.NLPEngine;
import com.iontrading.rom.nlp.engine.parsers.impl.SecurityCodeParserImpl;
import com.iontrading.rom.nlp.engine.parsers.impl.SecurityDescriptionParserImpl;
import com.iontrading.rom.nlp.engine.processors.NERProcessor;
import com.iontrading.rom.nlp.engine.processors.SecurityCodeProcessor;
import com.iontrading.rom.nlp.engine.processors.SecurityDescriptionProcessor;
import com.iontrading.rom.nlp.engine.processors.impl.NERProcessorImpl;
import com.iontrading.rom.nlp.engine.processors.impl.SecurityCodeProcessorImpl;
import com.iontrading.rom.nlp.engine.processors.impl.SecurityDescriptionProcessorImpl;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.InstrumentsDataUtil;

public class NLPBootstrap {
	private static NLPEngine NLPEngineInstance;

	public static NLPEngine getNLPEngine(){
		if(null == NLPEngineInstance) {
			Collection<SecurityCode> securityCodes=getSecurityCodesfromTSV();
			SecurityCodeProcessor secCodeProcessor=new SecurityCodeProcessorImpl(new SecurityCodeParserImpl(securityCodes));

			Collection<String> securityDescs=getSecurityDescfromTSV();
			SecurityDescriptionProcessor secDescriptionProcessor=new SecurityDescriptionProcessorImpl(new SecurityDescriptionParserImpl(securityDescs));

			NERProcessor nerProcessor=new NERProcessorImpl();
			
			NLPEngineInstance=new NLPEngineImpl(secCodeProcessor, secDescriptionProcessor, nerProcessor);
		}
		return NLPEngineInstance;
	}

	private static Collection<SecurityCode> getSecurityCodesfromTSV() {
		Collection<SecurityCode> secCodes= new HashSet<>();
		InstrumentsDataUtil.ALL_INSTRUMENTS.forEach((isin,instrumentData)->{
			secCodes.add(new SecurityCode(instrumentData.getIsin(),"ISIN"));	
		});
		return secCodes;

	}
	private static Collection<String> getSecurityDescfromTSV() {
		Collection<String> secDesc = new HashSet<>();
		InstrumentsDataUtil.ALL_INSTRUMENTS.forEach((isin,instrumentData)->{
			secDesc.add(instrumentData.getDescription());	
		});
		return secDesc;
	}
}
