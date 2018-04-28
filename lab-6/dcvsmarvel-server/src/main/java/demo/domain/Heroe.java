package demo.domain;

/**
 * 'Word' object is nicely represented in JSON over a regular String.
 */
public class Heroe {

	public String word;

	public Heroe() {
		super();
	}	
	
	public Heroe(String word) {
		this();
		this.word = word;
	}

	public String getWord() {
		return word;
	}
	
	public String getString() {
		return getWord();
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
