
public class Sbid implements Comparable<Sbid> {

	int bidid;
	int bidprice;
	int targetitemid;
	Integer targetitemquality;
	
	public Sbid(Sbid b)
	{
		this.bidid = b.bidid;
		this.bidprice = b.bidprice;
		this.targetitemid = b.targetitemid;
		this.targetitemquality = b.targetitemquality;
    }
	
	public Sbid (int bidid, int bidprice, int targetitemid, int targetitemquality)
	{
			this.bidid = bidid;
			this.bidprice = bidprice;
			this.targetitemid = targetitemid;
			this.targetitemquality = targetitemquality;
	}

	@Override
	public String toString()
	{
		return("Single bid "+this.bidid + " " + this.bidprice + " "+this.targetitemid + " "+this.targetitemquality + " ");
	}
	
	@Override
	public int compareTo(Sbid other) {
		// TODO Auto-generated method stub
		return  this.targetitemquality < other.targetitemquality ? 1 : (this.targetitemquality > other.targetitemquality ? -1 : 0);
	}

}
