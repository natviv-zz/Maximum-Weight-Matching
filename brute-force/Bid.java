
import java.util.ArrayList;
import java.util.Collections;

public class Bid {

	public int bidid;
	
	public boolean islinear;
	public int singlebidamount;
	//Not required comes in arraylist
	public int targetitemid;
	public int slope;
	public int intercept;
	public ArrayList <ItemforBid> itempreferencelist = new ArrayList <ItemforBid>();
	public boolean isitemassigned;
	public int assigneditemid;
	//Not required instead strip elements from arraylist from head
	//public int preflistpointer;
	//public boolean islisttraversed;
	
	public Bid (int bidid, boolean islinear, int singlebidamount, int targetitemid, int targetreserveprice)
	{
		this.bidid = bidid;
		this.islinear = islinear;
		this.singlebidamount = singlebidamount;
		this.slope = 0;
		this.intercept = 0;
		this.isitemassigned = false;
		this.targetitemid=targetitemid;
		ItemforBid newitem = new ItemforBid(targetitemid,singlebidamount);
		//itempreferencelist.add(new ItemforBid(intercept,slope,item.itemid,item.quality));
	    if (newitem.bidprice>=targetreserveprice)
	    {
	    	itempreferencelist.add(newitem);
	    }
		this.assigneditemid = -1;
		
	}
	
	
	public Bid (int bidid, boolean islinear, int intercept, int slope, ArrayList<Item> itemlist)
	{
		this.bidid = bidid;
		this.islinear = islinear;
		this.singlebidamount = 0;
		this.slope = slope;
		this.intercept = intercept;
		this.isitemassigned = false;
		this.assigneditemid = -1;
		for (Item item:itemlist)
		{
			ItemforBid newitem = new ItemforBid(intercept,slope,item.itemid,item.quality,item.reserveprice);
			//itempreferencelist.add(new ItemforBid(intercept,slope,item.itemid,item.quality));
		    if (newitem.bidprice>=item.reserveprice)
		    {
		    	itempreferencelist.add(newitem);
		    }
		
		}
		Collections.sort(itempreferencelist);
		
	}
	
	public void assignitemtobid(int itemid)
	{
	   	this.isitemassigned = true;
	   	this.assigneditemid = itemid;
	   	
		
	}
	
	public void removefrompreflist()
	{
		this.itempreferencelist.remove(0);
		this.isitemassigned = false;
		this.assigneditemid = -1;
	}
	
	public void recomputepreflist(int bidid, boolean islinear, int intercept, int slope, ArrayList<Item> itemlist)
	{
		this.bidid = bidid;
		this.islinear = islinear;
		this.singlebidamount = 0;
		this.slope = slope;
		this.intercept = intercept;
		this.isitemassigned = false;
		this.assigneditemid = -1;
		for (Item item:itemlist)
		{
			ItemforBid newitem = new ItemforBid(intercept,slope,item.itemid,item.quality,item.reserveprice);
			//itempreferencelist.add(new ItemforBid(intercept,slope,item.itemid,item.quality));
		    if (newitem.bidprice>=item.reserveprice)
		    {
		    	itempreferencelist.add(newitem);
		    }
		
		}
		Collections.sort(itempreferencelist);
		
		
	}
	
	public void recomputepreflist(int bidid, boolean islinear, int singlebidamount, int targetitemid, int targetreserveprice)
	{
		this.bidid = bidid;
		this.islinear = islinear;
		this.singlebidamount = singlebidamount;
		this.slope = 0;
		this.intercept = 0;
		this.isitemassigned = false;
		
		ItemforBid newitem = new ItemforBid(targetitemid,singlebidamount);
		//itempreferencelist.add(new ItemforBid(intercept,slope,item.itemid,item.quality));
	    if (newitem.bidprice>=targetreserveprice)
	    {
	    	itempreferencelist.add(newitem);
	    }
		this.assigneditemid = -1;
		
		
		
	}
	
	
	
}
