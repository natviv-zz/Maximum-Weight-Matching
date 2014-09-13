package com.maxweightbid;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.common.collect.Lists;
import com.maxweightbid.Bid.ItemforBid;
public class MaxWeightCompute {

	private static long startTime = System.currentTimeMillis();
	public static void printitemlist(ArrayList<Item> itemlist)
	{
		for(Item item:itemlist)
		{
			System.out.println(item.itemid+" "+item.quality +" " + item.reserveprice +"\n");
		}
	}
	
	public static void printbidlist(ArrayList<Bid> bidlist)
	{
		for (Bid bid : bidlist)
		{
			System.out.println(bid.bidid +" " + bid.singlebidamount + " " +bid.intercept+ " "+ bid.slope+"\n");
			for (ItemforBid item:bid.itempreferencelist)
				System.out.println( item.id + " " + item.bidprice +" \n");
			    System.out.println("Is empty " + bid.itempreferencelist.isEmpty());
		}	
		
	}
	
	public static void auction(ArrayList<Item> itemlist, ArrayList<Bid> inputbidlist)
	{
		ArrayList <Bid> bidlist = new ArrayList <Bid>(inputbidlist); 
		
    //System.out.println("Entering auction "+ !bidlist.isEmpty());
    while(!bidlist.isEmpty())
	 {
		Iterator<Bid> iterator = bidlist.iterator();
		while(iterator.hasNext())
		{
			  Bid b = iterator.next();  
		      if(b.itempreferencelist.isEmpty())
		      {
		//          System.out.println("Removed bid id "+b.bidid);
		    	  iterator.remove();
		      }
		      else
		      {
		      if(!b.isitemassigned) 	  
		      {   
		    	  if(itemlist.get(b.itempreferencelist.get(0).id).assignbid(b.bidid,b.itempreferencelist.get(0).bidprice, bidlist))
		          {
		             b.assignitemtobid(b.itempreferencelist.get(0).id);
		  // 	     System.out.println("Assigned bid for bidid "+b.bidid+" to room "+ b.itempreferencelist.get(0).id);
		      
		          }
		      }
		         b.itempreferencelist.remove(0);
		      
		      }
		  
		}
	  }
         
         int maxweightsum = 0;
         for (Item item:itemlist)
         {
        	maxweightsum+=item.acceptedbidprice;
        	//System.out.print(item.acceptedbidid+" "); 
        	 
         }
         System.out.print(maxweightsum+" ");
         
         for (Item item:itemlist)
         {
        	
        	System.out.print(item.acceptedbidid+" "); 
        	 
         }
    
		 System.out.print("\n");
	}
	
	
	public static void BruteAuction(ArrayList<Item> itemlist, ArrayList<Bid> inputbidlist)
	{
		
		ArrayList <Bid> bidlist = new ArrayList <Bid>(inputbidlist); 
		ArrayList<Integer> reslist = new ArrayList<Integer>(Collections.nCopies(itemlist.size(), -1));
		
		ArrayList<Integer> templist = new ArrayList<Integer>(Collections.nCopies(itemlist.size(), -1));
		
		
		Collection<List<Integer>> permlist = Lists.newArrayList();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		List<Integer> f = Lists.newArrayList();
		int bidpos = 0; 
		for(Bid b:bidlist)
		 {
			 f.add(b.bidid);
			 ++bidpos;
			 //System.out.println("added "+b.bidid);
		 }
		    if(bidpos<itemlist.size())
		    {
		    	for(int i = bidpos; i < itemlist.size();i++)
		    		f.add(-1);
		    }
		
		    Permutations<Integer> g = new Permutations<Integer>();
            permlist = g.permutations( f, itemlist.size() );
        //    System.out.println("Permlist size "+permlist.size());
		 int maxweight =0;
		 for(Item item:itemlist)
		 {
			 maxweight+=item.reserveprice;
			 
		 }
		 		 
		 for (List<Integer> perm: permlist)
		 {
			 int itemid =0;
			 int sum = 0;
			 int flag = 0;
		
			 for(int bidid :perm)
			 {
		
				 Item i = itemlist.get(itemid);
				 
				
				 
				 try {
					 
					 sum+=i.assignbidtoitem(bidid,bidlist);
				//	 System.out.println("Sum "+sum);
					 templist.set(itemid,bidid);
					 ++itemid;
				 }
				  catch(IllegalArgumentException e)
				  {   
					//  System.out.println("Bid Failed");
					  sum+=i.reserveprice;
					  templist.set(itemid,-1);
					  ++itemid;
				  }
				 
				 
			 }
			
			
		
				 if(sum>maxweight)
				 {   Collections.copy(reslist,templist);
					 maxweight = sum;
				 }
		
			  
			 }
		 
        System.out.print(maxweight+" ");
         
         for (Integer bidid:reslist)
         {
        	
        	System.out.print(bidid+" "); 
            	 
         }
    
		 System.out.print("\n");
		 
		 
		 
		 
		 
	}
		
		
		
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException
	{
		int numbid;
		String filename = args[0];

        File file = new File(filename);
		Scanner readinput =new Scanner(file);
		numbid = readinput.nextInt();
		ArrayList <Item> itemlist = new ArrayList <Item> ();
		ArrayList <Bid> bidlist = new ArrayList <Bid> ();
		int quality, reserveprice;
		int bidid = 0;
		int singlebidamount,intercept,slope,targetitemid;
		int inputid = 0;
		int targetreserveprice=0;
		for(int i=0; i< numbid ;i++)
		{
			quality = readinput.nextInt();
			reserveprice = readinput.nextInt();
			itemlist.add(new Item(i,quality,reserveprice));
			
			
		}
		//Collections.sort(itemlist);
		//printitemlist(itemlist);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		do
		{
			inputid = readinput.nextInt();
			if (inputid==1)
			{
				singlebidamount = readinput.nextInt();
				targetitemid = readinput.nextInt();
				for (Item item:itemlist)
				{
					if(item.itemid==targetitemid)
					{
						targetreserveprice=item.reserveprice;
					}
				}
				
				
				bidlist.add(new Bid(bidid,false,singlebidamount,targetitemid,targetreserveprice));
				bidid++;
				
				
				
			}
			else if (inputid==2)
			{
				intercept = readinput.nextInt();
				slope = readinput.nextInt();
				bidlist.add(new Bid(bidid,true,intercept,slope,itemlist));
				bidid++;
				
			}	
			
			else if (inputid==3)
			{
			//    printbidlist(bidlist);	
			    
//			    auction(itemlist,bidlist);
				//bidid = 0;
				//bidlist.clear();
		
				BruteAuction(itemlist,bidlist);
				
				
				for(Bid b:bidlist)
			    	
			    {
			    	if(b.islinear)
			    	{
			    	b.recomputepreflist(b.bidid,b.islinear,b.intercept,b.slope,itemlist);
			    	
			    	}
			    	else
			    	{
			    		targetitemid = b.targetitemid;
			  //  		System.out.println(b.bidid + "Bid targets room" +b.targetitemid);
						for (Item item:itemlist)
						{
							if(item.itemid==targetitemid)
							{
								targetreserveprice=item.reserveprice;
							}
						}	
			    		
			    		
			    	b.recomputepreflist(b.bidid, b.islinear, b.singlebidamount, b.targetitemid,targetreserveprice);	
			    	}
			    	
			    }
			    
			    
			  //  System.out.println("Size of bidlist " + bidlist.size());
			    for (Item item:itemlist)
			    {
			    	item.itemreset();
			    }
			//	System.out.println("Run Complete\n");
				
				
			}
			
		}while(readinput.hasNext());
		
		
		
		
		readinput.close();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		long endTime = System.currentTimeMillis();
        //System.out.println("It took " + (endTime - startTime) + " milliseconds");
		
		}
	
	
}
	
	
	
	

