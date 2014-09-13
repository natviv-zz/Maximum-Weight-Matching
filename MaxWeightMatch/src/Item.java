
public class Item implements Comparable <Item>{
	
	int itemid;
	int quality;
	int matchedbidid;
	int matchedbidprice;
	boolean ismatched;
	
	public Item (int itemid, int quality)
	{
		this.itemid = itemid;
		this.quality = quality;
		this.ismatched = false;
	}
	
	public void setbid(int bidid, int bidprice)
	{
		this.matchedbidid=bidid;
		this.matchedbidprice = bidprice;
		this.ismatched = true;
	}
	
	
	
	public boolean assignbid(boolean islinear, int bidid, int bidprice, int intercept, int slope )
	{
		if(this.ismatched)
		{
			if (islinear) // is bid linear
			{
				int linearbidprice = intercept + (this.quality*slope);
				if (linearbidprice > this.matchedbidprice)
				{
					this.matchedbidid = bidid;
					this.matchedbidprice = linearbidprice;
					this.ismatched = true;
					return true;
				}
				else
					return false;
			}
			else // bid is not linear
			{
				if (bidprice > this.matchedbidprice)
				{
				//    System.out.println ("Assigned single item bid to matched itemid " + this.itemid + "  bidid "+ bidid + " bidprice " + bidprice);
					this.matchedbidid = bidid;
					this.matchedbidprice = bidprice;
					this.ismatched = true; 
					return true;
					
				}
				else
					return false;
				
			}
		}
		
		
		else // not already matched
		{
			if(islinear)
			{
				int linearbidprice = intercept + (this.quality*slope);
			    this.matchedbidid = bidid;
				this.matchedbidprice = linearbidprice;
				this.ismatched = true;
				return true;
				
			}
			else
			{
					this.matchedbidid = bidid;
					this.matchedbidprice = bidprice;
					this.ismatched = true;
					return true;
		
			}
			
		}
		
		
		
	}
	
	
	public boolean assignbid(Bid b )
	{
		if(this.ismatched)
		{
			if (b.islinear) // is bid linear
			{
				int linearbidprice = b.intercept + (this.quality*b.slope);
				if (linearbidprice > this.matchedbidprice)
				{
					this.matchedbidid = b.bidid;
					this.matchedbidprice = linearbidprice;
					this.ismatched = true;
					return true;
				}
				else
					return false;
			}
			else // bid is not linear
			{
				if (b.bidprice > this.matchedbidprice)
				{
					this.matchedbidid = b.bidid;
					this.matchedbidprice = b.bidprice;
					this.ismatched = true;
					return true;
					
				}
				else
					return false;
				
			}
		}
		
		
		else // not already matched
		{
			if(b.islinear)
			{
				int linearbidprice = b.intercept + (this.quality*b.slope);
			    this.matchedbidid = b.bidid;
				this.matchedbidprice = linearbidprice;
				return true;
				
			}
			else
			{
					this.matchedbidid = b.bidid;
					this.matchedbidprice = b.bidprice;
					return true;
		
			}
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public int compareTo(Item other) {
		// TODO Auto-generated method stub
		return  this.quality < other.quality ? 1 : (this.quality > other.quality ? -1 : 0);
	}
	

}
