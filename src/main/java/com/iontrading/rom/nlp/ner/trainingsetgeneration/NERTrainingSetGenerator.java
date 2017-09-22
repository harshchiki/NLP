package com.iontrading.rom.nlp.ner.trainingsetgeneration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.iontrading.rom.nlp.ner.trainingsetgeneration.pattern.IPattern;

public class NERTrainingSetGenerator implements ITrainingSetGenerator{
	private final String trainingFilePath;

	public NERTrainingSetGenerator(final String trainingFilePath) {
		this.trainingFilePath=trainingFilePath;
	}

	@Override
	public void generate() {
		final List<String> trainingSet = new LinkedList<String>();
		final List<IPattern> patterns=NERTrainingPatterns.get(trainingSet, InstrumentsDataUtil.ALL_INSTRUMENTS);
	
		patterns.stream().forEach(pattern -> pattern.generate());
		
		try{
			writeTrainingSetToFile(trainingSet);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	private void writeTrainingSetToFile(List<String> trainingSet) throws FileNotFoundException {
		trainingSet = trainingSet.stream().collect(Collectors.toList());
		final PrintWriter trainingSetWriter = new PrintWriter(new File(trainingFilePath));
		trainingSet.stream().forEach(statement->trainingSetWriter.println(statement));
		trainingSetWriter.close();
	}
}
