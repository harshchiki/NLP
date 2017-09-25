package nlp.ner.trainingsetgeneration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.pattern.IPattern;
import nlp.ner.trainingsetgeneration.pattern.NERPatternCurrPleaseVerb;
import nlp.ner.trainingsetgeneration.pattern.NERPatternCurrRateDescIsinDate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternCurrencyPleaseVerb;
import nlp.ner.trainingsetgeneration.pattern.NERPatternLookingForIsinDescQtyStartDate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternOtherRateVerbBaseInfoDateStop;
import nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseMayIHaveVerbCurr;
import nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseVerbQtyDesc;
import nlp.ner.trainingsetgeneration.pattern.more.NERCurrIsinQuantity;
import nlp.ner.trainingsetgeneration.pattern.more.NERDescIsinQty;
import nlp.ner.trainingsetgeneration.pattern.more.NERIsinQuantity;
import nlp.ner.trainingsetgeneration.pattern.more.NERIsinQuantityVerbDescStartDateEndDate;
import nlp.ner.trainingsetgeneration.pattern.more.NERQtyDescTenor;
import nlp.ner.trainingsetgeneration.pattern.more.NERTenorIsinQty;
import nlp.ner.trainingsetgeneration.pattern.more.NERVerbIsinQty;

public class NERTrainingPatterns {
	public static List<IPattern>  getApacheOpenNLPPatterns(List<String> trainingSetElements, Map<String,Instrument> instruments){
		List<IPattern> patterns = new LinkedList<>();
		
		patterns.add(new NERCurrIsinQuantity(trainingSetElements, instruments));
		
		patterns.add(new NERDescIsinQty(trainingSetElements, instruments));
		
		patterns.add(new NERIsinQuantity(trainingSetElements, instruments));
		
		patterns.add(new NERIsinQuantityVerbDescStartDateEndDate(trainingSetElements, instruments));
		
		patterns.add(new NERQtyDescTenor(trainingSetElements, instruments));
		
		patterns.add(new NERTenorIsinQty(trainingSetElements, instruments));
		
		patterns.add(new NERVerbIsinQty(trainingSetElements, instruments));
		
		// old patterns
		patterns.add(new NERPatternCurrPleaseVerb(trainingSetElements, instruments));
		
		patterns.add(new NERPatternCurrRateDescIsinDate(trainingSetElements, instruments));
		
		patterns.add(new NERPatternLookingForIsinDescQtyStartDate(trainingSetElements, instruments));
		
		patterns.add(new NERPatternOtherRateVerbBaseInfoDateStop(trainingSetElements, instruments));
		
		patterns.add(new NERPatternPleaseMayIHaveVerbCurr(trainingSetElements, instruments));
		
		patterns.add(new NERPatternPleaseVerbQtyDesc(trainingSetElements, instruments));
		
		// new patterns - singular
		
//		patterns.add(new NERDescIsinQty(trainingSetElements, instruments));
		
		return patterns;
	}
	
//	public static List<IPattern>  getApacheOpenNLPPatterns(List<String> trainingSet,Map<String,Instrument> instruments){
//		List<IPattern> nerPatterns = new LinkedList<IPattern>();
//		
//		nerPatterns.add(new NERPatternCurrPleaseVerb(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternCurrRateDescIsinDate(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternDescIsinCurr(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinCurrDesc(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinCurrDescQty(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinDescCurrRateStartDateStopDate(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinDesc(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinDescStartDateCurrency(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinDescStartDateDateStopRate(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinDesQty(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternIsinQtyDesc(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternLookingForIsinDescQtyStartDate(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternOtherRateVerbBaseInfoDateStop(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternPleaseMayIHaveVerbCurr(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternPleaseMayIHaveVerbsDateStartDateStop(trainingSet));
//		
//		nerPatterns.add(new NERPatternRateVerbBaseInfoDateStop(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternVerbQtyDescIsinStartDate(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternWithGeneralEnglish1(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternWithGeneralEnglish2(trainingSet, instruments));
//
//		nerPatterns.add(new NERPatternPleaseDescVerbQty(trainingSet, instruments));
//		
//		nerPatterns.add(new NERPatternPleaseVerbQtyDesc(trainingSet, instruments));
//
//		nerPatterns.add(new NERMultiLinePatternPleaseVerbDurIsinQty(trainingSet, instruments));
//		
//		nerPatterns.add(new NERMultiLinePatternPleaseVerbIsinQty(trainingSet, instruments));
//		
//		return nerPatterns;
//	}

	public static List<IPattern> getStanfordNERPatterns(List<String> trainingSetElements, Map<String, Instrument> instruments){
		List<IPattern> nerPatterns = new LinkedList<>();
		
		nerPatterns.add(new NERCurrIsinQuantity(trainingSetElements, instruments));
		
		nerPatterns.add(new NERDescIsinQty(trainingSetElements, instruments));
		
		nerPatterns.add(new NERIsinQuantity(trainingSetElements, instruments));
		
		nerPatterns.add(new NERIsinQuantityVerbDescStartDateEndDate(trainingSetElements, instruments));
		
		nerPatterns.add(new NERQtyDescTenor(trainingSetElements, instruments));

		nerPatterns.add(new NERTenorIsinQty(trainingSetElements, instruments));
		
		nerPatterns.add(new NERVerbIsinQty(trainingSetElements, instruments));
		
		return nerPatterns;
	}
}
