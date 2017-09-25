package nlp.engine.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import nlp.engine.NLPEngine;
import nlp.engine.parsers.impl.SecurityDescriptionParserImpl;
import nlp.ner.trainingsetgeneration.InstrumentsDataUtil;


public class DummyNLPTest {
	public static void main(String[] args) throws IOException {
		runNLP();
	}


	private static void runFunzzyMatchForDesc() throws IOException{
		File messages = new File("Messages_truncated.txt");
		
		List<String> descriptionsInRefData = new LinkedList<>();
		InstrumentsDataUtil.ALL_INSTRUMENTS.values().forEach(inst -> descriptionsInRefData.add(inst.getDescription()));
		
		
		SecurityDescriptionParserImpl secDescparser = new SecurityDescriptionParserImpl(
				InstrumentsDataUtil.ALL_INSTRUMENTS.values().stream()
				.map(p -> p.getDescription())
				.collect(Collectors.toList()));

		BufferedReader b;
		try {
			String readLine = "";
			b = new BufferedReader(new FileReader(messages));

			while ((readLine = b.readLine()) != null) {
				System.out.println("ReadLine -- > "+readLine);
				if(readLine.length() > 0 && !readLine.startsWith("#")) {
					Arrays.stream(readLine.split("\\n")).forEach(ln -> {
						System.out.println("line passed -- > "+ln);
					});
				}
				System.out.println();
				System.out.println();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	private static void runNLP() throws IOException {
		NLPEngine nlpEngine = NLPBootstrap.getNLPEngine();


		// desc isin qty
		// XS0193406609	PARGN 7X B1B	EUR
		//		nlpEngine.process("PARGN 7X B1B	EUR XS0193406609 10mm");
		nlpEngine.process("DE0001143378 BUND CPN JUL34 A 8.8mil T1W EUR BID");
		//		nlpEngine.process("DE0001143378 8.8mil EUR BID ");
		//		nlpEngine.process("DE0001143378 EUR BID ");
		//		nlpEngine.process("DE0001143378 8.8mil BID ");
		//		nlpEngine.process("DE0001143378 8.8mil EUR");
		//		nlpEngine.process("DE0001143378 8.8mil EUR");


		File messages = new File("Messages_truncated.txt");

		BufferedReader b;
		try {
			String readLine = "";
			b = new BufferedReader(new FileReader(messages));

			while ((readLine = b.readLine()) != null) {
				System.out.println("ReadLine -- > "+readLine);
				if(readLine.length() > 0 && !readLine.startsWith("#")) {
					Arrays.stream(readLine.split("\\n")).forEach(ln -> {
						System.out.println("line passed -- > "+ln);
						nlpEngine.process(ln);
					});
				}
				System.out.println();
				System.out.println();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
