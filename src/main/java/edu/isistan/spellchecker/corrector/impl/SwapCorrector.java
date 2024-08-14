package edu.isistan.spellchecker.corrector.impl;

import java.util.HashSet;
import java.util.Set;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.dictionary.Dictionary;
/**
 * Este corrector sugiere correciones cuando dos letras adyacentes han sido cambiadas.
 * <p>
 * Un error com�n es cambiar las letras de orden, e.g.
 * "with" -> "wiht". Este corrector intenta dectectar palabras con exactamente un swap.
 * <p>
 * Por ejemplo, si la palabra mal escrita es "haet", se debe sugerir
 * tanto "heat" como "hate".
 * <p>
 * Solo cambio de letras contiguas se considera como swap.
 */
public class SwapCorrector extends Corrector {

	Dictionary dictionary;

	/**
	 * Construcye el SwapCorrector usando un Dictionary.
	 *
	 * @param dict 
	 * @throws IllegalArgumentException si el diccionario provisto es null
	 */
	public SwapCorrector(Dictionary dict) {
		dictionary = dict;
	}

	/**
	 * 
	 * Este corrector sugiere correciones cuando dos letras adyacentes han sido cambiadas.
	 * <p>
	 * Un error com�n es cambiar las letras de orden, e.g.
	 * "with" -> "wiht". Este corrector intenta dectectar palabras con exactamente un swap.
	 * <p>
	 * Por ejemplo, si la palabra mal escrita es "haet", se debe sugerir
	 * tanto "heat" como "hate".
	 * <p>
	 * Solo cambio de letras contiguas se considera como swap.
	 * <p>
	 * Ver superclase.
	 *
	 * @param wrong 
	 * @return retorna un conjunto (potencialmente vac�o) de sugerencias.
	 * @throws IllegalArgumentException si la entrada no es una palabra v�lida 
	 */
	@Override
	public Set<String> getCorrections(String wrong) {
		Set<String> corrections = new HashSet<>();

		// Generar todas las combinaciones con letras adyacentes intercambiadas
		for (int i = 0; i < wrong.length() - 1; i++) {
			String swapped = swapAdjacent(wrong, i);
			if (dictionary.isWord(swapped)) {
				corrections.add(swapped);
			}
		}

		return corrections;
	}

	// Método auxiliar para intercambiar dos letras adyacentes
	private String swapAdjacent(String word, int index) {
		char[] chars = word.toCharArray();
		char temp = chars[index];
		chars[index] = chars[index + 1];
		chars[index + 1] = temp;
		return new String(chars);
	}
}
