package demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Jug {

	@Id
	@GeneratedValue
	Long id;
	String name;
	String location;
	String mascot;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="jugId")
	Set<Member> members;
	
	
	public Jug() {
		super();
	}
	public Jug(String location, String name, Set<Member> members) {
		this();
		this.name = name;
		this.location = location;
		this.members = members;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMascot() {
		return mascot;
	}
	public void setMascot(String mascot) {
		this.mascot = mascot;
	}
	public Set<Member> getMembers() {
		return members;
	}
	public void setMembers(Set<Member> members) {
		this.members = members;
	}
	
	
}
