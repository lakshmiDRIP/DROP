
package org.drip.zen.text;

public class WordProcessing {

	public static boolean IsVowel (
		char alphabet)
	{
		boolean isVowel = false;

		if (alphabet == 'a' || alphabet == 'A')
			isVowel = true;

		if (alphabet == 'e' || alphabet == 'E')
			isVowel = true;

		if (alphabet == 'i' || alphabet == 'I')
			isVowel = true;

		if (alphabet == 'o' || alphabet == 'O')
			isVowel = true;

		if (alphabet == 'u' || alphabet == 'U')
			isVowel = true;

		return isVowel;
	}

	public static int CountVowels (
		final String word)
	{
		int numberOfVowels = 0;
		int alphabetCounter = 0;

		while (alphabetCounter < word.length())
		{
			char wordAlphabet = word.charAt (alphabetCounter);

			if (IsVowel (wordAlphabet))
				numberOfVowels = numberOfVowels + 1;

			alphabetCounter = alphabetCounter + 1;
		}

		return numberOfVowels;
	}

	public static boolean ContainsWord (
		String mainWord,
		String containedWord)
	{
		boolean contained = false;

		for (int mainCounter = 0; mainCounter < mainWord.length() - containedWord.length(); mainCounter = mainCounter + 1)
		{
			int numAlphabetsMatch = 0;
			
			for (int containedCounter = 0; containedCounter < containedWord.length(); containedCounter = containedCounter + 1)
			{
				if (mainWord.charAt (mainCounter + containedCounter) == containedWord.charAt (containedCounter))
					numAlphabetsMatch = numAlphabetsMatch + 1;
			}

			if (numAlphabetsMatch == containedWord.length())
				contained = true;
		}

		return contained;
	}

	public static boolean ContainsWord2 (
		String mainWord,
		String containedWord)
	{
		boolean contained = false;

		int mainWordSize = mainWord.length();

		char[] mainLetters = mainWord.toCharArray();

		int containedWordSize = containedWord.length();

		char[] containedLetters = containedWord.toCharArray();

		if (mainWordSize < containedWordSize)
			return false;

		for (int mainCounter = 0; mainCounter < mainWordSize - containedWordSize; mainCounter = mainCounter + 1)
		{
			int numAlphabetsMatch = 0;
			
			for (int containedCounter = 0; containedCounter < containedWordSize; containedCounter = containedCounter + 1)
			{
				if (mainLetters[mainCounter + containedCounter] != containedLetters[containedCounter])
					break;

				numAlphabetsMatch = numAlphabetsMatch + 1;
			}

			if (numAlphabetsMatch == containedWordSize)
				contained = true;
		}

		return contained;
	}

	public static int ContainedWordCount (
		String mainWord,
		String containedWord)
	{
		int containedWordCount = 0;

		int mainWordSize = mainWord.length();

		char[] mainLetters = mainWord.toCharArray();

		int containedWordSize = containedWord.length();

		char[] containedLetters = containedWord.toCharArray();

		if (mainWordSize < containedWordSize)
			return 0;

		for (int mainCounter = 0; mainCounter < mainWordSize - containedWordSize; mainCounter = mainCounter + 1)
		{
			int numAlphabetsMatch = 0;
			
			for (int containedCounter = 0; containedCounter < containedWordSize; containedCounter = containedCounter + 1)
			{
				if (mainLetters[mainCounter + containedCounter] != containedLetters[containedCounter])
					break;

				numAlphabetsMatch = numAlphabetsMatch + 1;
			}

			if (numAlphabetsMatch == containedWordSize)
				containedWordCount = containedWordCount + 1;
		}

		return containedWordCount;
	}

	public static final void main (
		String[] args)
	{
		String myWord = "New York City, New York";

		int vowelsInWord = CountVowels (myWord);

		System.out.println ("\tNumber of Vowels in " + myWord + " is " + vowelsInWord);

		String myContainedWord = "City";

		System.out.println ("\tIs " + myContainedWord + " inside " + myWord + "? " + ContainsWord2 (myWord, myContainedWord));

		myContainedWord = "new";

		System.out.println ("\tIs " + myContainedWord + " inside " + myWord + "? " + ContainsWord2 (myWord, myContainedWord));

		System.out.println ("\tHow many '" + myContainedWord + "' in '" + myWord + "'? " + ContainedWordCount (myWord, myContainedWord));
	}
}
