package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 */
public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 1000;

    Dictionary dict;

    public NearbyWords(Dictionary dict) {
        this.dict = dict;
    }

    public static void main(String[] args) {
        String word = "i";
        // Pass NearbyWords any Dictionary implementation you prefer
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        NearbyWords w = new NearbyWords(d);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");

        word = "tailo";
        List<String> suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);
    }

    /**
     * Return the list of Strings that are one modification away
     * from the input string.
     *
     * @param s         The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    public List<String> distanceOne(String s, boolean wordsOnly) {
        List<String> retList = new ArrayList<String>();
        insertions(s, retList, wordsOnly);
        substitution(s, retList, wordsOnly);
        deletions(s, retList, wordsOnly);
        return retList;
    }

    /**
     * Add to the currentList Strings that are one character mutation away
     * from the input string.
     *
     * @param s           The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     */
    public void substitution(String s, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for (int index = 0; index < s.length(); index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // use StringBuffer for an easy interface to permuting the
                // letters in the String
                StringBuffer sb = new StringBuffer(s);
                sb.setCharAt(index, (char) charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(sb.toString()) &&
                        (!wordsOnly || dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character insertion away
     * from the input string.
     *
     * @param s           The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     */
    public void insertions(String s, List<String> currentList, boolean wordsOnly) {
        //for each position in the word
        //including the one before first letter,
        //the ones between each letter
        //and the one after last letter
        for (int i = 0; i < s.length() + 1; i++) {
            //for each letter of the alphabet
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                //insert letter into the position
                String start = s.substring(0, i);
                String end = s.substring(i);
                String result = start + (char) charCode + end;
                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(result) &&
                        (!wordsOnly || dict.isWord(result)) &&
                        !s.equals(result)) {
                    currentList.add(result);
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param s           The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     */
    public void deletions(String s, List<String> currentList, boolean wordsOnly) {
        //for each letter in the word
        for (int i = 0; i < s.length(); i++) {
            //remove letter
            String start = s.substring(0, i);
            String end = s.substring(i + 1);
            String result = start + end;
            // if the item isn't in the list, isn't the original string, and
            // (if wordsOnly is true) is a real word, add to the list
            if (!currentList.contains(result) &&
                    (!wordsOnly || dict.isWord(result)) &&
                    !s.equals(result)) {
                currentList.add(result);
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param word           The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {


        //Create a queue to hold words to explore
        List<String> queue = new LinkedList<String>();

        //Create a visited set to avoid looking at the same String repeatedly
        HashSet<String> visited = new HashSet<String>();

        //Create list of real words to return when finished
        List<String> retList = new LinkedList<String>();

        //Add the initial word to the queue and visited
        queue.add(word);
        visited.add(word);

        //while the queue has elements and we need more suggestions
        while (!queue.isEmpty() && retList.size() < numSuggestions) {
            //remove the word from the start of the queue and assign to curr
            String curr = queue.remove(queue.size() - 1);

            //get a list of neighbors (strings one mutation away from curr)
            List<String> neighbors = distanceOne(curr, true);

            //for each n in the list of neighbors
            for (String n : neighbors) {
                //if n is not visited
                if (!visited.contains(n)) {
                    //add n to the visited set
                    visited.add(n);

                    //add n to the back of the queue
                    queue.add(n);
                }

                //if n is a word in the dictionary
                if (dict.isWord(n)) {
                    //add n to the list of words to return
                    retList.add(n);
                }
            }
        }
        //return the list of real words
        return retList;
    }
}
