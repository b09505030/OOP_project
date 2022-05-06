import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//path jdbcUrl should be changed 
//path jdbcUrl should be changed 
//path jdbcUrl should be changed 
//path jdbcUrl should be changed 
//path jdbcUrl should be changed 
//path jdbcUrl should be changed 
public class Test {

	public static void main(String[] args) {
		//path jdbcUrl should be changed 
		String  jdbcUrl = "jdbc:sqlite:/"+"D:\\esoeoop\\repo\\uber esoet\\Client"+"\\clientdb.db";
		Client MyAccount = new Client("Billy","b09505028","b09505028","0988233936","b09505028@gmail.com");

		System.out.println(MyAccount);
		MyAccount.MonthVIP();
		System.out.println(MyAccount);
		MyAccount.storeToDB(jdbcUrl);
		getDB(jdbcUrl);
		

	}


	//print all data in SQLite
	private static void getDB(String jdbcUrl) {
		//String  jdbcUrl = "jdbc:sqlite:/D:\\esoeoop\\repo\\uber esoet\\Client\\clientdb.db";
		try 
		{
			Connection connection = DriverManager.getConnection(jdbcUrl);
			String sql = "SELECT * FROM client";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				String Name = result.getString("Name");
				String Id = result.getString("ID");
				String Email = result.getString("Email");
				String Password = result.getString("Password");
				String Phone = result.getString("Phone");
				boolean VIP = result.getBoolean("VIP");
				String Due = result.getString("Due");
				//Date Due = result.getDate("Due");
				System.out.println("Client [Name=" + Name + 
						", Id=" + Id + 
						", Password=" + Password + 
						", Phone=" + Phone + 
						", Email=" + Email + 
						", VIP=" + VIP  + 
						", Due=" + Due +"]"
						) ;
				}
			connection.close();
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			System.out.println("Error connecting to SQLite database");
			e.printStackTrace();
		}
		
		
	}

}
