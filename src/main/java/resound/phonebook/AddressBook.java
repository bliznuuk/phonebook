package resound.phonebook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;


public class AddressBook {
	private Map<String,Contact> contactsByName;
	
	public AddressBook(){
		contactsByName = new TreeMap<String,Contact>();
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/address", 
				"root", "armada")){
			Statement statement = connection.createStatement();
			statement.executeQuery("INSERT INTO adresses VALUES('ins_first_to_db','332233','add from statement')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public AddressBook(String name, String number, String comment) throws IOException, SQLException{
		this();
		addContact(name, number, comment);
	}
	public AddressBook(String name, String number) throws IOException, SQLException{
		this(name,number,"");
	}

	Map<String,Contact> getContacts(){
		return contactsByName;
	}
	
	public Contact getContact(String name) {
		return contactsByName.get(name);
	}

	public void addContact(String name, String number, String comment) throws IOException{
		contactsByName.put(name, new Contact(name, number, comment));

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/address", 
				"root", "armada")){
			Statement statement = connection.createStatement();
			statement.executeQuery("INSERT INTO adresses VALUES('ins_first_to_db','332233','add from statement')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		saveContactInformation();
	}
	
	public void addContact(String name, String number) throws IOException{
		addContact(name, number, "");
	}

	public Contact remove(String name) throws IOException{
		Contact contact = contactsByName.remove(name);
		saveContactInformation();
		return contact;
		
	}
	
	public boolean editContact(String oldName, String name, String number, String comment) throws IOException{
		if(contactsByName.remove(oldName)==null)
			return false;
		addContact(name, number, comment);
		saveContactInformation();
		return true;
	}
	
	private void saveContactInformation() throws IOException{
		
	}
}
