import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class ComputeMaxWeightMatch {

	private static long startTime = System.currentTimeMillis();
	public static void printoutput(ArrayList<Item> itemlist)
	{
		int maxweight = 0;
		for (Item item: itemlist)
		{   
			maxweight = maxweight + item.matchedbidprice;
		}
		
		System.out.print(maxweight);
		for (Item item: itemlist)
		{   
			System.out.print(" "+ item.matchedbidid );
			
		}
		
		System.out.println("\n");
		
	}
	
	public static void printitemlist(ArrayList<Item> itemlist)
	{
		System.out.println("Printing itemlist");
		for(Item item:itemlist)
		{
			System.out.println(item.itemid+" "+item.quality +" " + item.matchedbidid + " " + item.matchedbidprice +"\n");
		}
	}
	
	public static void printbidlist(ArrayList<Bid> bidlist)
	{
		System.out.println("Printing bidlist");
		for (Bid bid : bidlist)
		{
			System.out.println(bid.bidid +" " + bid.islinear + " "+ bid.bidprice + " " +bid.intercept+ " "+ bid.slope + " " + bid.targetitemid  + "\n");
			
		}	
		
	}
	
	public static void printoutput(HashMap <Integer, Integer> hashmap, int maxweightsum)
	{
		System.out.print(maxweightsum);
		for (int i = 0; i < hashmap.size() ; i++)
		{
		
			System.out.print(" " + hashmap.get(i));
			
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
		int quality, bidprice;
		int bidid = 0;
		int intercept,slope,targetitemid;
		int inputid = 0;
		int  maxweight = 0;
		int maxweightsum = 0;
		HashMap<Integer,Integer> hashmap = new HashMap<Integer,Integer>();
	    HashMap<Integer,Integer> tempmap = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> pricemap = new HashMap<Integer, Integer>(); 	
		
		for(int i=0; i< numbid ;i++)
		{
			quality = readinput.nextInt();
			bidprice = readinput.nextInt();
			itemlist.add(new Item(i,quality));
			bidlist.add(new Bid(false,-1,bidprice,0,0,i));
			Item matchitem = itemlist.get(i);
			Bid matchbid = bidlist.get(i);
			matchitem.assignbid(false, -1 , bidprice, 0, 0 );
			hashmap.put(i,-1);
			maxweightsum = maxweightsum + bidprice;
		}
		
		//printoutput(hashmap,maxweightsum);
		
		ArrayList <Bid> tempbidlist = new ArrayList <Bid>();
		ArrayList <Item> tempitemlist = new ArrayList <Item> ();
		//Collections.sort(itemlist);
	   
		int oldbidprice = 0;
		int oldbidid;
		int rpos = -1;
		boolean oldbidislinear = false;
		do
		{
			inputid = readinput.nextInt();
			if (inputid==1)
			{
				//System.out.println("Reading new single item bid");
				bidprice = readinput.nextInt();
				targetitemid = readinput.nextInt();
				oldbidprice = itemlist.get(targetitemid).matchedbidprice;
				oldbidid = itemlist.get(targetitemid).matchedbidid;
				rpos = -1;
				if(oldbidid != -1)
				{
					for (Bid test : bidlist)
					{
						if(test.bidid == oldbidid)
							oldbidislinear = test.islinear;
					}
				}
				else
					oldbidislinear = false;
				if(!oldbidislinear)
				{
				if(itemlist.get(targetitemid).assignbid(false, bidid, bidprice, 0, 0))
				{
					if(oldbidid == -1)
					{
						for(Bid rbid : bidlist)
						{
							if(rbid.bidid == -1 && rbid.targetitemid == targetitemid)
								{ 
								 rpos = bidlist.indexOf(rbid);
							//	 System.out.println("rpos "+rpos + " Bid lis size" + bidlist.size());
								}
						}
						    	
						if(rpos!=-1)
					    bidlist.remove(rpos);
						
				    
					}
					else
					{
						
						for(Bid rbid : bidlist)
				        if (rbid.bidid == oldbidid)
				        	{
				        	rpos = bidlist.indexOf(rbid);
						//    System.out.println("rpos "+rpos + " Bid lis size" + bidlist.size());
				        	}
				        	
					if(rpos!=-1)
						{
						bidlist.remove(rpos);
						}
					    
					}
				
				bidlist.add(new Bid(false, bidid, bidprice,0,0,targetitemid));
				//System.out.println("Added new bid " + bidid);
				maxweightsum = maxweightsum - oldbidprice + bidprice;
				hashmap.put(targetitemid, bidid);
		         		
				}
				}
			
				else //Repeated code
				{
					//System.out.println("edge cases");
					
				    Bid newbid = new Bid(false, bidid, bidprice, 0, 0, targetitemid);	
					
			        int tempsum = 0;	
					int config = -1;
					
					for (int v = 0; v < bidlist.size(); v++ )
					{
						tempitemlist.clear();
						tempitemlist.addAll(itemlist);
						tempbidlist.clear();
						//printitemlist(tempitemlist);
						tempmap.clear();
						pricemap.clear();
						int count = 0;
					    tempsum = 0;
						for (Bid bid : bidlist)
						{
							if (count == v)
							{
								tempmap.put(targetitemid, bidid );
								//pricemap.put(bidid, bidprice );
								tempsum = tempsum + bidprice;		
								//tempbidlist.add(newbid);
								pricemap.put(bidid, bidprice);
								 int pos = -1;
					                for (Item item : tempitemlist)
					                {
					                	if(item.itemid == targetitemid)
					                		pos = tempitemlist.indexOf(item);
					                }
					                if(pos != -1)
					                	tempitemlist.remove(pos);
								
							}
							else
							{
								if (bid.islinear)
								tempbidlist.add(bid);
								else
								{
					                tempsum = tempsum + bid.bidprice;
					      //          System.out.println(bid.targetitemid + " " + remitemcount);
					                tempmap.put(bid.targetitemid, bid.bidid);
					                int pos = -1;
					                for (Item item : tempitemlist)
					                {
					                	if(item.itemid == bid.targetitemid)
					                		pos = tempitemlist.indexOf(item);
					                }
					                if(pos != -1)
					                	tempitemlist.remove(pos);
								}
							}	
							count++;
						}
					
						Collections.sort(tempitemlist);
						Collections.sort(tempbidlist);
		//				System.out.println("printing tem bid list");
			//			printbidlist(tempbidlist);
						//System.out.println("Size check" + (tempitemlist.size()==tempbidlist.size() ));
						for (int k = 0; k < tempitemlist.size(); k++)
						{
							int weight = tempbidlist.get(k).intercept + (tempbidlist.get(k).slope*tempitemlist.get(k).quality);
						    tempsum = tempsum + weight;
						    tempmap.put(tempitemlist.get(k).itemid, tempbidlist.get(k).bidid);
						    pricemap.put(tempbidlist.get(k).bidid,weight );
						}
					//	System.out.println("Temporary output");
					//	printoutput(tempmap,tempmaxweightsum);
						
						if (tempsum > maxweightsum)
						{		
							maxweightsum = tempsum;
							config = v;
							//System.out.println("max weight sum " + maxweightsum);
							//System.out.println("Assigned config" + v);
							hashmap.clear();
							hashmap = (HashMap<Integer, Integer>) tempmap.clone();
							
						}
						
						
						
					}
					
					if(config != -1)
					{ 
					    //System.out.println("assigning final output");
						
						Iterator<Integer> keySetIterator = hashmap.keySet().iterator();

						while(keySetIterator.hasNext()){
						  Integer item = keySetIterator.next();
						  Integer bid = hashmap.get(item);
						  Integer price = pricemap.get(bid);
						 // System.out.println("Item "+item +"Bid "+ bid + "Price "+ price);
						  if(price != null)
						  itemlist.get(item).setbid(bid,price);
						
						
						}
						
					    					
						bidlist.remove(config);
						bidlist.add(newbid);
									
				}
				
				
			//printoutput(hashmap,maxweightsum);
			}
				bidid++;
			}
			else if (inputid==2)
			{
				//System.out.println("Reading new linear bid");
				intercept = readinput.nextInt();
				slope = readinput.nextInt();
				//bidlist.add(new Bid (true,bidid, 0, intercept,slope, 0));
				Bid newbid = new Bid(true,bidid, 0, intercept,slope, 0);
				//Collections.sort(bidlist);
				//printbidlist(bidlist);
			    int tempmaxweightsum = 0;
				
			   
				
				int config = -1;
				for (int v = 0; v < bidlist.size(); v++ )
				{
					tempitemlist.clear();
					tempitemlist.addAll(itemlist);
					tempbidlist.clear();
					//printitemlist(tempitemlist);
					tempmap.clear();
					pricemap.clear();
					int count = 0;
				    tempmaxweightsum = 0;
					for (Bid bid : bidlist)
					{
						if (count == v)
							tempbidlist.add(newbid);
						else
						{
							if (bid.islinear)
							tempbidlist.add(bid);
							else
							{
				                tempmaxweightsum = tempmaxweightsum + bid.bidprice;
				      //          System.out.println(bid.targetitemid + " " + remitemcount);
				                tempmap.put(bid.targetitemid, bid.bidid);
				                int pos = -1;
				    //            System.out.println("Bid id" + bid.bidid + " Item to be removed " +bid.targetitemid);
				                for (Item item : tempitemlist)
				                {
				                	if(item.itemid == bid.targetitemid)
				                		pos = tempitemlist.indexOf(item);
				                }
				                if(pos != -1)
				                	tempitemlist.remove(pos);
							}
						}	
						count++;
					}
					
					Collections.sort(tempitemlist);
					Collections.sort(tempbidlist);
	//				System.out.println("printing tem bid list");
		//			printbidlist(tempbidlist);
					
										
					for (int k = 0; k < tempitemlist.size(); k++)
					{
						int weight = tempbidlist.get(k).intercept + (tempbidlist.get(k).slope*tempitemlist.get(k).quality);
					    tempmaxweightsum = tempmaxweightsum + weight;
					    tempmap.put(tempitemlist.get(k).itemid, tempbidlist.get(k).bidid);
					    pricemap.put(tempbidlist.get(k).bidid,weight );
					}
				//	System.out.println("Temporary output");
				//	printoutput(tempmap,tempmaxweightsum);
					
					if (tempmaxweightsum > maxweightsum)
					{		
						maxweightsum = tempmaxweightsum;
						config = v;
						//System.out.println("max weight sum " + maxweightsum);
						//System.out.println("Assigned config" + v);
						hashmap.clear();
						hashmap = (HashMap<Integer, Integer>) tempmap.clone();
						
					}
					
					
					
				}
				
				if(config != -1)
				{ 
				    //System.out.println("assigning final output");
					
					Iterator<Integer> keySetIterator = hashmap.keySet().iterator();

					while(keySetIterator.hasNext()){
					  Integer item = keySetIterator.next();
					  Integer bid = hashmap.get(item);
					  Integer price = pricemap.get(bid);
					 // System.out.println("Item "+item +"Bid "+ bid + "Price "+ price);
					  if(price != null)
					  itemlist.get(item).setbid(bid,price);
					
					
					}
					
				    					
					bidlist.remove(config);
					bidlist.add(newbid);
				}
				
								
				bidid++;
				
			}	
			
			else if (inputid==3)
			{
			      printoutput(hashmap,maxweightsum);
			      //System.out.println("Run finsihed");
				
			}
			
		}while(readinput.hasNext());
		
		
		//printbidlist(bidlist);
		
		readinput.close();
		
		long endTime = System.currentTimeMillis();
       // System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}

}
