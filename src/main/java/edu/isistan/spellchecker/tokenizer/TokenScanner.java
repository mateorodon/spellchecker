package edu.isistan.spellchecker.tokenizer;

import java.io.Reader;
import java.util.Iterator;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Dado un archivo provee un m�todo para recorrerlo.
 */
public class TokenScanner implements Iterator<String> {

  private Reader reader;
  private int nextChar;
  private StringBuilder token;

  /**
   * Crea un TokenScanner.
   * <p>
   * Como un iterador, el TokenScanner solo debe leer lo justo y
   * necesario para implementar los m�todos next() y hasNext(). 
   * No se debe leer toda la entrada de una.
   * <p>
   *
   * @param in fuente de entrada
   * @throws IOException si hay alg�n error leyendo.
   * @throws IllegalArgumentException si el Reader provisto es null
   */
  public TokenScanner(Reader in) throws IOException {
    if (in == null) {
      throw new IllegalArgumentException("Reader cannot be null");
    }
    this.reader = in;
    this.nextChar = reader.read();
    this.token = new StringBuilder();
  }

  /**
   * Determina si un car�cer es una caracter v�lido para una palabra.
   * <p>
   * Un caracter v�lido es una letra (
   * Character.isLetter) o una apostrofe '\''.
   *
   * @param c 
   * @return true si es un caracter
   */
  public static boolean isWordCharacter(int c) {
    return Character.isLetter(c) || c == '\'';
  }


   /**
   * Determina si un string es una palabra v�lida.
   * Null no es una palabra v�lida.
   * Un string que todos sus caracteres son v�lidos es una 
   * palabra. Por lo tanto, el string vac�o es una palabra v�lida.
   * @param s 
   * @return true si el string es una palabra.
   */
   public static boolean isWord(String s) {
     if (s == null) {
       return false;
     }
     for (char c : s.toCharArray()) {
       if (!isWordCharacter(c)) {
         return false;
       }
     }
     return true;
   }

  /**
   * Determina si hay otro token en el reader.
   */
  public boolean hasNext() {
    return nextChar != -1;
  }

  /**
   * Retorna el siguiente token.
   *
   * @throws NoSuchElementException cuando se alcanz� el final de stream
   */
  public String next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }

    token.setLength(0);  // Limpiar el token anterior

    try {
      boolean isWordToken = isWordCharacter(nextChar);
      do {
        token.append((char) nextChar);
        nextChar = reader.read();
      } while (nextChar != -1 && isWordCharacter(nextChar) == isWordToken);

    } catch (IOException e) {
      throw new NoSuchElementException("Error reading from input");
    }

    return token.toString();
  }

}
