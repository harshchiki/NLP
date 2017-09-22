package com.iontrading.rom.nlp.engine.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.rom.nlp.engine.NLPEngine;


public class DummyNLPTest {
	public static void main(String[] args) throws IOException {
		NLPEngine nlpEngine = NLPBootstrap.getNLPEngine();
		
//		nlpEngine.process("DE0001143378 BUND CPN JUL34 A 8.8mil T1W EUR BID ");
//		nlpEngine.process("DE0001143378 8.8mil EUR BID ");
		nlpEngine.process("DE0001143378 EUR BID ");
		nlpEngine.process("DE0001143378 8.8mil BID ");
		nlpEngine.process("DE0001143378 8.8mil EUR");
		nlpEngine.process("DE0001143378 8.8mil EUR");
		
		
//		File messages = new File("Messages_truncated.txt");
//		
//		BufferedReader b;
//		try {
//			String readLine = "";
//			b = new BufferedReader(new FileReader(messages));
//			
//			while ((readLine = b.readLine()) != null) {
//				System.out.println("ReadLine -- > "+readLine);
//				if(readLine.length() > 0) {
//					Arrays.stream(readLine.split("\\n")).forEach(ln -> {
//						System.out.println("line passed -- > "+ln);
//						nlpEngine.process(ln);
//					});
//				}
//				System.out.println();
//				System.out.println();
//			}
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}


        System.out.println("Reading file using Buffered Reader");

	}
}
