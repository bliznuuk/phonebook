package resound.phonebook;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class testSql {

	public static void main(String[] args) {
		String dsc = "java:comp/env/jdbc/address";
		Connection connection;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(dsc);
			connection = dataSource.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
