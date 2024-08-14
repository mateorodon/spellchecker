package edu.isistan.spellchecker.corrector.dictionary.trie;

import java.util.HashMap;

/**
 * Leer https://www.baeldung.com/trie-java
 */
public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private String content;
    private boolean isWord;


}