package nlp.ner.training;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import nlp.ner.models.NERModelConstants;
import nlp.ner.training.apache.ApacheNERTrainer;
import nlp.ner.trainingsetgeneration.ITrainingSetGenerator;
import nlp.ner.trainingsetgeneration.NERLibrary;
import nlp.ner.trainingsetgeneration.NERTrainingSetGenerator;
import nlp.stanford.ner.properties.StanfordNERProperties;

public class NERTrainingMain {
	Logger log = Logger.getLogger(NERTrainingMain.class.getName());

	public static void main(String[]args){
		
		
		
		
		
//		BasicConfigurator.configure();
//		new NERTrainingMain().log.info("logged");
		trainWithApache();
//		
//		trainWithStanford();
		
//		Properties stanfordNERProperties = new StanfordNERProperties().getProperties();
//		CRFClassifier crfClassifier = new CRFClassifier<>(stanfordNERProperties);
//		crfClassifier.classify("US92922F2Q02 40mm").forEach(t -> {
//			System.out.println(t.toString());
//		});
		
	}

	// Stanford Core NLP
	private static void trainWithStanford() {
		ITrainingSetGenerator trainingSetGeneratorStanford=new NERTrainingSetGenerator(
				NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINING_SET_FILE_STANFORD_OPEN_NLP,
				NERLibrary.STANFORD_CORE_NLP);
		
		INLPTrainer stanfordNERTrainer=new ApacheNERTrainer(NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINING_SET_FILE_STANFORD_OPEN_NLP, 
				NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINED_BINARY_APACHE);
		
		trainingSetGeneratorStanford.generate(); // dont need to train
//		generateAndTrain(trainingSetGeneratorStanford, stanfordNERTrainer);
	}


	// Apache Open NLP
	private static void trainWithApache() {
		ITrainingSetGenerator trainingSetGeneratorApache=new NERTrainingSetGenerator(
				NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINING_SET_FILE_APACHE);
		
		INLPTrainer apacheNERTrainer=new ApacheNERTrainer(NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINING_SET_FILE_APACHE, 
				NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINED_BINARY_APACHE);
		
		generateAndTrain(trainingSetGeneratorApache, apacheNERTrainer);
	}
	
	
	private static void generateAndTrain(ITrainingSetGenerator trainingSetGenerator,
			INLPTrainer nerTrainer) {
		try {
			trainingSetGenerator.generate();
			nerTrainer.train();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
