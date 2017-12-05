
package org.drip.zen.juice;

/*
 * 1) Sum Using WHILE Loop
 * 2) Sum Using FOR Loop
 * 3) Factorial Using WHILE Loop
 * 4) Factorial Using FOR Loop
 * 5) Counting the letters in a word
 * 6) Counting vowels in a word
 * 7) Counting consonants in a word
 * 8) Counting words in a sentence
 */

public class Loops {

	static double SumUsingWhileLoop (double firstNumber, double lastNumber)
	{
		double sum = 0;
		double counter = firstNumber;

		while (counter <= lastNumber)
		{
			sum = sum + counter;
			counter = counter + 1;
		}

		return sum;
	}

	static double SumUsingForLoop (double firstNumber, double lastNumber)
	{
		double sum = 0;

		for (double counter = firstNumber; counter <= lastNumber; counter = counter + 1)
		{
			sum = sum + counter;
		}

		return sum;
	}

	static double FactorialUsingWhileLoop (double lastNumber)
	{
		double factorial = 1;
		double counter = 1;

		while (counter <= lastNumber)
		{
			factorial = factorial * counter;
			counter = counter + 1;
		}

		return factorial;
	}

	static double FactorialUsingForLoop (double lastNumber)
	{
		double factorial = 1;

		for (double counter = 1; counter <= lastNumber; counter = counter + 1)
		{
			factorial = factorial * counter;
		}

		return factorial;
	}

	static int WordSize (String word)
	{
		char[] letters = word.toCharArray();

		int size = letters.length;
		return size;
	}

	static int VowelCount (String word)
	{
		int size = WordSize (word);

		int numberOfVowels = 0;

		for (int counter = 0; counter < size; counter = counter + 1)
		{
			char letter = word.charAt (counter);

			if (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u')
			{
				numberOfVowels = numberOfVowels + 1;
			}
		}

		return numberOfVowels;
	}

	static int ConsonantCount (String word)
	{
		int size = WordSize (word);

		int numberOfConsonants = 0;

		for (int counter = 0; counter < size; counter = counter + 1)
		{
			char letter = word.charAt (counter);

			if (letter == 'b' || letter == 'c' || letter == 'd' || letter == 'f' || letter == 'g' || letter == 'h' || letter == 'j' ||
				letter == 'k' || letter == 'l' || letter == 'm' || letter == 'n' || letter == 'p' || letter == 'q' || letter == 'r' ||
				letter == 's' || letter == 't' || letter == 'v' || letter == 'w' || letter == 'x' || letter == 'y' || letter == 'z')
			{
				numberOfConsonants = numberOfConsonants + 1;
			}
		}

		return numberOfConsonants;
	}

	static int WordCount (String sentence)
	{
		int size = WordSize (sentence);

		int numberOfWords = 1;

		for (int counter = 0; counter < size; counter = counter + 1)
		{
			char letter = sentence.charAt (counter);

			if (letter == ' ')
			{
				numberOfWords = numberOfWords + 1;
			}
		}

		return numberOfWords;
	}

	public static void main (String[] arguments)
	{
		double beginNumber = 1;
		double endNumber = 10;

		double sumWithWHILEStatement = SumUsingWhileLoop (beginNumber, endNumber);

		System.out.println ("\tSum with WHILE KeyWord       : " + sumWithWHILEStatement);

		double sumWithFORStatement = SumUsingForLoop (beginNumber, endNumber);

		System.out.println ("\tSum with FOR KeyWord         : " + sumWithFORStatement);

		double factorialWithWHILEStatement = FactorialUsingWhileLoop (endNumber);

		System.out.println ("\tFactorial with WHILE KeyWord : " + factorialWithWHILEStatement);

		double factorialWithFORStatement = FactorialUsingForLoop (endNumber);

		System.out.println ("\tFactorial with FOR KeyWord   : " + factorialWithFORStatement);

		String bigWord = "totalitarianism";

		int bigWordSize = WordSize (bigWord);

		System.out.println ("\tSize of " + bigWord + " is " + bigWordSize);

		int vowelsInBigWord = VowelCount (bigWord);

		System.out.println ("\tNumber of Vowels in " + bigWord + " is " + vowelsInBigWord);

		int consonantsInBigWord = ConsonantCount (bigWord);

		System.out.println ("\tNumber of Consonants in " + bigWord + " is " + consonantsInBigWord);

		String bigSentence = "He who pays the piper calls the tune.";

		int wordsInBigSentence = WordCount (bigSentence);

		System.out.println ("\tNumber of Words in '" + bigSentence + "' is " + wordsInBigSentence);
	}
}
