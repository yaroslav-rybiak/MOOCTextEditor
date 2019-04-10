package spelling;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie() {
        root = new TrieNode();
    }


    /**
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        if (word.isEmpty()) return false;
        word = word.toLowerCase();
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.getChild(c) == null) {
                node = node.insert(c);
            } else {
                node = node.getChild(c);
            }
        }
        if (!node.endsWord()) {
            node.setEndsWord(true);
            size++;
        }
        return node.getText().charAt(0) == word.charAt(word.length() - 1);
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size;
    }


    /**
     * Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week.
     */
    @Override
    public boolean isWord(String s) {
        if (s.isEmpty()) return false;
        s = s.toLowerCase();
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            node = node.getChild(c);
            if (node == null) return false;
        }
        return true;
    }

    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     * <p>
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     * <p>
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // This method should implement the following algorithm:
        // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
        //    empty list
        prefix = prefix.toLowerCase();
        if (!isWord(prefix)) return new LinkedList<>();

        // 2. Once the stem is found, perform a breadth first search to generate completions
        //    using the following algorithm:
        //    Create a queue (LinkedList) and add the node that completes the stem to the back
        //       of the list.
        Queue<TrieNode> nodes = new LinkedList<>();
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.getChild(c);
        }
        nodes.add(node);

        //    Create a list of completions to return (initially empty)
        List<String> completions = new LinkedList<>();
        //    While the queue is not empty and you don't have enough completions:
        while (!nodes.isEmpty() && completions.size() < numCompletions) {
            //       remove the first Node from the queue
            node = nodes.remove();
            //       If it is a word, add it to the completions list
            if (node.endsWord()) {
                completions.add(node.getText());

            }
            //       Add all of its child nodes to the back of the queue
            LinkedList<TrieNode> children = new LinkedList<>();
            for (char c : node.getValidNextCharacters()) {
                children.add(node.getChild(c));
            }
            nodes.addAll(children);
        }
        // Return the list of completions
        return completions;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }


}