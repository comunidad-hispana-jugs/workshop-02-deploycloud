package demo.domain;

import java.util.Map;
import java.util.TreeMap;

import demo.domain.Heroe.Role;

/**
 * Model of a Sentence, composed of Heroes.
 * 
 * @author Ken Krueger, Jorge Centeno Fernandez 
 */
public class DcVsMarvel {

	// ordered by role so to make easier print the sentence
	private Map<Role,String> heroes = new TreeMap<>(); 
	
	public void add(Heroe heroe){
		heroes.put(heroe.getRole(),heroe.getHeroe());
	}
	
	@Override
	public String toString() {
	  StringBuilder sb = new StringBuilder();
	  for (Role role: heroes.keySet()){
		  sb.append(heroes.get(role)).append(' ');
	  }
	  return sb.toString();
	}
}
