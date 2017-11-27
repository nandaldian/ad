package serpis.ad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PruebaMySql {

	public static void main(String[] args) throws SQLException {
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/dbprueba",
				"root",
				"sistemas");
		
		java.sql.Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery( "select * from articulo" );
	    
		 while ( resultSet.next() ) {
		        int numColumns = resultSet.getMetaData().getColumnCount();
		        for ( int i = 1 ; i <= numColumns ; i++ ) {
		           System.out.println( "COLUMN " + i + " = " + resultSet.getObject(i) );
		        }
		    }
		 connection.close();
	}
}
	

		

