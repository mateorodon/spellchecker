package edu.isistan.spellchecker.corrector.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.isistan.spellchecker.corrector.Corrector;

import java.io.*;

/**
 * Corrector basado en un archivo.
 * 
 */
public class FileCorrector extends Corrector {

	private Map<String, Set<String>> corrections;

	/** Clase especial que se utiliza al tener 
	 * algún error de formato en el archivo de entrada.
	 */
	public static class FormatException extends Exception {
		public FormatException(String msg) {
			super(msg);
		}
	}


	/**
	 * Constructor del FileReader
	 *
	 * Utilice un BufferedReader para leer el archivo de definición
	 *
	 * <p> 
	 * Cada línea del archivo del diccionario tiene el siguiente formato: 
	 * misspelled_word,corrected_version
	 *
	 * <p>
	 *Ejemplo:<br>
	 * <pre>
	 * aligatur,alligator<br>
	 * baloon,balloon<br>
	 * inspite,in spite<br>
	 * who'ev,who've<br>
	 * ther,their<br>
	 * ther,there<br>
	 * </pre>
	 * <p>
	 * Estas líneas no son case-insensitive, por lo que todas deberían generar el mismo efecto:<br>
	 * <pre>
	 * baloon,balloon<br>
	 * Baloon,balloon<br>
	 * Baloon,Balloon<br>
	 * BALOON,balloon<br>
	 * bAlOon,BALLOON<br>
	 * </pre>
	 * <p>
	 * Debe ignorar todos los espacios vacios alrededor de las palabras, por lo
	 * que estas entradas son todas equivalentes:<br>
	 * <pre>
	 * inspite,in spite<br>
	 *    inspite,in spite<br>
	 * inspite   ,in spite<br>
	 *  inspite ,   in spite  <br>
	 * </pre>
	 * Los espacios son permitidos dentro de las sugerencias. 
	 *
	 * <p>
	 * Debería arrojar <code>FileCorrector.FormatException</code> si se encuentra algún
	 * error de formato:<br>
	 * <pre>
	 * ,correct<br>
	 * wrong,<br>
	 * wrong correct<br>
	 * wrong,correct,<br>
	 * </pre>
	 * <p>
	 *
	 * @param r Secuencia de caracteres 
	 * @throws IOException error leyendo el archivo
	 * @throws FileCorrector.FormatException error de formato
	 * @throws IllegalArgumentException reader es null
	 */
	public FileCorrector(Reader r) throws IOException, FormatException {
		BufferedReader in = new BufferedReader(r);
		loadCorrections(in);
	}

	private void loadCorrections(BufferedReader reader) throws IOException, FormatException {
		String line;
		int lineNumber = 0;
		this.corrections = new HashMap<>();

		while ((line = reader.readLine()) != null) {
			lineNumber++;
			String[] parts = line.split(",", 2);

			// Validar el formato
			if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
				throw new FormatException("Error en el formato en la línea " + lineNumber + ": " + line);
			}

			String misspelledWord = parts[0].trim().toLowerCase();
			String correctedVersion = parts[1].trim().toLowerCase();

			// Agregar la corrección a la lista correspondiente en el Map
			corrections.computeIfAbsent(misspelledWord, k -> new HashSet<>()).add(correctedVersion);
		}

		reader.close();
	}

	/** Construye el Filereader.
	 *
	 * @param filename 
	 * @throws IOException 
	 * @throws FileCorrector.FormatException 
	 * @throws FileNotFoundException 
	 */
	public static FileCorrector make(String filename) throws IOException, FormatException {
		Reader r = new FileReader(filename);
		FileCorrector fc;
		try {
			fc = new FileCorrector(r);
		} finally {
			if (r != null) { r.close(); }
		}
		return fc;
	}

	/**
	 * Retorna una lista de correcciones para una palabra dada.
	 * Si la palabra mal escrita no está en el diccionario el set es vacio.
	 * <p>
	 * Ver superclase.
	 *
	 * @param wrong 
	 * @return retorna un conjunto (potencialmente vacío) de sugerencias.
	 * @throws IllegalArgumentException si la entrada no es una palabra válida 
	 */
	public Set<String> getCorrections(String wrong) {
		Set<String> ignoreCase = this.corrections.getOrDefault(wrong.toLowerCase(), new HashSet<>());
		return matchCase(wrong,ignoreCase);
	}
}
