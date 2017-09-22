package com.iontrading.rom.nlp.engine.parsers.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.iontrading.rom.nlp.engine.parsers.SecurityDescriptionParser;



public class SecurityDescriptionParserImpl implements SecurityDescriptionParser {
	private final Collection<String> descriptions;
	
	public SecurityDescriptionParserImpl(Collection<String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public Iterable<String> getSecurityDescription(String line) {
		
		Map<String, List<Closeness>> mapDescCloseness = new HashMap<>(); // implement this
		
		descriptions.forEach(desc -> {
			String preProcessedDescription = preProcessDescription(desc);
			LCSResult lcsResult = getLengthOfLongestCommonSubsequence(line, preProcessedDescription);
			System.out.println("line -> "+line);
			// considering if there is a 70% match
			if(lcsResult.length > 0 
					&& getMatchPercentage(lcsResult.length, preProcessedDescription.length()) > 95.0
					&& areSpacesThereOnEitherSides(line, lcsResult)){
				System.out.println("startindex - "+lcsResult.startIndex+" endIndex = "+lcsResult.endIndex);
				Closeness closeness = new Closeness(lcsResult.startIndex, lcsResult.endIndex, preProcessedDescription.length(), lcsResult.length);
				putInMap(mapDescCloseness, desc, closeness);
			}
		});
		
		
		Set<String> descSet = new HashSet<>();
		
		mapDescCloseness.entrySet().forEach(entry -> {
			String desc = entry.getKey();
			List<Closeness> lstCloseness = entry.getValue();
			
			Collections.sort(lstCloseness);
			
			Closeness bestCloseness = lstCloseness.get(0);
			descSet.add(line.substring(bestCloseness.startIndex, bestCloseness.endIndex));
		});

		return descSet;
	}

	private void putInMap(Map<String, List<Closeness>> mapDescCloseness,
			String desc, Closeness closeness) {
		if(mapDescCloseness.containsKey(desc)){
			mapDescCloseness.get(desc).add(closeness);
		}else{
			List<Closeness> ls = new LinkedList<>();
			ls.add(closeness);
			mapDescCloseness.put(desc, ls);
		}
	}
	
	private boolean areSpacesThereOnEitherSides(String line, LCSResult lcsResult) {
		if(lcsResult.startIndex != 0){
			if(line.charAt(lcsResult.startIndex-1) != ' '){
				return false;
			}						
		}
		
		if(lcsResult.endIndex != (line.length()-1)){
			if(line.charAt(lcsResult.endIndex+1) != ' '){
				return false;
			}
		}
		
		return true;
	}
	
	private String preProcessDescription(String description){
		char[] specialChars = new char[] {'-','_','/','%',' '};
		
		for(char c : specialChars){
			description = description.replaceAll(c+"", "");
		}
		return description;
	}
	
	private double getMatchPercentage(int matchStringLength, int descLength){
		if(descLength == 0) {
			// handling divide by zero exception
			return 0.0;
		}
		
		double matchPercent = ((double)matchStringLength/(double)descLength)*100;
		System.out.println("match percent = "+matchPercent);
		return matchPercent;
	}
	
	private LCSResult getLengthOfLongestCommonSubsequence(String line, String desc) {
		char[] lineArray = line.toCharArray();
		char[] descriptionArray = desc.toCharArray();
		int lineLength = lineArray.length, descLength = descriptionArray.length; 
		
		int startIndex = -1, endIndex = -1;
		
		int[][] table = new int[descLength+1][lineLength+1];
		
		for(int i = 0;i<lineLength;i++) {
			table[0][i] = 0;
		}
		
		for(int i = 0;i<descLength;i++) {
			table[i][0] = 0;
		}
		
		for(int i = 0;i<descLength;i++) { 
			for(int j = 0;j<lineLength;j++) { 
				if(lineArray[j] == descriptionArray[i]) {
					
					if(-1 == startIndex){
						startIndex = j;
					}else{
						endIndex = max(endIndex, j);
					}
					
					
					table[i+1][j+1] = 1+table[i][j];
				}else {
					table[i+1][j+1] = max(table[i+1][j], table[i][j+1]);
				}
			}
		}
		
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		System.out.println("printing indexes");
//		System.out.println("startIndex = "+startIndex+" endindex = "+endIndex);
//		System.out.println("printing indexes");
//		System.out.println();
//		System.out.println();
//		
		
		if(table[descLength][lineLength] > 0){			
			return new LCSResult(table[descLength][lineLength], startIndex, endIndex);
		}else{
			return new LCSResult(0);
		}
	}
	
	private int max(int a, int b){
		return a>b ? a:b;
	}
	
	private class LCSResult{
		int length, startIndex, endIndex;

		public LCSResult(int length){
			this.length = length;
			this.startIndex = -1;
			this.endIndex = -1;
		}
		
		public LCSResult(int length, int startIndex, int endIndex) {
			this.length = length;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		/*
		 * Cases:
		 * 1.) No LCS 
		 * 
		 * 2.) LCS.length = 1
		 * 
		 * 3.) LCS.length > 1 
		 */
	}
	
	
	private class Closeness implements Comparable<Closeness>{

		int startIndex, endIndex; // in line
		int preProcessDescLength;
		int lcsLength;
		
		Closeness(int startLength, int endIndex, int preProcessDescLength, int lcsLength){
			this.startIndex = startLength;
			this.endIndex = endIndex;
			this.preProcessDescLength = preProcessDescLength;
			this.lcsLength = lcsLength;
		}
		
		@Override
		public int compareTo(Closeness o) {
			int thisMatchLength = endIndex-startIndex;
			int thisPreProcesedDescLength = preProcessDescLength;
			int thisCloseness = Math.abs(thisMatchLength - thisPreProcesedDescLength);
			
			int otherMatchLength = o.startIndex-o.endIndex;
			int otherPreProcessedDescLength = o.preProcessDescLength;
			int otherCloseness = Math.abs(otherMatchLength - otherPreProcessedDescLength);
			
			if(thisCloseness < otherCloseness){
				return -1;
			}else if(thisCloseness == otherCloseness){
				return 0;
			}else{
				return 1;
			}
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.valueOf(2).compareTo(Integer.valueOf(3)));
	}
}
