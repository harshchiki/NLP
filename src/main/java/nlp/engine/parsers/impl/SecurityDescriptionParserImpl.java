package nlp.engine.parsers.impl;

import java.util.ArrayList;
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

	public SecurityDescriptionParserImpl(Collection<String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public Iterable<String> getSecurityDescription(String line) {

		// for each description that close matches the line, we have a 
		// map of desc to list of closeness, which encapsulates the boundaries of text in 'line'.
		Map<String, Closeness> mapDescCloseness = new HashMap<>(); 

		descriptions.forEach(desc -> {
			String preProcessedDescription = preProcessDescription(desc);
			LCSResult lcsResult = getLengthOfLongestCommonSubsequence(line, preProcessedDescription);
			// considering if there is a 70% match
			if(lcsResult.length > 0 
					//					&& getMatchPercentage(lcsResult.length, preProcessedDescription.length()) > 95.0
					&& areSpacesThereOnEitherSides(line, lcsResult)
					//					&& matchPercentageByEditDistance(line, lcsResult.startIndex, lcsResult.endIndex, desc)

					){

				Closeness closeness = new Closeness(lcsResult.startIndex, 
						lcsResult.endIndex, 
						preProcessedDescription.length(), 
						lcsResult.length,
						line, 
						desc);
				putInMap(mapDescCloseness, desc, closeness);
			}
		});

		// map is ready with the closeness with each description (that qualifies by lcs length)

		List<Closeness> lstDesc = new ArrayList<>(mapDescCloseness.values());
		Collections.sort(lstDesc);
		// the desc with the lowest levenshtein distance is closest to the one in 'line', and so should be returned.
		List<String> securityDescription = new LinkedList<>(); // TODO: ideally this should be just a string, not a collection.
		Closeness bestDesc = lstDesc.get(lstDesc.size() - 1);
		securityDescription.add(line.substring(bestDesc.startIndex, bestDesc.endIndex));
		return securityDescription;
	}

	private List<Integer> getLCSBounds(int[][] L, int m, int n, String X, String Y){
		List<Integer> lst = new ArrayList<>(2);

		// Following code is used to print LCS
		int index = L[m][n];
		int temp = index;

		// Create a character array to store the lcs string
		char[] lcs = new char[index+1];
		lcs[index] = '\0'; // Set the terminating character

		// Start from the right-most-bottom-most corner and
		// one by one store characters in lcs[]
		int i = m, j = n;
		while (i > 0 && j > 0)
		{
			// If current character in X[] and Y are same, then
			// current character is part of LCS
			if (X.charAt(i-1) == Y.charAt(j-1))
			{
				// Put current character in result
				lcs[index-1] = X.charAt(i-1); 

				// reduce values of i, j and index
				i--; 
				j--; 
				index--;     
			}

			// If not same, then find the larger of two and
			// go in the direction of larger value
			else if (L[i-1][j] > L[i][j-1])
				i--;
			else
				j--;
		}

		lst.add(Integer.valueOf(lcs[0]));
		lst.add(Integer.valueOf(lcs[temp]));

		return lst;
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

	// not to be used. TODO: Remove this method
	private double getMatchPercentage(int matchStringLength, int descLength){
		if(descLength == 0) {
			// handling divide by zero exception
			return 0.0;
		}

		double matchPercent = ((double)matchStringLength/(double)descLength)*100;
		return matchPercent;
	}

	private boolean matchPercentageByEditDistance(final String line, final int startIndex, final int endIndex, final String desc) {
		int editDistance = getEditDistance(line, startIndex, endIndex, desc);
		if(Math.abs(editDistance - desc.length()) < 4){
			// this value 4 is just a value assumed (assumed a small number).
			// this could vary
			return true;
		}else {
			return false;
		}
	}

	private int getEditDistance(final String line, final int startIndex, final int endIndex, final String desc) {
		return editDistanceHelper(line.substring(startIndex, endIndex), desc, endIndex-startIndex, desc.length());
	}

	private int editDistanceHelper(String str1, String str2, int m, int n) {
		// Create a table to store results of subproblems
		int editDistanceTable[][] = new int[m+1][n+1];

		// Fill d[][] in bottom up manner
		for (int i=0; i<=m; i++)
		{
			for (int j=0; j<=n; j++)
			{
				// If first string is empty, only option is to
				// insert all characters of second string
				if (i==0)
					editDistanceTable[i][j] = j;  // Min. operations = j

				// If second string is empty, only option is to
				// remove all characters of second string
				else if (j==0)
					editDistanceTable[i][j] = i; // Min. operations = i

				// If last characters are same, 
				// bring forward the left upper diagonal value
				else if (str1.charAt(i-1) == str2.charAt(j-1))
					editDistanceTable[i][j] = editDistanceTable[i-1][j-1];

				// If last character are different, consider all
				// possibilities and find minimum
				else
					editDistanceTable[i][j] = 1 + min(editDistanceTable[i][j-1],  // Insert
							editDistanceTable[i-1][j],  // Remove
							editDistanceTable[i-1][j-1]); // Replace
			}
		}

		return editDistanceTable[m][n];
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
