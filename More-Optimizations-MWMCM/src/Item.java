
public class Item implements Comparable <Item>{
	
	int itemid;
	int quality;
	int reserveprice;
	int startprice;
	int bidid;
	
	public Item (int itemid, int quality, int reserveprice, int startprice )
	{
		this.itemid = itemid;
		this.quality = quality;
		this.reserveprice = reserveprice;
		this.startprice = startprice;
		this.bidid = -1;
	}
		
	@Override
	public String toString()
	{
		return("Item "+this.itemid + " " + this.quality + " " + this.reserveprice + " " + this.startprice + " bidid "+this.bidid +" ");
	}
		
	@Override
	public int compareTo(Item other) {
		// TODO Auto-generated method stub
		return  this.quality < other.quality ? -1 : (this.quality > other.quality ? 1 : (this.itemid<other.itemid ? -1 : 1 ));
	}
	

}

