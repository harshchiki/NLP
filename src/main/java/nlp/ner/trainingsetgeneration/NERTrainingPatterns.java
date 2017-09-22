package nlp.ner.trainingsetgeneration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nlp.ner.trainingsetgeneration.pattern.IPattern;
import nlp.ner.trainingsetgeneration.pattern.NERMultiLinePatternPleaseVerbDurIsinQty;
import nlp.ner.trainingsetgeneration.pattern.NERMultiLinePatternPleaseVerbIsinQty;
import nlp.ner.trainingsetgeneration.pattern.NERPatternCurrPleaseVerb;
import nlp.ner.trainingsetgeneration.pattern.NERPatternCurrRateDescIsinDate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternDescIsinCurr;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinCurrDesc;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinCurrDescQty;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDesQty;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDesc;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDescCurrRateStartDateStopDate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDescStartDateCurrency;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinDescStartDateDateStopRate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternIsinQtyDesc;
import nlp.ner.trainingsetgeneration.pattern.NERPatternLookingForIsinDescQtyStartDate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternOtherRateVerbBaseInfoDateStop;
import nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseDescVerbQty;
import nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseMayIHaveVerbCurr;
import nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseMayIHaveVerbsDateStartDateStop;
import nlp.ner.trainingsetgeneration.pattern.NERPatternPleaseVerbQtyDesc;
import nlp.ner.trainingsetgeneration.pattern.NERPatternRateVerbBaseInfoDateStop;
import nlp.ner.trainingsetgeneration.pattern.NERPatternVerbQtyDescIsinStartDate;
import nlp.ner.trainingsetgeneration.pattern.NERPatternWithGeneralEnglish1;
import nlp.ner.trainingsetgeneration.pattern.NERPatternWithGeneralEnglish2;

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
