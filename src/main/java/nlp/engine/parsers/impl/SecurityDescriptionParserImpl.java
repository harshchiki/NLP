package nlp.engine.parsers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import nlp.engine.parsers.SecurityDescriptionParser;



public class SecurityDescriptionParserImpl implements SecurityDescriptionParser {


	private final Collection<String> descriptions;
	private final int FUZZYSEARCH_PARTIAL_RATIO_THRESHOLD = 80;

	public SecurityDescriptionParserImpl(Collection<String> descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * Approach
	 * 1.) Populate Map of Description to Best Closeness (encapsulates the bounds)
	 * 
	 * 2.) 
	 */
	@Override
	public Iterable<String> getSecurityDescription(String line) {
		final Map<String, Closeness> mapDescCloseness = new HashMap<>(); 

		descriptions.forEach(desc -> {
			final String preProcessedDescription = preProcessDescription(desc);
			final LCSResult lcsResult = getLengthOfLongestCommonSubsequence(line, preProcessedDescription);

			if(lcsResult.length > 0 
					&& areSpacesThereOnEitherSides(line, lcsResult)
					&& FuzzySearch.partialRatio(line.substring(lcsResult.startIndex, lcsResult.endIndex), desc) > FUZZYSEARCH_PARTIAL_RATIO_THRESHOLD){
				final Closeness closeness = new Closeness(lcsResult.startIndex, 
						lcsResult.endIndex, 
						preProcessedDescription.length(), 
						lcsResult.length,
						line, 
						desc);
				putInMap(mapDescCloseness, desc, closeness);
			}
		});

		// map is ready with the closeness with each description (that qualifies by lcs length)

		if(mapDescCloseness.isEmpty()){
			return Collections.EMPTY_LIST;
		}
		
		final List<Closeness> lstDesc = new ArrayList<>(mapDescCloseness.values()); 		
		Collections.sort(lstDesc);
		Closeness bestDesc = lstDesc.get(lstDesc.size() - 1);
		
		return Arrays.asList(line.substring(bestDesc.startIndex, bestDesc.endIndex));
	}

	private void putInMap(Map<String, Closeness> mapDescCloseness,
			String desc, Closeness closeness) {
		if(!mapDescCloseness.containsKey(desc)){
			mapDescCloseness.put(desc, closeness);
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



	int min(int a, int b) {
		return a < b ? a : b;
	}

	int min(int a, int b, int c) {
		return c < min(a,b) ? c : min(a,b);
	}

	private LCSResult getLengthOfLongestCommonSubsequence(String line, String desc) {
		char[] lineArray = line.toCharArray();
		char[] descriptionArray = desc.toCharArray();
		int lineLength = lineArray.length, descLength = descriptionArray.length; 

		int indexL=-1,indexD=-1;;
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

					indexL=j+1;
					indexD=i+1;


					table[i+1][j+1] = 1+table[i][j];
				}else {
					table[i+1][j+1] = max(table[i+1][j], table[i][j+1]);
				}
			}
		}

		int endIndex=indexL;
		int startIndex=findStartIndex(indexL,indexD,table,line,desc);


		if(table[descLength][lineLength] > 0){   
			return new LCSResult(table[descLength][lineLength], startIndex, endIndex);
		}else{
			return new LCSResult(0);
		}
	}

	private int findStartIndex(int indexL, int indexD, int[][] table, String line, String desc) {
		
		if(line.charAt(indexL-1) == desc.charAt(indexD-1)){
			if(table[indexD][indexL] == 1){
				return indexL - 1;
			}

			return findStartIndex(indexL-1, indexD-1, table, line, desc) - 1;
		}



		// left  < upper -> go up else go left 
		int upperValue = table[indexD-1][indexL];
		int leftValue = table[indexD][indexL-1];
		if(leftValue < upperValue){
			// go up
			return findStartIndex(indexL, indexD-1, table, line, desc) - 1;
		}
		
		// go left
		return findStartIndex(indexL-1, indexD, table, line, desc) - 1;
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
	}


	private class Closeness implements Comparable<Closeness>{

		final int startIndex, endIndex; // in line
		final int preProcessDescLength;
		final int lcsLength;
		final String line, refDataDesc;
		final int partialRatioLevenshteinDistance;

		Closeness(int startIndex, int endIndex, int preProcessDescLength, int lcsLength, 
				String line, String refDataDesc){

			if(endIndex < startIndex){
				System.out.println("bad indexes = "+startIndex+" "+endIndex);
				System.out.println(refDataDesc);
			}

			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.preProcessDescLength = preProcessDescLength;
			this.lcsLength = lcsLength;
			this.line = line;
			this.refDataDesc = refDataDesc;

			this.partialRatioLevenshteinDistance = FuzzySearch.partialRatio(line.substring(startIndex, endIndex), 
					refDataDesc);
		}

		@Override
		public int compareTo(Closeness o) {
			return comparisonApproachUsingLevenshteinDistance(o);

			//			return comparisonApproach1(o);

		}

		private int comparisonApproachUsingLevenshteinDistance(Closeness o) {
			int thisPartialRatio = this.partialRatioLevenshteinDistance;
			int otherPartialRatio = o.partialRatioLevenshteinDistance;

			if(thisPartialRatio > otherPartialRatio) {
				return 1;
			}else if(thisPartialRatio == otherPartialRatio) {
				return 0;
			}else {
				return -1;
			}
		}

		private int comparisonApproach1(Closeness o) {
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
		// https://github.com/harshchiki/fuzzywuzzy
		String l = "DE0001143378 BUND CPN JUL34 A 8.8mil T1W EUR BID ";
		String rdDesc = "J";

		LCSResult lcsResults = new SecurityDescriptionParserImpl(null).getLengthOfLongestCommonSubsequence(l, rdDesc);

		System.out.println("start = "+lcsResults.startIndex+" end = "+lcsResults.endIndex+" length = "+lcsResults.length);


		System.out.println("sub - "+l.substring(lcsResults.startIndex, lcsResults.endIndex));

		String line = "DBR1 CPN 07/23/2018 DE0001143261 10m";
		String desc = "BUND CPN 07/23/2018";

		System.out.println("line --> "+line);
		System.out.println("desc --> "+desc);

		// Simple Ratio
		System.out.println("Simple Ratio");
		System.out.println(FuzzySearch.ratio(line, desc));


		//		System.out.println("\n\n\n");
		//		
		//Partial ratio
		System.out.println("Partial Ratio");
		System.out.println(FuzzySearch.partialRatio(line, desc));

		System.out.println("--------------------------------");
		//		
		//		
		//		System.out.println("\n\n\n");
		//		
		//		// Token Sort Partial Ratio
		//		System.out.println("Token Sort Partial Ratio");
		//		System.out.println(FuzzySearch.tokenSortPartialRatio(s1,s2));
		//		
		//		System.out.println("\n\n\n");
		//		
		//		
		//		// Token Sort Ratio
		//		System.out.println("Token Sort Ratio");
		//		System.out.println(FuzzySearch.tokenSortRatio(s1,s2));
		//		
		//		System.out.println("\n\n\n");
		//		
		//		// Token Set Ratio
		//		System.out.println("Token Set Ratio");
		//		System.out.println(FuzzySearch.tokenSetRatio(s1,s2));
		//		
		//		System.out.println("\n\n\n");
		//		
		//		System.out.println("Weighted Ratio");
		//		System.out.println(FuzzySearch.weightedRatio(s1,s2));
		//		
		//		System.out.println("\n\n\n");

		// extract methods yet to be evaluated - given in the link, placed at the beginning of this method.

	}
}
