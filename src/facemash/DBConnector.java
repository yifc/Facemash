package facemash;

import java.sql.*;

public class DBConnector {
	// connect to the EloRank database 
	public Connection connectDB() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/EloRank";
		String username = "root";
		String password = "";
		
		try {
			Class.forName(driver);	// load the driver class
			Connection conn = DriverManager.getConnection(url, username, password);
			if(!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			return conn;	
		} catch (ClassNotFoundException e) {   
			System.err.println("Error: Cannot find the Driver!");   
			e.printStackTrace();   
			return null;
		} catch (SQLException e) {
			System.err.println("Error: Cannot connect the database!");
			return null;
		} 
	}
	
	// get a competitor randomly from the database
	Competitor getCompetitor(Connection conn) {
		Statement statement;
		try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM elorank.girls ORDER BY RAND() LIMIT 1";
//					"where id= FLOOR(((select MAX(id) from girls) AS ) *rand())";
			ResultSet rs = statement.executeQuery(sql);
			
			rs.next(); // Moves the cursor forward one row to make the first row the current row
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String image = rs.getString("image");
			double rankPoint = rs.getDouble("rankpoint");
			
			Competitor comp = new Competitor(id, name, rankPoint, image);
			rs.close();
			return comp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
		
	// write the rank points back to the database
	void writeRankpoints (Connection conn, Competitor compA, Competitor compB) {
		Statement statement;
		try {
			statement = conn.createStatement();
			String sqlA = String.format("UPDATE elorank.girls SET rankpoint = %f WHERE id = %d", compA.getRankPoints(), compA.getId());
			String sqlB = String.format("UPDATE elorank.girls SET rankpoint = %f WHERE id = %d", compB.getRankPoints(), compB.getId());
			statement.executeUpdate(sqlA);
			statement.executeUpdate(sqlB);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// update the rank according to the rank points
	void updateRank(Connection conn) {
		Statement statement1;
		try {
			// sort the data by rank point
			statement1 = conn.createStatement();
			String sql1 = "SELECT rankpoint FROM elorank.girls ORDER BY rankpoint DESC";
			ResultSet rs = statement1.executeQuery(sql1);
			
			// set the rank according to the rank point
			int n = 0;
			Statement statement2;
			while(rs.next()) {
				n += 1;		
				statement2 = conn.createStatement();
				String sql2 = String.format("UPDATE elorank.girls SET rank = %d WHERE rankpoint = %s", n, rs.getString("rankpoint"));
				statement2.executeUpdate(sql2);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
}
