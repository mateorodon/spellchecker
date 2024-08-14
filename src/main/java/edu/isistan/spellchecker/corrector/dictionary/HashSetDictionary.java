package edu.isistan.spellchecker.corrector.dictionary;

import edu.isistan.spellchecker.tokenizer.TokenScanner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HashSetDictionary extends Dictionary {

    private Set<String> words;

    /**
     * Construye un diccionario usando un TokenScanner.
     * Una palabra válida es una secuencia de letras (ver Character.isLetter) o apostrofes.
     * Toda palabra no válida se debe ignorar
     * @param ts
     * @throws IOException Error leyendo el archivo
     * @throws IllegalArgumentException el TokenScanner es null
     */

    public HashSetDictionary(TokenScanner ts) throws IOException {
        this.words = new HashSet<>();
        while (ts.hasNext()) {
            addWord(ts.next());
        }
    }

    public Set<String> getWords(){
        return this.words;
    }

    @Override
    public int getNumWords() {
        return words.size();
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    @Override
    public void addWord(String word) {
        if (isValid(word) && !words.contains(word.toLowerCase()))
            words.add(word.toLowerCase());
    }

}
