package nlp.ner.trainingsetgeneration;

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

import nlp.ner.trainingsetgeneration.pattern.IPattern;

public class NERTrainingSetGenerator implements ITrainingSetGenerator{
	private final String trainingFilePath;
	private final NERLibrary library;

	public NERTrainingSetGenerator(final String trainingFilePath) {
		this.trainingFilePath=trainingFilePath;
		this.library = NERLibrary.APACHE_OPEN_NLP;
	}
	public NERTrainingSetGenerator(final String trainingFilePath,
			final NERLibrary library) {
		this.trainingFilePath=trainingFilePath;
		this.library = library;
	}

	@Override
	public void generate() {
		final List<String> trainingSet = new LinkedList<String>();
		final List<IPattern> patterns=library == NERLibrary.APACHE_OPEN_NLP ? NERTrainingPatterns.getApacheOpenNLPPatterns(trainingSet, InstrumentsDataUtil.ALL_INSTRUMENTS)
				: NERTrainingPatterns.getStanfordNERPatterns(trainingSet, InstrumentsDataUtil.ALL_INSTRUMENTS);
	
		// Apache Open NLP
		patterns.stream().forEach(pattern -> pattern.generate(NERLibrary.APACHE_OPEN_NLP));
		
//		// Stanford Open NLP
//		patterns.stream().forEach(pattern -> pattern.generate(NERLibrary.STANFORD_CORE_NLP));
		
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
		trainingSet.stream().limit(1000000).forEach(statement->trainingSetWriter.println(statement));
		trainingSetWriter.close();
	}
}
