
package org.drip.zen.juice;

import java.io.BufferedReader;
import java.io.FileReader;

/*
 * 1) Number of lines in the file
 * 2) Read the File into a String Array
 * 3) Count the Number of Words in a Single Line
 * 4) Count the Number Words across the full File
 * 5) Display the File Contents
 * 6) Some Notes on Error Handling
 * 7) Exercise - Count the Number of Instances of Occurrence of a Word (example, Romeo)
 * 8) Exercise - Find out the Line that has the most Occurrence of the given Word
 */

public class FileProcessing {

	static int NumberOfLinesInFile (String fullFileName)
		throws Exception
	{
		String line = "";
		int numberOfLines = 1;
		boolean stopReading = false;

		BufferedReader reader = new BufferedReader (new FileReader (fullFileName));

		while (stopReading == false)
		{
			line = reader.readLine();

			if (line == null)
			{
				stopReading = true;
			}
			else
			{
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

		while (stopReading == false)
		{
			line = reader.readLine();

			if (line == null)
				stopReading = true;
			else
			{
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

	public static final void main (String[] input)
		throws Exception
	{
		String fileLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Feeds\\TextMiner\\RomeoAndJuliet.txt";

		int fileLineCount = NumberOfLinesInFile (fileLocation);

		String[] fileContents = ReadFile (fileLocation);

		int totalWords = 0;

		for (int i = 0; i < fileLineCount; i = i + 1)
		{
			String currentLine = fileContents[i];

			int numberOfWordsInCurrentLine = WordCount (currentLine);

			totalWords = totalWords + numberOfWordsInCurrentLine;

			String[] wordsInCurrentLine = Words (currentLine);

			String wordDump = "";

			for (int j = 0; j < numberOfWordsInCurrentLine; j = j + 1)
			{
				wordDump = wordDump + wordsInCurrentLine[j] + ",";
			}

			System.out.println (wordDump);
		}

		System.out.println ("\tNumber of Lines in File: " + fileLineCount);

		System.out.println ("\tNumber of Words in File: " + totalWords);
	}
}
