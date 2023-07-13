package readabilityscore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	static StringBuilder stringBuilder = new StringBuilder();

	public static void main(String[] args) {
		File file = new File(args[0]);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
				stringBuilder.append(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

		// Example strings below for testing purpose. If you don't have a txt file,
		// kindly comment above lines an use below one

		// stringBuilder.append("This is the front page of the Simple English Wikipedia?
		// Wikipedias are places where people work together to write encyclopedias in
		// different languages. We use Simple English words and grammar here. The Simple
		// English Wikipedia is for everyone! That includes children and adults who are
		// learning English. There are 142,262 articles on the Simple English Wikipedia.
		// All of the pages are free to use. They have all been published under both the
		// Creative Commons License and the GNU Free Documentation License. You can help
		// here! You may change these pages and make new pages. Read the help pages and
		// other good pages to learn how to write pages here. If you need help, you may
		// ask questions at Simple talk. Use Basic English vocabulary and shorter
		// sentences. This allows people to understand normally complex terms or
		// phrases.");

		// stringBuilder.append("includes");

		// stringBuilder.append("Readability is the ease with which a reader can
		// understand a written text. In natural language, the readability of text
		// depends on its content and its presentation. Researchers have used various
		// factors to measure readability. Readability is more than simply legibility,
		// which is a measure of how easily a reader can distinguish individual letters
		// or characters from each other. Higher readability eases reading effort and
		// speed for any reader, but it is especially important for those who do not
		// have high reading comprehension. In readers with poor reading comprehension,
		// raising the readability level of a text from mediocre to good can make the
		// difference between success and failure");

		ReadabilityScore rs = new ReadabilityScore(stringBuilder.toString());
		rs.printInfo();
	}
}
