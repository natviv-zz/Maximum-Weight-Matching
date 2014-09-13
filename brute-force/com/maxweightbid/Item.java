package com.maxweightbid;

import java.util.ArrayList;

import com.maxweightbid.Bid.ItemforBid;

public class Item implements Comparable<Item> {
	public int itemid;
	public int quality;
	public int reserveprice;
	public boolean acceptedbid;
	public int acceptedbidid;
	public int acceptedbidprice;
	
	public Item (int itemid, int quality , int reserveprice)
	{
		this.itemid=itemid;
		this.quality=quality;
		this.reserveprice = reserveprice;
		this.acceptedbid = false;
		this.acceptedbidid = -1;
		this.acceptedbidprice = reserveprice;
	}
	
	public int assignbidtoitem(int bidid , ArrayList<Bid> bidlist) throws IllegalArgumentException
	{   
		//int bidprice = this.reserveprice;
		int flag = 0;
		int bidflag = 0;
		for (Bid b : bidlist)
		{
			if(b.bidid==bidid)
			{    
	             for (ItemforBid item : b.itempreferencelist)
	             {
	           
	              if(this.itemid == item.id)
	              {   
	           
	        //     System.out.println("Assigned item to bid " + this.itemid +" : "+ bidid + "Bid Price "+ item.bidprice);
	            	  this.acceptedbidprice = item.bidprice;
	            	  this.acceptedbidid=bidid;
	            	  flag =1;
	            	  break;
	              } 
	             }
				 
	             
	             if(flag == 0)
	            	 throw new IllegalArgumentException();
		         bidflag=1;
			}
			
		}
		if(bidflag==0)
			throw new IllegalArgumentException();
		return this.acceptedbidprice;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean assignbid (int acceptedbidid, int acceptedbidprice, ArrayList <Bid> bidlist)
	{
	/*
		if(acceptedbidprice>this.acceptedbidprice)
		{
	    if(this.acceptedbid)
	    {
		bidlist.get(this.acceptedbidid).isitemassigned = false;
		System.out.println("Deassigned "+this.acceptedbidid + " from item" + itemid);
	    }
		this.acceptedbidid = acceptedbidid;
		this.acceptedbidprice = acceptedbidprice;
		this.acceptedbid = true;
        return true;
		}
		else
			return false;
      	
	*/  int var1=0;
	    int var2=0; // hold bid of next preferred items of current and new bids 
		if(this.acceptedbid)
		{
			try{
			var1 = bidlist.get(this.acceptedbidid).itempreferencelist.get(0).bidprice;
		   
			} catch(IndexOutOfBoundsException e)
			{
				var1=0;
			}
//			 System.out.println("Var 1 "+var1);
			
			try{
			var2 = bidlist.get(acceptedbidid).itempreferencelist.get(1).bidprice;
			} catch (IndexOutOfBoundsException e)
			{
				var2=0;
			}
	     //    System.out.println("New bid price "+acceptedbidprice+" Var 1 "+var1+" old bid price"+this.acceptedbidprice +" Var 2 " + var2);
			
			if((acceptedbidprice + var1) > (this.acceptedbidprice + var2))
			{
				bidlist.get(this.acceptedbidid).isitemassigned = false;
				System.out.println("Deassigned "+this.acceptedbidid + " from item in if1 " + itemid);
				this.acceptedbidid = acceptedbidid;
				this.acceptedbidprice = acceptedbidprice;
				this.acceptedbid = true;
				
				return true;
			}
			else
				return false;
			
		}
		else if(acceptedbidprice>this.acceptedbidprice)
		{
			this.acceptedbidid = acceptedbidid;
			this.acceptedbidprice = acceptedbidprice;
			this.acceptedbid = true;
			//System.out.println("Deassigned "+this.acceptedbidid + " from item in if2 " + itemid);
			
	        return true;
			
			
		}
		else
			return false;
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
	public void itemreset()
	{
		this.acceptedbid = false;
		this.acceptedbidid = -1;
		this.acceptedbidprice = this.reserveprice;
		
	}

	@Override
	public int compareTo(Item other) {
		return  this.quality < other.quality ? 1 : (this.quality > other.quality ? -1 : 0);
	}
	

}
