
package org.drip.zen.juice;

/*
 * 1) What we've seen so far - Functions, Conditionals, Loops
 * 2) Primitive Data Types Review
 * 3) More involved Objects - Strings - Collection of Letters side by side - example
 * 4) Display the Letters in a String
 * 5) Vowels Count
 * 6) Consonant Count
 * 7) Word Count
 * 8) Word Containment - Describe
 * 9) Word Containment - Step Through
 * 10) Word Containment - Edge Cases
 */

public class StringFunctions {

	static void DisplayLetters (String word)
	{
		char[] letters = word.toCharArray();

		int wordSize = letters.length;

		for (int i = 0; i < wordSize; i = i + 1)
		{
			System.out.println (letters[i]);
		}
	}

	static int VowelCount (String word)
	{
		char[] letters = word.toCharArray();

		int wordSize = letters.length;
		int numberOfVowels = 0;

		for (int i = 0; i < wordSize; i = i + 1)
		{
			char alphabet = letters[i];

			if (alphabet == 'a' || alphabet == 'e' || alphabet == 'i' || alphabet == 'o' || alphabet == 'u')
			{
				numberOfVowels = numberOfVowels + 1;
			}
		}

		return numberOfVowels;
	}

	static int ConsonantCount (String word)
	{
		char[] letters = word.toCharArray();

		int wordSize = letters.length;
		int numberOfVowels = 0;

		for (int i = 0; i < wordSize; i = i + 1)
		{
			char alphabet = letters[i];

			if (alphabet == 'b' || alphabet == 'c' || alphabet == 'd' || alphabet == 'f' || alphabet == 'g' || alphabet == 'h' ||
				alphabet == 'j' || alphabet == 'k' || alphabet == 'l' || alphabet == 'm' || alphabet == 'n' || alphabet == 'p' ||
				alphabet == 'q' || alphabet == 'r' || alphabet == 's' || alphabet == 't' || alphabet == 'v' || alphabet == 'w' ||
				alphabet == 'x' || alphabet == 'y' || alphabet == 'z')
			{
				numberOfVowels = numberOfVowels + 1;
			}
		}

		return numberOfVowels;
	}

	static int WordCount (String sentence)
	{
		char[] letters = sentence.toCharArray();

		int sentenceSize = letters.length;
		int numberOfWords = 1;

		for (int i = 0; i < sentenceSize; i = i + 1)
		{
			char alphabet = letters[i];

			if (alphabet == ' ')
			{
				numberOfWords = numberOfWords + 1;
			}
		}

		return numberOfWords;
	}

	public static boolean SecondWordInFirst (String firstWord, String secondWord)
	{
		char[] firstWordLetters = firstWord.toCharArray();

		int firstWordSize = firstWordLetters.length;

		char[] secondWordLetters = secondWord.toCharArray();

		int secondWordSize = secondWordLetters.length;

		// if (firstWordSize < secondWordSize) return false;

		boolean secondInsideFirst = false;

		for (int firstWordCounter = 0; firstWordCounter <= firstWordSize - secondWordSize; firstWordCounter = firstWordCounter + 1)
		{
			int numberofLettersMatch = 0;

			for (int secondWordCounter = 0; secondWordCounter < secondWordSize; secondWordCounter = secondWordCounter + 1)
			{
				if (firstWordLetters[firstWordCounter + secondWordCounter] == secondWordLetters[secondWordCounter])
				{
					numberofLettersMatch = numberofLettersMatch + 1;
				}
			}

			if (numberofLettersMatch == secondWordSize)
			{
				secondInsideFirst = true;
			}
		}

		return secondInsideFirst;
	}

	public static void main (String[] input)
	{
		String myWord = "elephant";

		DisplayLetters (myWord);

		int vowelsInWord = VowelCount (myWord);

		System.out.println ("\tNumber of Vowels in '" + myWord + "'    : " + vowelsInWord);

		int consonantsInWord = ConsonantCount (myWord);

		System.out.println ("\tNumber of Consonants in '" + myWord + "': " + consonantsInWord);

		String mySentence = "he who pays the piper plays the tune";

		int wordsInSentence = WordCount (mySentence);

		System.out.println ("\tNumber of Words in '" + mySentence + "': " + wordsInSentence);

		String myFirstWord = "bold";
		String mySecondWord = "old";

		boolean isSecondInsideFirst = SecondWordInFirst (myFirstWord, mySecondWord);

		System.out.println ("\tIs '" + mySecondWord + "' in '" + myFirstWord + "'? " + isSecondInsideFirst);
	}
}
