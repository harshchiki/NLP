package nlp.ner.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

//TODO: Finish ups pending
public class StandardModels {
	public static TokenNameFinderModel TOKEN_NAME_FINDER_MODEL;
	public static SentenceModel SENTENCE_MODEL;
	public static ParserModel PARSER_MODEL;
	public static ChunkerME CHUNKER_ME;
	public static ChunkerModel CHUNKER_MODEL;
	public static POSModel POS_MODEL;
	public static POSTaggerME POS_TAGGER_ME;
	public static Tokenizer TOKENIZER;
	public static NameFinderME NAME_FINDER_ME;
	
	static{
		TOKEN_NAME_FINDER_MODEL = getTokenNameFinderModel();
		SENTENCE_MODEL = getSentenceModel();
		PARSER_MODEL = getParserModel();
		CHUNKER_ME = getChunkerME();
		CHUNKER_MODEL = getChunkerModel();
		POS_MODEL = getPOSModel();
		POS_TAGGER_ME = getPOSTaggerME();
		TOKENIZER = getTokenizer();
		NAME_FINDER_ME = getNameFinderModel();
	}

	private static TokenNameFinderModel getTokenNameFinderModel(){
		TokenNameFinderModel tokenNameFinderModel = null;
		try {
			final InputStream is = new FileInputStream(NERModelConstants.STANDARD_ENGLISH_NER_TOKENIZER_TRAINED_BINARY_APACHE);
			try {
				tokenNameFinderModel = new TokenNameFinderModel(is);
			} catch (IOException e) {
				// TODO: Handle
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					//TODO: Handle
				}
			}
		} catch (FileNotFoundException e) {
			// TODO: Log this exception - Hande failure
		}
		return tokenNameFinderModel;
	}
	
	private static SentenceModel getSentenceModel(){
		SentenceModel sentenceModel = null;
		InputStream is;
		try {
			is = new FileInputStream(NERModelConstants.STANDARD_ENGLISH_SENTENCE_TRAINED_BINARY_APACHE);
			try {
				sentenceModel = new SentenceModel(is);
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sentenceModel;
	}
	
	private static ParserModel getParserModel(){
		ParserModel parserModel = null;
		try {
			InputStream is = new FileInputStream(NERModelConstants.STANDARD_ENGLISH_PARSER_TRAINED_BINARY_APACHE);
			try {
				parserModel = new ParserModel(is);				
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parserModel;
	}
	
	private static ChunkerME getChunkerME(){
		return new ChunkerME(getChunkerModel());
	}
	
	private static ChunkerModel getChunkerModel(){
		ChunkerModel chunkerModel = null;
		try {
			InputStream is = new FileInputStream(NERModelConstants.STANDARD_ENGLISH_CHUNKER_TRAINED_BINARY_APACHE);
			try {
				chunkerModel = new ChunkerModel(is);
				is.close(); // should be in finally
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chunkerModel;
	}
	
	private static POSModel getPOSModel(){
		POSModel posModel = null;
		posModel = new POSModelLoader().load(new File(NERModelConstants.STANDARD_ENGLISH_POS_TRAINED_BINARY_APACHE));
		return posModel;
	}
	
	private static POSTaggerME getPOSTaggerME(){
		return new POSTaggerME(getPOSModel());
	}
	
	private static Tokenizer getTokenizer(){
		Tokenizer tokenizer = null;
		try {
			InputStream is = new FileInputStream(NERModelConstants.STANDARD_ENGLISH_TOKENIZER_TRAINED_BINARY_APACHE);
			try {
				TokenizerModel model = new TokenizerModel(is);
				tokenizer = new TokenizerME(model);
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokenizer;
	}

	private static NameFinderME getNameFinderModel(){
		NameFinderME nameFinder = null;
		TokenNameFinderModel tokenNameFinderModel = null;
		InputStream is;
		try {
//			is = new FileInputStream("en-ner-person.bin");
			is = new FileInputStream(NERModelConstants.STANDARD_ENGLISH_NAME_FINDER_NER_TRAINED_BINARY_APACHE);
			try {
				tokenNameFinderModel = new TokenNameFinderModel(is);
				nameFinder = new NameFinderME(tokenNameFinderModel);
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nameFinder;
	}
}