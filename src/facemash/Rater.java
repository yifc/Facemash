package facemash;

import java.sql.*;

public class Rater {	
	static int TOPN = 5;
	// construct a competitor pair
	Competitor[] getCompetitorPair(Connection conn) {
		DBConnector connector = new DBConnector();
		Competitor[] competitorPair = new Competitor[2];
		
		// fetch two different competitors
		Competitor compA = connector.getCompetitor(conn);
		Competitor compB = connector.getCompetitor(conn);
		while (compA.getId() == compB.getId())
			compB = connector.getCompetitor(conn);
		
		// add the competitors into an array to form a pair
		competitorPair[0] = compA;
		competitorPair[1] = compB;
		return competitorPair;
	}
	
	/**
	 * Compute the score of the two competitors according to the current rating
	 * @param compA competitor A
	 * @param compB competitor B
	 * @param result the rating of the user, 1 for compA and 2 for compB
	 */
	void computeScore(Competitor compA, Competitor compB, int result) {
		int sa, sb; // actual score of A and B in this round
		if(result == 1) {
			sa = 1;
			sb = 0;
		} else {
			sa = 0;
			sb = 1;
		}
		int K = 32;
		double ra = compA.getRankPoints();	// the current rank points of the competitors
		double rb = compB.getRankPoints();
		double ea = (double)1 / (1 + Math.pow(10, (rb -ra) / 400));	// the expected score of the competitors in this round
		double eb = (double)1 / (1 + Math.pow(10, (ra -rb) / 400));	
		compA.setRankPoints(ra + K * (sa - ea)); 		// the updated rank points after this round
		compB.setRankPoints(rb + K * (sb - eb));
	}
	
	void getRank(Connection conn) {
		Statement statement1;
		try {
			// sort the data by rank point
			statement1 = conn.createStatement();
			String sql1 = "SELECT * FROM elorank.girls ORDER BY rank";
			ResultSet rs = statement1.executeQuery(sql1);
			
			// output the TOPN-ranked item
			for (int i = 0; i < TOPN && rs.next() == true; i++) {
//				System.out.printf("The first %d competitors are:", TOPN); // TOPN < total item in the database
				System.out.println("Rank: " + rs.getInt("rank") + " Name: " + rs.getString("name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
