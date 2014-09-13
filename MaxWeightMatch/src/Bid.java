
public class Bid implements Comparable<Bid> {

	int bidid;
	int slope;
	int intercept;
	int bidprice;
	int targetitemid;
	boolean islinear;
	
	public Bid(Bid b)
	{
		this.bidid = b.bidid;
		this.slope = b.slope;
		this.intercept = b.intercept;
		this.bidprice = b.bidprice;
		this.targetitemid = b.targetitemid;
		this.islinear = b.islinear;
	}
	
	public Bid (boolean islinear, int bidid, int bidprice, int intercept, int slope, int targetitemid)
	{
		if(islinear)
		{
			this.bidid = bidid;
			this.intercept = intercept;
			this.slope = slope;
			this.islinear = islinear;
			this.bidprice = Integer.MIN_VALUE;
			this.targetitemid = Integer.MIN_VALUE;
		}
		
		else
		{
		    this.bidid = bidid;
		    this.bidprice = bidprice;
		    this.intercept = Integer.MAX_VALUE;
		    this.slope = Integer.MAX_VALUE;
		    this.islinear = islinear;
		    this.targetitemid = targetitemid;
		}
	}
	
	@Override
	public int compareTo(Bid other) {
		// TODO Auto-generated method stub
		return  this.slope < other.slope ? 1 : (this.slope > other.slope ? -1 : 0);
	}

}
