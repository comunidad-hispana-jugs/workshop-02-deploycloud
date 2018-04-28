package demo.domain;



/**
 * 'Word' object is nicely represented in JSON over a regular String.
 * 
 * @author Ken Krueger, Jorge Centeno Fernandez 
 */
public class Heroe {

	private String heroe;
	private Role role;

	public Heroe(String heroe, Role role) {
		this(heroe);
		this.role = role;
	}

	public Heroe(String heroe ) {
		this();
		this.heroe = heroe;
	}

	public Heroe() {
		super();
	}
	

	public String getHeroe() {
		return heroe;
	}

	public void setHeroe(String heroe) {
		this.heroe = heroe;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getString() {
		return getHeroe();
	}
	
	public static enum Role {
		marvel,dc;
	}
}
