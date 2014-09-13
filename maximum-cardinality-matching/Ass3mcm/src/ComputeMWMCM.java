
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;



public class ComputeMWMCM {

	private static long startTime = System.currentTimeMillis();

	static class ValueComparator implements Comparator<Integer> {

	    Map<Integer, Sbid> base;
	    public ValueComparator(Map<Integer, Sbid> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(Integer a, Integer b) {
	    	
	    	if(a.equals(b))
	    	{
	    		return 0;
	    	}
	        else if (base.get(a).targetitemquality < base.get(b).targetitemquality) {
	            return -1;
	        } else if (base.get(a).targetitemquality > base.get(b).targetitemquality) {
	            return 1;
	        }
	        else
	        	return base.get(a).targetitemid - base.get(b).targetitemid;
	    	// returning 0 would merge keys
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void printitemlist(ArrayList<Item> itemlist)
	{
		System.out.println("Printing itemlist ");
		for(Item item:itemlist)
		{
			System.out.println(item);
		}
	}
	
	public static void printsbids(ArrayList<Sbid> bidlist)
	{
		System.out.println("Printing single bidlist");
		for (Sbid bid : bidlist)
		{
			System.out.println(bid);
			
		}	
		
	}
	
	public static void printlbidlist(ArrayList<Lbid> bidlist)
	{
		System.out.println("Printing linear bidlist");
		for (Lbid bid : bidlist)
		{
			System.out.println(bid);
			
		}	
		
	}
	
	public static void printsbidmap(TreeMap<Integer,Sbid> sbidmap)
	{
		System.out.println("Printing Map of Single bids");
		Iterator<Entry<Integer, Sbid>> it = sbidmap.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer,Sbid> entry = it.next();
			System.out.println(entry.getKey()+" "+entry.getValue());
			
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
	
	
		
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		int numbid;
		String filename = args[0];

        File file = new File(filename);
		Scanner readinput =new Scanner(file);
		numbid = readinput.nextInt();
		ArrayList <Item> itemlist = new ArrayList <Item> ();
		HashMap<Integer,Sbid> map = new HashMap<Integer,Sbid>();
        ValueComparator bvc =  new ValueComparator(map);
		TreeMap<Integer,Sbid> sbidmap = new TreeMap <Integer,Sbid> (bvc);
		ArrayList <Lbid> lbidlist = new ArrayList <Lbid> ();
	
		
		int quality, bidprice;
		int bidid = 0;
		int intercept,slope,targetitemid;
		int inputid = 0;
		int maxweightsum = 0;
		HashMap<Integer,Integer> hashmap = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> mapidtoindex = new HashMap<Integer, Integer>(); 	
		
		for(int i=0; i< numbid ;i++)
		{
			quality = readinput.nextInt();
			bidprice = readinput.nextInt();
			itemlist.add(new Item(i,quality));
			map.put(i,new Sbid(-1,bidprice,i,quality));
			hashmap.put(i,-1);
			maxweightsum = maxweightsum + bidprice;
		}
		
		sbidmap.putAll(map);
		Collections.sort(itemlist);
		int count = 0;
		for(Item item:itemlist)
		{
			mapidtoindex.put(item.itemid,count);
			count++;
		}
		
		
		
		
		
		do
		{
			inputid = readinput.nextInt();
			
			if(inputid==1)
			{
				//System.out.println("Reading new single bid");
				bidprice = readinput.nextInt();
				targetitemid = readinput.nextInt();
				if(sbidmap.containsKey(targetitemid))
				{
					if(sbidmap.get(targetitemid).bidprice<bidprice)
					{
						
						int targetitemquality = sbidmap.get(targetitemid).targetitemquality;
						maxweightsum = maxweightsum + bidprice - sbidmap.get(targetitemid).bidprice;
						sbidmap.remove(targetitemid);
						map.remove(targetitemid);
						map.put(targetitemid, new Sbid(bidid,bidprice,targetitemid,targetitemquality));
						sbidmap.put(targetitemid, new Sbid(bidid,bidprice,targetitemid,targetitemquality));
						hashmap.put(targetitemid, bidid);
					//	printoutput(hashmap,maxweightsum);
						//System.out.println("Hi");
						
						
					}
				}
				
				else
				{
				//	System.out.println("No matching single bid. Hence adding new");
					
					int targetitemquality = itemlist.get(mapidtoindex.get(targetitemid)).quality;
					map.put(targetitemid, new Sbid(bidid,bidprice,targetitemid,targetitemquality));
				    sbidmap.put(targetitemid, new Sbid(bidid,bidprice,targetitemid,targetitemquality));
				    
				    maxweightsum = computesingleweight (itemlist, lbidlist, sbidmap, mapidtoindex, hashmap, maxweightsum);	
				    maxweightsum = computelinearweight (itemlist, lbidlist, sbidmap, mapidtoindex, hashmap, maxweightsum);	
				//    printoutput(hashmap,maxweightsum);
				    Iterator<Entry<Integer, Sbid>> it3 = sbidmap.entrySet().iterator();
				    while(it3.hasNext())
					{
						
						Entry<Integer,Sbid> entry = it3.next();
						int bid = entry.getValue().bidid;
						int target = entry.getValue().targetitemid;
						if(hashmap.get(target)!=bid)
						{
						//	System.out.println("Removed sbid " + bid + "for item "+target);
							it3.remove();
						}
					}
				    
				    int pos = -1;
					for(int l= 0; l<lbidlist.size();l++)
					{
						Lbid bid = lbidlist.get(l);
						if(!hashmap.containsValue(bid.bidid))
							pos = l;
					}
					if(pos!=-1)
						lbidlist.remove(pos);
				    
				}
				
				
				
				bidid++;
			}
			
			if(inputid == 2)
			{
			 //  System.out.println("Reading new linear bid");
			   intercept = readinput.nextInt();
			   slope = readinput.nextInt();
			   Lbid newbid = new Lbid(bidid,intercept,slope);
			   insertlinearbid(lbidlist, newbid);
			  
			   maxweightsum = computesingleweight (itemlist, lbidlist, sbidmap, mapidtoindex, hashmap, maxweightsum);	
			   maxweightsum = computelinearweight (itemlist, lbidlist, sbidmap, mapidtoindex, hashmap, maxweightsum);	
			 // printoutput(hashmap,maxweightsum);
			   
			    Iterator<Entry<Integer, Sbid>> it3 = sbidmap.entrySet().iterator();
				while(it3.hasNext())
				{
					
					Entry<Integer,Sbid> entry = it3.next();
					
					int bid = entry.getValue().bidid;
					int target = entry.getValue().targetitemid;
					if(hashmap.get(target)!=bid)
					{
						//System.out.println("Removed sbid " + bid + "for item "+target);
					//	System.out.println("removed");
						it3.remove();
					}
				}
				int pos = -1;
				for(int l= 0; l<lbidlist.size();l++)
				{
					Lbid bid = lbidlist.get(l);
					if(!hashmap.containsValue(bid.bidid))
						pos = l;
				}
				if(pos!=-1)
					lbidlist.remove(pos);
				
				
						bidid++;
			}
			
						
			if (inputid==3)
			{
				//System.out.println("Printing output");
				printoutput(hashmap,maxweightsum);
			}
		}while(readinput.hasNext());
		
		readinput.close();
		long endTime = System.currentTimeMillis();
	       System.out.println("It took " + (endTime - startTime) + " milliseconds");
		//printitemlist(itemlist);
		/*Item item = new Item(4,5);
		insertitem(itemlist,item);
		item = new Item(3,5);
		insertitem(itemlist,item);
		item = new Item(7,30);
		insertitem(itemlist,item);
		item = new Item(5,30);
		insertitem(itemlist,item);
		printitemlist(itemlist);*/
		/*System.out.println("unsorted: "+map);
		
		printsbidmap(sbidmap);
		//System.out.println("sorted: "+sbidmap);
		printlbidlist(lbidlist);*/
		//printoutput(hashmap,maxweightsum);
			
	}
	
	public static void insertlinearbid(ArrayList<Lbid> lbidlist, Lbid newbid)
	{
		int k = 0;
		int slope = newbid.slope;
	    int lbidindex = 1;
		//Insertion into lbidlist max O(n)
	    if(lbidlist.size()==1)
	    {
	    	Lbid firstitem = lbidlist.get(0);
	    	if(firstitem.slope > slope)
	    		lbidlist.add(0,newbid);
	    	else if(firstitem.slope == slope && firstitem.bidid > newbid.bidid)
	    		lbidlist.add(0,newbid);
	    	else
	    		lbidlist.add(newbid);
	    	
	    }
	    else
	    {
		for (int i = 0; i < lbidlist.size()-1; i++)
		{
			if(lbidlist.get(i).slope<=slope && lbidlist.get(i+1).slope>slope)
			{
				//System.out.println("came here 1");
				lbidindex = i+1;
				k = 1;
				break;
				
			}
			
			else if(lbidlist.get(i).slope > slope)
			{
				//System.out.println("came here 1");
				lbidindex = i;
				k = 1;
				break;
				
			}
			
			else if (lbidlist.get(i+1).slope == slope && lbidlist.get(i).slope == slope )
			{
			//System.out.println("came here 2");
			lbidindex = i+2;
			k = 1;
			break;
			}
			
			
		}
		if(k==1)
		{
			lbidlist.add(lbidindex,newbid);
		//	System.out.println("Inserted new bid "+newbid.bidid + " " + newbid.slope+" at "+lbidindex);
		}
		else
			{
			lbidlist.add(newbid);
		//	System.out.println("Inserted new bid "+newbid.bidid +" "+ newbid.slope+" at "+lbidindex);
			}
	    }
	}
	
	
	public static void insertitem(ArrayList<Item> tempitemlist, Item item)
	{
		int k = 0;
	    int lbidindex = 1;
	    int quality = item.quality;
		//Insertion into lbidlist max O(n)
	    if(tempitemlist.size()==1)
	    {
	    
	    	Item firstitem = tempitemlist.get(0);
	    	if(firstitem.quality > quality)
	    		tempitemlist.add(0,item);
	    	else if(firstitem.quality == quality && firstitem.itemid > item.itemid)
	    		tempitemlist.add(0,item);
	    	else
	    		tempitemlist.add(item);
	    }
	    
	    else
	    {
	    
	    //System.out.println("Inserting "+item.itemid + " quality "+ quality);
		for (int i = 0; i < tempitemlist.size()-1; i++)
		{
			
			if(tempitemlist.get(i).quality<quality && tempitemlist.get(i+1).quality>quality)
			{
				//System.out.println("came here 1");
				lbidindex = i+1;
				k = 1;
				break;
				
			}
			
			else if(tempitemlist.get(i).quality > quality)
			{
				//System.out.println("came here 1");
				lbidindex = i;
				k = 1;
				break;
				
			}
			
			else if (tempitemlist.get(i+1).quality == quality && tempitemlist.get(i).quality == quality )
			{
			//System.out.println("came here 2");
				int id1 = tempitemlist.get(i).itemid;
				int id2 = tempitemlist.get(i+1).itemid;
				int id = item.itemid;
				if(id<id1 && id < id2)
					lbidindex =i;
				else if(id>id1 && id< id2)
					lbidindex = i+1;
				else
					lbidindex = i+2;
				k = 1;
				break;
			}
			
			else if (tempitemlist.get(i).quality == quality )
			{
				int id1 = tempitemlist.get(i).itemid;
				int id = item.itemid;
				if(id<id1)
					lbidindex =i;
				else
					lbidindex = i+1;
				k = 1;
				break;
			}
			
			
			
		}
		if(k==1)
		{
			//System.out.println("at pos "+lbidindex);
			tempitemlist.add(lbidindex,item);
		}
		else
		{
			//System.out.println("at pos end list");
			
			tempitemlist.add(item);
		}
	    }
	}
	
	public static int computelinearweight (ArrayList<Item> itemlist, ArrayList<Lbid> lbidlist, TreeMap<Integer,Sbid> sbidmap, HashMap<Integer,Integer> mapidtoindex, HashMap<Integer,Integer> hashmap, int maxweightsum)
	{
		//printlbidlist(lbidlist);
		//printitemlist(itemlist);
		ArrayList <Item> tempitemlist = new ArrayList <Item> ();
		for (Item item:itemlist)
		{
			if(!sbidmap.containsKey(item.itemid))
			{
				insertitem(tempitemlist,item);
			}
		}
		
		int singlebidsum=0,linearbidsum = 0;
		int tempweight = 0;
		int config = -1;
		Iterator<Entry<Integer, Sbid>> it = sbidmap.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer,Sbid> entry = it.next();
			singlebidsum = singlebidsum + entry.getValue().bidprice;
			
		}	
		if(lbidlist.size()>1 && (lbidlist.size()==tempitemlist.size()+1))
		{
			for (int i = lbidlist.size()-2; i>=0; i--)
			{
				Lbid lbid = lbidlist.get(i);
				Item item = tempitemlist.get(i);
				linearbidsum = linearbidsum + lbid.intercept + (lbid.slope*item.quality); 
			}
			tempweight = singlebidsum + linearbidsum;
			if(tempweight>maxweightsum)
				{
				maxweightsum = tempweight;
				config = lbidlist.size()-1;
				/*System.out.println("Config lbid "+config);
				System.out.println("Tempsum "+tempweight);
				System.out.println("Linearbidsum "+linearbidsum);
				System.out.println("singlebidsum "+singlebidsum);*/
				}
			
			for (int i = lbidlist.size()-1; i>0; i--)
			{
				Lbid newbid = lbidlist.get(i);
				Lbid oldbid = lbidlist.get(i-1);
				Item item = tempitemlist.get(i-1);
				int newweight = newbid.intercept + (newbid.slope*item.quality);
				int oldweight = oldbid.intercept + (oldbid.slope*item.quality);
				linearbidsum = linearbidsum + newweight - oldweight;
				tempweight = singlebidsum + linearbidsum;
				if(tempweight>maxweightsum)
					{
					maxweightsum = tempweight;
					config = i-1;
					/*System.out.println("Config lbid "+config);
					System.out.println("Tempsum "+tempweight);
					System.out.println("Linearbidsum "+linearbidsum);
					System.out.println("singlebidsum "+singlebidsum);*/
					}
				
			}
			
				
		
		}
		else if (lbidlist.size() ==1)
			{
			 //System.out.println("Nothing to remove");
			
			}
		else
		{
			System.out.println("Identity permutation exception lbid");
			/*System.out.println("l bid list size" + lbidlist.size());
			System.out.println("temp item list size" + tempitemlist.size());*/
			
		}
		
		if(config != -1)
		{
		//System.out.println("New config found  "+config + " Updating hashmap");
		Iterator<Entry<Integer, Sbid>> it1 = sbidmap.entrySet().iterator();
		while(it1.hasNext())
		{
			Entry<Integer,Sbid> entry = it1.next();
			hashmap.put(entry.getValue().targetitemid, entry.getValue().bidid);
			
		}	
		
		for (int i =0,j = 0; i < lbidlist.size() ; i++)
		{
			if(i != config)
			{
				hashmap.put(tempitemlist.get(j).itemid,lbidlist.get(i).bidid);
				j++;
			}
			
			
		}
		//System.out.println("Size befor removal " + lbidlist.size());
		lbidlist.remove(config);
		//System.out.println("Size after removal " + lbidlist.size());
		}
		
		
		
		return maxweightsum;
	}
	
	public static int computesingleweight (ArrayList<Item> itemlist, ArrayList<Lbid> lbidlist, TreeMap<Integer,Sbid> sbidmap, HashMap<Integer,Integer> mapidtoindex, HashMap<Integer,Integer> hashmap, int maxweightsum)
	{
		//System.out.println("Called computesingle weight");
		//printlbidlist(lbidlist);
		
		ArrayList <Item> tempitemlist = new ArrayList <Item> ();
		for (Item item:itemlist)
		{
			if(!sbidmap.containsKey(item.itemid))
			{
				insertitem(tempitemlist,item);
			//	System.out.println("Inserting from sbidmap "+item.itemid + " "+sbidmap.get(item.itemid));
			}
		}
		//printsbidmap(sbidmap);
		//printitemlist(tempitemlist);
		int singlebidsum=0,linearbidsum = 0;
		int tempweight = 0;
		int config = -1;
		Iterator<Entry<Integer, Sbid>> it = sbidmap.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer,Sbid> entry = it.next();
			singlebidsum = singlebidsum + entry.getValue().bidprice;
			
		}	
		//Removal of single bid
		Iterator<Entry<Integer, Sbid>> it2 = sbidmap.entrySet().iterator();
		while(it2.hasNext())
		{
			Entry<Integer,Sbid> entry = it2.next();
			int itemid = entry.getKey();
			insertitem(tempitemlist,itemlist.get(mapidtoindex.get(itemid)));
			//System.out.println("Inserted item id " + itemid);
			singlebidsum = singlebidsum - entry.getValue().bidprice;
			
			if(lbidlist.size()<=tempitemlist.size())
			{
				linearbidsum = 0;
				for (int i = lbidlist.size()-1; i>=0; i--)
				{
					Lbid lbid = lbidlist.get(i);
					Item item = tempitemlist.get(i);
					linearbidsum = linearbidsum + lbid.intercept + (lbid.slope*item.quality); 
				//	System.out.println(item.itemid + "temp itemlist size "+tempitemlist.size());
				}
				
				tempweight = singlebidsum + linearbidsum;
				if(tempweight>maxweightsum)
				{
			//		printlbidlist(lbidlist);
			//		printitemlist(tempitemlist);
				//	System.out.println("Tempweight " +tempweight );
					maxweightsum = tempweight;
					config = itemid;
					/*System.out.println("Config sbid "+config);
					System.out.println("Tempsum "+tempweight);
					System.out.println("Linearbidsum "+linearbidsum);
					System.out.println("singlebidsum "+singlebidsum);*/
				}
				
			}
			
			else
		
				{
				
				
				System.out.println("Identity permutation exception sbid");
				System.out.println("l bid list size" + lbidlist.size());
				System.out.println("temp item list size" + tempitemlist.size());
				}
			
			
			Item toremove = itemlist.get(mapidtoindex.get(itemid));
			//System.out.println("Removed item "+toremove.itemid);
			tempitemlist.remove(toremove);
			singlebidsum = singlebidsum + entry.getValue().bidprice;
		}	
		
		
		if(config !=-1)
		{
			Iterator<Entry<Integer, Sbid>> it3 = sbidmap.entrySet().iterator();
			while(it3.hasNext())
			{
				
				Entry<Integer,Sbid> entry = it3.next();
				if(config != entry.getKey())
				hashmap.put(entry.getKey(),entry.getValue().bidid );
				else
				{
					int itemid = entry.getKey();
					insertitem(tempitemlist,itemlist.get(mapidtoindex.get(itemid)));
					/*System.out.println(sbidmap.size() + " before removal");
					it3.remove();*/
				//	System.out.println(sbidmap.size() + " after removal");
				}
				
			}	
			
			if(lbidlist.size()<=tempitemlist.size())
			{
				for (int i = lbidlist.size()-1; i>=0; i--)
				{
					Lbid lbid = lbidlist.get(i);
					Item item = tempitemlist.get(i);
					hashmap.put(item.itemid, lbid.bidid); 
				}
				
				
			}
			
			else
				System.out.println("Identity permutation exception in config");
			
			
		}
		
		
		
		
		
		return maxweightsum;
	}
	
}
