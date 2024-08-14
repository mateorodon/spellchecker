package edu.isistan.spellchecker.corrector.dictionary.trie;

import edu.isistan.spellchecker.corrector.dictionary.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

import java.io.IOException;
import java.util.Set;

public class TrieDictionary extends Dictionary {

    private TrieNode root;

    public TrieDictionary(TokenScanner ts) throws IOException {
        this.root  = new TrieNode();
    }

    @Override
    public int getNumWords() {
        return 0;
    }

    @Override
    public boolean isWord(String word) {
        return false;
    }

    @Override
    public void addWord(String word) {

    }

    @Override
    public Set<String> getWords() {
        return null;
    }
}
