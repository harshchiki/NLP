package nlp.ner.training;

import java.io.IOException;

import nlp.ner.models.NERModelConstants;
import nlp.ner.trainingsetgeneration.ITrainingSetGenerator;
import nlp.ner.trainingsetgeneration.NERTrainingSetGenerator;

public class NERTrainingMain {

	public static void main(String[]args){

		ITrainingSetGenerator trainingSetGenerator=new NERTrainingSetGenerator(NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINING_SET_FILE);
		INLPTrainer nerTrainer=new NERTrainer(NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINING_SET_FILE, NERModelConstants.CUSTOM_ENGLISH_TOKEN_NAME_FINDER_NER_TRAINED_BINARY);
		
		try{
			trainingSetGenerator.generate();
			nerTrainer.train();
		}
		catch(IOException e){
			// TODO: Failure mechanism to be devised
			e.printStackTrace();
		}
	}
}
