
public class Sbid {

	int bidid;
	int bidprice;
	int targetitemid;
	
	public Sbid(Sbid b)
	{
		this.bidid = b.bidid;
		this.bidprice = b.bidprice;
		this.targetitemid = b.targetitemid;
    }
	
	public Sbid (int bidid, int bidprice, int targetitemid)
	{
			this.bidid = bidid;
			this.bidprice = bidprice;
			this.targetitemid = targetitemid;
	}

	@Override
	public String toString()
	{
		return("Single bid "+this.bidid + " " + this.bidprice + " "+this.targetitemid + " ");
	}
	
	

}
