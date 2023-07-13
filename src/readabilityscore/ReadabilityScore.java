package readabilityscore;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadabilityScore {

	private double countOfSentences, countOfWords, countOfCharacters;
	private double score;
	private double totalSyllables, countOfPollySyllables;
	private String inputText;
	private double ageSum;

	private static final Scanner sc = new Scanner(System.in);

	private final String ageGroup[] = { "5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15",
			"15-16", "16-17", "17-18", "18-22" };

	public ReadabilityScore(String inputText) {
		this.inputText = inputText;
		countingsOnText(inputText);
	}

	void calculateFleschKincaidScore() {
		score = 0.39 * (countOfWords / countOfSentences) + 11.8 * (totalSyllables / countOfWords) - 15.59;
		score = Math.floor(score * 100) / 100; // for having 2 digits after decimal without rounding off
		System.out.print("Flesch-Kincaid readability tests: " + score + " (");
		determineAgeSutability((int) Math.ceil(score));
	}

	void calculateSMOGIndexScore() {
		score = 1.043 * Math.sqrt(countOfPollySyllables * (30.0 / countOfSentences)) + 3.1291;
		score = Math.floor(score * 100) / 100; // for having 2 digits after decimal without rounding off
		System.out.print("Simple Measure of Gobbledygook: " + score + " (");
		determineAgeSutability((int) Math.ceil(score));
	}

	void calculateColemanLiauIndex() {
		double averageNumOfCharactersPerWord = countOfCharacters / countOfWords;
		double averageNumOfSentencesPerWord = countOfSentences / countOfWords;
		double L = averageNumOfCharactersPerWord * 100; // L is the average number of characters per 100 words
		double S = averageNumOfSentencesPerWord * 100; // S is the average number of sentences per 100 words.
		score = 0.0588 * L - 0.296 * S - 15.8;
		score = Math.floor(score * 100) / 100; // for having 2 digits after decimal without rounding off
		System.out.print("Coleman-Liau index: " + score + " (");
		determineAgeSutability((int) Math.ceil(score));
	}

	void calculateARIScore() {
		score = 4.71 * (countOfCharacters / countOfWords) + 0.5 * (countOfWords / countOfSentences) - 21.43;
		score = Math.floor(score * 100) / 100; // for having 2 digits after decimal without rounding off
		System.out.print("Automated Readability Index: " + score + " (");
		determineAgeSutability((int) Math.ceil(score));
	}

	private void countingsOnText(String str) {

		// if str has only 1 sentence and it doesn't have full stop, then also below can
		// handle that.
		String[] sentences = str.split("[.!?]"); // splitting the text if any of them occurs. each array will contain a
													// sentence.
		// some of the sentence in this array will have an extra space at the beginning

		countOfSentences = sentences.length; // Total count of sentences will be equal to this array length

		for (int i = 0; i < sentences.length; i++) {

			sentences[i] = sentences[i].trim(); // trimming leading spaces in a sentence

			// System.out.println(sentences[i]);
			// System.out.println("Words in this sentence are :-");

			String wordsArray[] = sentences[i].split(" "); // storing words of the current sentence in an array
			countOfWords += wordsArray.length; // count of words will increase by the amount of words[] length

			// iterating wordsArray[] array for counting characters and syllables in each
			// word of the current sentence
			for (int j = 0; j < wordsArray.length; j++) {
				System.out.println(wordsArray[j]);
				countOfCharacters += wordsArray[j].length(); // characters count will increase by the amount of current
																// word length
				totalSyllables += checkSyllableOfAWord(wordsArray[j]); // sending the current word in the function to
																		// get number of
																		// syllables and polysyllables
			}
		}
		// System.out.println("Count of characters : " + countOfCharacters);
		// Each sentence (except for the last sentence) will definitely ends with '.'
		// Full stop must also be counted as a character.
		// 10 sentences will contain 10 full stops. Just check if the last sentence
		// doesn't have a full stack, if yes then minus 1;
		countOfCharacters = countOfCharacters + countOfSentences - (str.endsWith(".") ? 0 : 1);
	}

	private long checkSyllableOfAWord(String word) {
		final Pattern p = Pattern.compile("[aeiouyAEIOUY]+");
		final Matcher m = p.matcher(word);
		int numberOfSyllables = 0;
		while (m.find()) {
			++numberOfSyllables;
		}
		if (word.endsWith("e")) {
			numberOfSyllables--;
		}
		if (numberOfSyllables <= 0) {
			numberOfSyllables = 1;
		}
		if (numberOfSyllables > 2) {
			countOfPollySyllables++;
		}
		return numberOfSyllables;
	}

	private void determineAgeSutability(double score) {
		score--; // to match with arrayIndex
		if (score >= 14) { // only 14 (index 0-13) age groups are there. So if score is 14 or more, it will
							// always be the last group
			score = 13;
		}
		// System.out.println("index : " + score);
		String age = ageGroup[(int) score];
		age = age.substring(age.indexOf("-") + 1);
		System.out.println("about " + age + " year-olds).");
		ageSum += Double.parseDouble(age);
	}

	void printInfo() {
		System.out.println("The text is:\n" + inputText);
		System.out.println("\nWords: " + (int) countOfWords);
		System.out.println("Sentences: " + (int) countOfSentences);
		System.out.println("Characters: " + (int) countOfCharacters);
		System.out.println("Syllables: " + (int) totalSyllables);
		System.out.println("Polysyllables: " + (int) countOfPollySyllables);

		System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
		String choice = sc.nextLine();
		System.out.println();
		switch (choice) {
		case "ARI": {
			calculateARIScore();
			break;
		}
		case "FK": {
			calculateFleschKincaidScore();
			break;
		}
		case "SMOG": {
			calculateSMOGIndexScore();
			break;
		}
		case "CL": {
			calculateColemanLiauIndex();
			break;
		}
		case "all": {
			calculateARIScore();
			calculateFleschKincaidScore();
			calculateSMOGIndexScore();
			calculateColemanLiauIndex();
			System.out.println("\nThis text should be understood in average by " + (ageSum / 4) + "-year-olds.");
			break;
		}
		default: {
			System.out.println("Invalid input");
		}
		}// end of switch
	}// end of printInfo()
}// end of class