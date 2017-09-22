package com.iontrading.rom.nlp.ner.training;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.ml.maxent.quasinewton.QNTrainer;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class NERTrainer implements INLPTrainer {
	private final String trainingFilePath;
	private final String modelPath;
	private final TrainingParameters trainingParams;

	public NERTrainer(final String trainingFilePath, final String modelPath){
		this.trainingFilePath=trainingFilePath;
		this.modelPath=modelPath;
		this.trainingParams = new TrainingParameters();
		trainingParams.put(TrainingParameters.ITERATIONS_PARAM, 70);
		trainingParams.put(TrainingParameters.CUTOFF_PARAM, 1);
		trainingParams.put(TrainingParameters.ALGORITHM_PARAM, QNTrainer.MAXENT_QN_VALUE);
	}
	
	public NERTrainer(final String trainingFilePath,
			final String modelPath,
			final int iterationsParam,
			final int cutOffParam,
			final String algorithmName){
		this.trainingFilePath=trainingFilePath;
		this.modelPath=modelPath;
		
		this.trainingParams = new TrainingParameters();
		trainingParams.put(TrainingParameters.ITERATIONS_PARAM, iterationsParam);
		trainingParams.put(TrainingParameters.CUTOFF_PARAM, cutOffParam);
		trainingParams.put(TrainingParameters.ALGORITHM_PARAM, algorithmName);
	}
	
	/*
	 * TODO: Possibly swallow the IOException here + logging it.
	 * We could throw back an ApplicationException suggesting the training failed - and so a failure mechanism for that.
	 * 
	 * (non-Javadoc)
	 * @see com.iontrading.rom.nlp.training.ner.INLPTrainer#train()
	 */
	@Override
	public void train() throws IOException  {
		final InputStreamFactory in = new MarkableFileInputStreamFactory(new File(trainingFilePath));
		final ObjectStream<NameSample> sampleStream = new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
		final TokenNameFinderModel nameFinderModel = trainForEnglish(sampleStream);
		writeToFile(nameFinderModel);
	}

	private void writeToFile(final TokenNameFinderModel nameFinderModel)
			throws FileNotFoundException, IOException {
		final FileOutputStream outputStream = new FileOutputStream(new File(modelPath));
		nameFinderModel.serialize(outputStream);
		outputStream.close();
	}

	private TokenNameFinderModel trainForEnglish(
			final ObjectStream<NameSample> sampleStream) throws IOException,
			InvalidFormatException {
		final Map<String,Object> map=new HashMap<String, Object>();
		final TokenNameFinderModel nameFinderModel = NameFinderME.train("en", null, sampleStream,
				this.trainingParams, TokenNameFinderFactory.create(null, null, map, new BioCodec()));
		sampleStream.close();
		return nameFinderModel;
	}

}
