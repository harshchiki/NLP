package com.iontrading.rom.nlp.ner.trainingsetgeneration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.IPattern;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERMultiLinePatternPleaseVerbDurIsinQty;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERMultiLinePatternPleaseVerbIsinQty;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternCurrPleaseVerb;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternCurrRateDescIsinDate;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternDescIsinCurr;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinCurrDesc;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinCurrDescQty;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDesQty;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDesc;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDescCurrRateStartDateStopDate;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDescStartDateCurrency;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDescStartDateDateStopRate;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternIsinQtyDesc;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternLookingForIsinDescQtyStartDate;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternOtherRateVerbBaseInfoDateStop;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseDescVerbQty;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseMayIHaveVerbCurr;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseMayIHaveVerbsDateStartDateStop;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseVerbQtyDesc;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternRateVerbBaseInfoDateStop;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternVerbQtyDescIsinStartDate;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternWithGeneralEnglish1;
import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.NERPatternWithGeneralEnglish2;

public class NERTrainingPatterns {
	public static List<IPattern>  get(List<String> trainingSet,Map<String,Instrument> instruments){
		List<IPattern> nerPatterns = new LinkedList<IPattern>();
		
		nerPatterns.add(new NERPatternCurrPleaseVerb(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternCurrRateDescIsinDate(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternDescIsinCurr(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinCurrDesc(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinCurrDescQty(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinDescCurrRateStartDateStopDate(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinDesc(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinDescStartDateCurrency(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinDescStartDateDateStopRate(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinDesQty(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternIsinQtyDesc(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternLookingForIsinDescQtyStartDate(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternOtherRateVerbBaseInfoDateStop(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternPleaseMayIHaveVerbCurr(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternPleaseMayIHaveVerbsDateStartDateStop(trainingSet));
		
		nerPatterns.add(new NERPatternRateVerbBaseInfoDateStop(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternVerbQtyDescIsinStartDate(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternWithGeneralEnglish1(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternWithGeneralEnglish2(trainingSet, instruments));

		nerPatterns.add(new NERPatternPleaseDescVerbQty(trainingSet, instruments));
		
		nerPatterns.add(new NERPatternPleaseVerbQtyDesc(trainingSet, instruments));

		nerPatterns.add(new NERMultiLinePatternPleaseVerbDurIsinQty(trainingSet, instruments));
		
		nerPatterns.add(new NERMultiLinePatternPleaseVerbIsinQty(trainingSet, instruments));
		
		return nerPatterns;
	}
}
