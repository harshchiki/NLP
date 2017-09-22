package nlp.engine.impl;

import java.util.Collection;
import java.util.HashSet;

import nlp.engine.NLPEngine;
import nlp.engine.parsers.impl.SecurityCodeParserImpl;
import nlp.engine.parsers.impl.SecurityDescriptionParserImpl;
import nlp.engine.processors.NERProcessor;
import nlp.engine.processors.SecurityCodeProcessor;
import nlp.engine.processors.SecurityDescriptionProcessor;
import nlp.engine.processors.impl.NERProcessorImpl;
import nlp.engine.processors.impl.SecurityCodeProcessorImpl;
import nlp.engine.processors.impl.SecurityDescriptionProcessorImpl;
import nlp.ner.trainingsetgeneration.InstrumentsDataUtil;

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
