package edu.isistan.spellchecker.corrector.dictionary;

import java.io.*;
import java.util.Set;

import edu.isistan.spellchecker.corrector.dictionary.trie.TrieDictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

/**
 * El diccionario maneja todas las palabras conocidas.
 * El diccionario es case insensitive 
 * 
 * Una palabra "v�lida" es una secuencia de letras (determinado por Character.isLetter) 
 * o apostrofes.
 */
public abstract class Dictionary {
	/**
	 * Construye un diccionario usando un archivo.

	 * @param filename
	 * @throws FileNotFoundException si el archivo no existe
	 * @throws IOException Error leyendo el archivo
	 */
	public static Dictionary make(String filename, boolean useTrie) throws IOException {
		Reader r = new FileReader(filename);
		Dictionary d;
		if (useTrie)
			d = new TrieDictionary(new TokenScanner(r));
		else
			d = new HashSetDictionary(new TokenScanner(r));
		r.close();
		return d;
	}

	/**
	 * Testea si una palabra es valida para ser incluida en el diccionario.
	 * Una palabra "válida" es una secuencia de letras (determinado por Character.isLetter)
	 *  o apostrofes.
	 */
	public boolean isValid(String word) {
		return word != null &&
				!word.isEmpty() &&
				!word.equals(" ") &&
				!word.contains("\n") &&
				word.chars().allMatch(ch -> Character.isLetter(ch) || ch == '\'');
	}

	/**
	 * Retorna el número de palabras correctas en el diccionario.
	 * Recuerde que como es case insensitive si Dogs y doGs están en el
	 * diccionario, cuentan como una sola palabra.
	 *
	 * @return número de palabras únicas
	 */
	public abstract int getNumWords();

	/**
	 * Testea si una palabra es parte del diccionario. Si la palabra no est� en
	 * el diccionario debe retornar false. null debe retornar falso.
	 * Si en el diccionario est� la palabra Dog y se pregunta por la palabra dog
	 * debe retornar true, ya que es case insensitive.
	 *
	 *Llamar a este m�todo no debe reabrir el archivo de palabras.
	 *
	 * @param word verifica si la palabra est� en el diccionario.
	 * Asuma que todos los espacios en blanco antes y despues de la palabra fueron removidos.
	 * @return si la palabra est� en el diccionario.
	 */
	public abstract boolean isWord(String word);

	public abstract void addWord(String word);

	public abstract Set<String> getWords();

}