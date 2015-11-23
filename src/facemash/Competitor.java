package facemash;

class Competitor {
//	double eScore;	// the expected score of the competitor for a series of game
//	double aScore;	// the actual score of the competitor for a series of game
	private int id;				// to differentiate competitors with the same name
	private double rankPoints; 	// the accumulated points for ranking 
	private String name;		// the name of the competitor
	private String image;		// the image of the competitor
	
	Competitor(int id, String name, double initialRankPoints, String image) {
		this.id = id;
		this.name = name;
		this.rankPoints = initialRankPoints;
		this.image = image;
	}
	
	public int getId() {
		return id;
	}
	
	public double getRankPoints() {
		return rankPoints;
	}
	
	public void setRankPoints(double newPoints) {
		rankPoints = newPoints;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImageLink() {
		return image;
	}
}