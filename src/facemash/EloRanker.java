package facemash;

import java.sql.Connection;

public class EloRanker {	
	static int RATENUM = 5; 
	public static void main (String[] args) throws InterruptedException {
		DBConnector dbConnector = new DBConnector();
		Competitor[] competitorPair = new Competitor[2];
		Rater rater = new Rater();
		
		// Initialize the database connection
		System.out.println("Connecting EloRank database...");
		Connection dbConnection = dbConnector.connectDB();
		System.out.println("-----------------------");
		
		// Begin the rating by users
		System.out.println("Now the game begins!");
		
		for (int i = 0; i < RATENUM; i++) {
			competitorPair = rater.getCompetitorPair(dbConnection);	
			RateFrame rf = new RateFrame(competitorPair[0], competitorPair[1]);
			SwingConsole.run(rf, 750, 400);		  
		
			synchronized(EloRanker.class) {
				while(RateFrame.finished == false) {
					EloRanker.class.wait();
				}
			}
			rater.computeScore(competitorPair[0], competitorPair[1], RateFrame.result);
			dbConnector.writeRankpoints(dbConnection, competitorPair[0], competitorPair[1]);
			rf.dispose();	// remove the previous JFrame window
			System.out.format("You have rated %d competitors.\n", i + 1);		
		}
		dbConnector.updateRank(dbConnection);
		
		// Show rating results
			System.out.println("-----------------------");
			rater.getRank(dbConnection);
	}
}
