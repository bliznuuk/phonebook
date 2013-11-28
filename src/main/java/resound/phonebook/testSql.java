package resound.phonebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class testSql {
	private static final String HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	private static final String PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	private static final String USERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
	private static final String PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
	private static final String URL = System.getenv("OPENSHIFT_MYSQL_DB_URL");
	private static final String DBNAME = "phonebook3";

	public static void main(String[] args) throws SQLException {
		String dsc = "java:comp/env/jdbc/address";
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME, USERNAME, PASSWORD);
/*		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(dsc);
			connection = dataSource.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}

}
