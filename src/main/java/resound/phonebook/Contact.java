package resound.phonebook;

import java.io.Serializable;

public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String number;
	private String comment;
	
	public Contact(String name, String number, String comment){
		this.name = name;
		this.number = number;
		this.comment = comment;
	}
	public Contact(String name, String number){
		this(name,number,"");
	}
	
	public String getName() {
		return name;
	}
	public String getNumber() {
		return number;
	}
	public String getComment() {
		return comment;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+name+" : "+number+"]";
	}
}
