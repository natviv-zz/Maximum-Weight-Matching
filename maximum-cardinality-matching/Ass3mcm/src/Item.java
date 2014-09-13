
public class Item implements Comparable <Item>{
	
	int itemid;
	int quality;
	
	public Item (int itemid, int quality)
	{
		this.itemid = itemid;
		this.quality = quality;
		
	}
		
	@Override
	public String toString()
	{
		return("Item "+this.itemid + " " + this.quality + " ");
	}
		
	@Override
	public int compareTo(Item other) {
		// TODO Auto-generated method stub
		return  this.quality < other.quality ? -1 : (this.quality > other.quality ? 1 : 0);
	}
	

}

