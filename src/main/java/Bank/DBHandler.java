package Bank;
import Bank.*;
import java.sql.*;

public class DBHandler {
	Connection con;
	
	// For using PostgreSQL  Database
	/*String url="jdbc:postgresql://localhost:5432/Bank";
	String uname="postgres";
	String pass="#Bigguy1";*/
	
	
	// For using MySQL Database
	String url="jdbc:mysql://localhost:3306/bank";
	String uname="Bandya308";
	String pass="#Bigguy1";
	
	
	DBHandler() throws Exception{
		
		//For Using MySQL  Database
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//Loading and Registering Driver
	//	Class.forName("org.postgresql.Driver");
		this.con=DriverManager.getConnection(url, uname, pass);
		
		
	}
	
	
	

	/* Steps for creating JDBC Connection
	 1:Import Packages
	 2:Load Driver
	 3:Register Driver
	 4: Create Connection
	 5:Create Statement
	 6:Excecute Statement
	 7:Close 
	   */
	
	

	
}
