
package org.drip.zen.juice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class FileProcessingMap {

	static int NumberOfLinesInFile (String fullFileName)
		throws Exception
	{
		String line = "";
		int numberOfLines = 1;
		boolean stopReading = false;

		BufferedReader reader = new BufferedReader (new FileReader (fullFileName));

		while (stopReading == false) {
			line = reader.readLine();

			if (line == null) {
				stopReading = true;
			} else {
				numberOfLines = numberOfLines + 1;
			}
		}

		reader.close();

		return numberOfLines;
	}

	static String[] ReadFile (String fullFileName)
		throws Exception
	{
		int numberOfLinesToRead = NumberOfLinesInFile (fullFileName);

		String[] fileLines = new String[numberOfLinesToRead];

		String line = "";
		int lineNumber = 0;
		boolean stopReading = false;

		BufferedReader reader = new BufferedReader (new FileReader (fullFileName));

		while (stopReading == false) {
			line = reader.readLine();

			if (line == null)
				stopReading = true;
			else {
				fileLines[lineNumber] = line;
				lineNumber = lineNumber + 1;
			}
		}

		reader.close();

		return fileLines;
	}

	static int WordCount (String singleLine)
	{
		if (singleLine == null)
		{
			return 0;
		}

		int count = 1;

		for (int letterIndex = 0; letterIndex < singleLine.length(); letterIndex = letterIndex + 1)
		{
			char letter = singleLine.charAt (letterIndex);

			if (letter == ' ')
			{
				count = count + 1;
			}
		}

		return count;
	}

	static String[] Words (String singleLine)
	{
		if (singleLine == null)
		{
			return null;
		}

		String[] wordsInLine = singleLine.split (" ");

		return wordsInLine;
	}

	static void AddWordToCountMap (String singleLine, HashMap<String, Integer> wordCountMap)
	{
		String[] wordArray = Words (singleLine);

		if (wordArray == null)
		{
			return;
		}

		for (int j = 0; j < wordArray.length; j = j + 1)
		{
			String thisWord = wordArray[j];

			if (wordCountMap.containsKey (thisWord)) {
				int wordOccurrances = wordCountMap.get (thisWord);

				wordCountMap.put (thisWord, wordOccurrances + 1);
			} else {
				wordCountMap.put (thisWord, 1);
			}
		}
	}

	static TreeMap<Integer, String> CountToWordMap (HashMap<String, Integer> wordToCountMap)
	{
		TreeMap<Integer, String> counterToWordsMap = new TreeMap<Integer, String>();

		Set<String> wordSet = wordToCountMap.keySet();

		for (String word : wordSet)
		{
			counterToWordsMap.put (wordToCountMap.get (word), word);
		}

		return counterToWordsMap;
	}

	/*
	 * Begin Added 7 May 2016
	 */

	static TreeMap<Integer, Set<String>> CountToWordArrayMap (HashMap<String, Integer> wordToCountMap)
	{
		TreeMap<Integer, Set<String>> counterToWordArrayMap = new TreeMap<Integer, Set<String>>();

		Set<String> wordSet = wordToCountMap.keySet();

		for (String word : wordSet)
		{
			int wordCount = wordToCountMap.get (word);

			if (counterToWordArrayMap.containsKey (wordCount))
			{
				Set<String> countedWordSet = counterToWordArrayMap.get (wordCount);

				countedWordSet.add (word);

				counterToWordArrayMap.put (wordCount, countedWordSet);
			}
			else
			{
				Set<String> countedWordSet = new HashSet<String>();

				countedWordSet.add (word);

				counterToWordArrayMap.put (wordCount, countedWordSet);
			}
		}

		return counterToWordArrayMap;
	}

	/*
	 * End Added 7 May 2016
	 */

	public static final void main (String[] input)
		throws Exception
	{
		String fileLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Feeds\\TextMiner\\RomeoAndJuliet.txt";

		int fileLineCount = NumberOfLinesInFile (fileLocation);

		String[] fileContents = ReadFile (fileLocation);

		int totalWords = 0;

		HashMap<String, Integer> wordToCountMap = new HashMap<String, Integer>();

		for (int i = 0; i < fileLineCount; i = i + 1)
		{
			String currentLine = fileContents[i];

			int numberOfWordsInCurrentLine = WordCount (currentLine);

			totalWords = totalWords + numberOfWordsInCurrentLine;

			String[] wordsInCurrentLine = Words (currentLine);

			String wordDump = "";

			AddWordToCountMap (currentLine, wordToCountMap);

			for (int j = 0; j < numberOfWordsInCurrentLine; j = j + 1)
			{
				wordDump = wordDump + wordsInCurrentLine[j] + ",";
			}

			System.out.println (wordDump);
		}

		System.out.println ("\tNumber of Lines in File: " + fileLineCount);

		System.out.println ("\tNumber of Words in File: " + totalWords);

		/* Set<String> wordSet = wordToCountMap.keySet();

		for (String word : wordSet)
		{
			System.out.println ("\t\t[" + word + "] => " + wordToCountMap.get (word));
		} */

		/*
		 * Begin Added 7 May 2016
		 */

		TreeMap<Integer, String> wordCounterMap = CountToWordMap (wordToCountMap);

		Set<Integer> wordCounterSet = wordCounterMap.descendingKeySet();

		for (int wordCount : wordCounterSet)
		{
			System.out.println ("\t\t[" + wordCount + "] => '" + wordCounterMap.get (wordCount) + "'");
		}

		TreeMap<Integer, Set<String>> wordCountSetMap = CountToWordArrayMap (wordToCountMap);

		Set<Integer> wordSetCountSet = wordCountSetMap.descendingKeySet();

		for (int wordSetCount : wordSetCountSet)
		{
			String counterLineToPrint = "\t\t[" + wordSetCount + "] =>";

			Set<String> countedWordSet = wordCountSetMap.get (wordSetCount);

			for (String countedWord : countedWordSet)
			{
				counterLineToPrint = counterLineToPrint + " '" + countedWord + "' |";
			}

			System.out.println (counterLineToPrint);
		}

		/*
		 * End Added 7 May 2016
		 */
	}
}
