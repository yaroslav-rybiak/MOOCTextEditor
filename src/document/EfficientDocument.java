package document;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a text document
 * It does one pass through the document to count the number of syllables, words,
 * and sentences and then stores those values.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class EfficientDocument extends Document {

    private int numWords;
    private int numSentences;
    private int numSyllables;

    public EfficientDocument(String text) {
        super(text);
        processText();
    }

    public static void main(String[] args) {
        testCase(new EfficientDocument("This is a test.  How many???  "
                        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new EfficientDocument(""), 0, 0, 0);
        testCase(new EfficientDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new EfficientDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new EfficientDocument("Here is a series of test sentences. Your program should "
                + "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
                + "the correct amount of syllables (example, for example), "
                + "but most of them will."), 49, 33, 3);
        testCase(new EfficientDocument("Segue"), 2, 1, 1);
        testCase(new EfficientDocument("Sentence"), 2, 1, 1);
        testCase(new EfficientDocument("Sentences?!"), 3, 1, 1);
        testCase(new EfficientDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
                32, 15, 1);

    }

    /**
     * Take a string that either contains only alphabetic characters,
     * or only sentence-ending punctuation.  Return true if the string
     * contains only alphabetic characters, and false if it contains
     * end of sentence punctuation.
     *
     * @param tok The string to check
     * @return true if tok is a word, false if it is punctuation.
     */
    private boolean isWord(String tok) {
        return !(tok.indexOf("!") >= 0 || tok.indexOf(".") >= 0 || tok.indexOf("?") >= 0);
    }

    /**
     * Passes through the text one time to count the number of words, syllables
     * and sentences, and set the member variables appropriately.
     * Words, sentences and syllables are defined as described below.
     */
    private void processText() {

        List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
        List<String> words = new ArrayList<>();
        List<StringBuilder> sentences = new ArrayList<>();
        for (int i = 0, k = 0; i < tokens.size(); i++) {
            if (isWord(tokens.get(i))) {
                words.add(tokens.get(i));
                numSyllables += countSyllables(tokens.get(i));
                if (sentences.size() <= k) {
                    sentences.add(k, new StringBuilder());
                }
                sentences.get(k).append(tokens.get(i) + " ");
            } else {
                if (sentences.size() <= k) {
                    sentences.add(k, new StringBuilder());
                }
                k++;
            }
        }
        numWords = words.size();
        numSentences = sentences.size();
    }

    /**
     * This method does NOT process the whole text each time it is called.
     * It returns information already stored in the EfficientDocument object.
     *
     * @return The number of sentences in the document.
     */
    @Override
    public int getNumSentences() {
        return numSentences;
    }

    /**
     * This method does NOT process the whole text each time it is called.
     * It returns information already stored in the EfficientDocument object.
     *
     * @return The number of words in the document.
     */
    @Override
    public int getNumWords() {
        return numWords;
    }

    /**
     * This method does NOT process the whole text each time it is called.
     * It returns information already stored in the EfficientDocument object.
     *
     * @return The number of syllables in the document.
     */
    @Override
    public int getNumSyllables() {
        return numSyllables;
    }
}
