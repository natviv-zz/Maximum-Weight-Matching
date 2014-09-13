
public class Lbid implements Comparable<Lbid> {

	int bidid;
	int slope;
	int intercept;
	
	public Lbid(Lbid b)
	{
		this.bidid = b.bidid;
		this.slope = b.slope;
		this.intercept = b.intercept;		
	}
	
	public Lbid (int bidid, int intercept, int slope)
	{
		
			this.bidid = bidid;
			this.intercept = intercept;
			this.slope = slope;
	}
	
	@Override
	public String toString()
	{
		return("Linear bid "+this.bidid + " " + this.slope + " "+this.intercept + " ");
	}
	
	
	
	
	
	@Override
	public int compareTo(Lbid other) {
		// TODO Auto-generated method stub
		return  this.slope > other.slope ? 1 : (this.slope < other.slope ? -1 : 0);
	}

}
