package resound.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


public class AddressBook {
//	private Map<String,Contact> contactsByName;
	private static PoolingDataSource poolingDataSource;
	
	public AddressBook() throws SQLException{
		if (poolingDataSource==null){
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
			String dbName = System.getenv("OPENSHIFT_APP_NAME");
			String connectUri = "jdbc:mysql://"+host+":"+port+"/"+dbName;
			String uname = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
			String passwd = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
			
			GenericObjectPool connectionPool = new GenericObjectPool(null);
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectUri, uname, passwd);
			new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
			poolingDataSource = new PoolingDataSource(connectionPool);
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (//Connection connection = DriverManager.getConnection(
			 //	"jdbc:mysql://"+host+":3306/phonebook3", "adminXU9chNa", "pyd5AUHSEa7H")
				Connection connection = poolingDataSource.getConnection()){
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO addresses VALUES('addfrst','111','cmnt')");
		}
	}
	public AddressBook(String name, String number, String comment) throws SQLException{
		this();
		addContact(name, number, comment);
	}
	public AddressBook(String name, String number) throws SQLException{
		this(name,number,"");
	}

	/**
	 * Search in DB and return Contact for name
	 * If DB has more then one record for name, then return the first record
	 * @param name
	 * @return Contact 
	 */
	public Contact getContact(String name) throws SQLException{
		final String SQL = "SELECT name, number, comment FROM addresses WHERE name=?";
		Contact contact = null;
		try(Connection connection = poolingDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL)){
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				contact = new Contact(name, result.getString("number"),	result.getString("comment"));
				break;
			}
		}
		return contact;
	}
	
	public Map<String,Contact> getContacts() throws SQLException{
		final String SQL = "SELECT name, number, comment FROM addresses";
		Map<String,Contact> contacts = new TreeMap<String, Contact>();
		try(Connection connection = poolingDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL)){
			ResultSet result = statement.executeQuery();
			while(result.next()){
				Contact contact = new Contact(result.getString("name"), result.getString("number")
						, result.getString("comment"));
				contacts.put(contact.getName(), contact);
			}
		}
		return contacts;
	}

	public void addContact(String name, String number, String comment)throws SQLException{
		final String SQL = "INSERT INTO addresses(name,number,comment) VALUES(?,?,?)";
		try(Connection connection = poolingDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL);) {
			statement.setString(1, name);
			statement.setString(2, number);
			statement.setString(3, comment);
			statement.executeUpdate();
		}
	}
	
	public void addContact(String name, String number)throws SQLException{
		addContact(name, number, "");
	}

	public void remove(String name) throws SQLException{
		final String SQL = "DELETE FROM addresses WHERE name=?";
		try(Connection connection = poolingDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL)){
			statement.setString(1, name);
			statement.executeUpdate();
		}
	}
	
	public boolean editContact(String name, String number, String comment)throws SQLException{
		boolean edit = false;
		final String SQL = "UPDATE addresses SET number=?, comment=? WHERE name=?";
		try(Connection connection = poolingDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL);){
			statement.setString(3, name);
			statement.setString(1, number);
			statement.setString(2, comment);
			statement.executeUpdate();
			edit = true;
		}
		return edit;
	}
}
