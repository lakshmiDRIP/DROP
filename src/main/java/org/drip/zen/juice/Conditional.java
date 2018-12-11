
package org.drip.zen.juice;

/*
 * 1) Odd/Even
 * 2) Simple Vowel/Consonant Variants
 * 4) Hot/Cold/Comfortable
 * 5) Grades Table
 */

public class Conditional {

	public static boolean IsOdd (int number)
	{
		boolean odd = false;

		if (number % 2 == 0)
			odd = false;
		else
			odd = true;

		return odd;
	}

	public static boolean IsVowel (char alphabet)
	{
		boolean vowel = false;

		if (alphabet == 'a' || alphabet == 'e' || alphabet == 'i' || alphabet == 'o' || alphabet == 'u')
			vowel = true;
		else
			vowel = false;

		return vowel;
	}

	public static boolean IsVowel2 (char alphabet)
	{
		boolean vowel = false;

		if (alphabet == 'a' || alphabet == 'e' || alphabet == 'i' || alphabet == 'o' || alphabet == 'u' || alphabet == 'A' || alphabet == 'E' || alphabet == 'I' || alphabet == 'O' || alphabet == 'U')
			vowel = true;
		else
			vowel = false;

		return vowel;
	}

	public static String HotColdComfortable (double temperature)
	{
		String hotColdComfortable;

		if (temperature < 55)
			hotColdComfortable = "TOO COLD";
		else if (temperature > 85)
			hotColdComfortable = "TOO HOT";
		else
			hotColdComfortable = "JUST RIGHT";

		return hotColdComfortable;
	}

	public static String Grade (double numberGrade)
	{
		String letterGrade;

		if (numberGrade >= 95)
			letterGrade = "A";
		else if (numberGrade >= 85 && numberGrade < 95)
			letterGrade = "B";
		else if (numberGrade >= 75 && numberGrade < 85)
			letterGrade = "C";
		else if (numberGrade >= 65 && numberGrade < 75)
			letterGrade = "D";
		else if (numberGrade >= 55 && numberGrade < 65)
			letterGrade = "E";
		else
			letterGrade = "F";

		return letterGrade;
	}

	public static final void main (String[] args)
	{
		int firstNumber = 33;

		boolean isFirstNumberOdd = IsOdd (firstNumber);

		System.out.println ("\t" + firstNumber + " is Odd? " + isFirstNumberOdd);

		char firstAlphabet = 'o';

		boolean isFirstAlphabetVowel = IsVowel (firstAlphabet);

		System.out.println ("\t" + firstAlphabet + " is Vowel? " + isFirstAlphabetVowel);

		char secondAlphabet = 'O';

		boolean isSecondAlphabetVowel = IsVowel (secondAlphabet);

		System.out.println ("\tFirst Try: " + secondAlphabet + " is Vowel? " + isSecondAlphabetVowel);

		boolean isSecondAlphabetVowel2 = IsVowel2 (secondAlphabet);

		System.out.println ("\tSecond Try: " + secondAlphabet + " is Vowel? " + isSecondAlphabetVowel2);

		double temperature = 75;

		String temperatureFeeling = HotColdComfortable (temperature);

		System.out.println ("\t" + temperature + " is " + temperatureFeeling);

		double score = 93;

		String scoreGrade = Grade (score);

		System.out.println ("\tGrade for " + score + " is " + scoreGrade);
	}
}
