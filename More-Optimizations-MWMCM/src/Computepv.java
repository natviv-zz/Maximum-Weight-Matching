import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Computepv {
	
	
	public static void printoutput(LinkedHashMap <Integer, Integer> pricevector)
	{
		//System.out.println("Printing pricevector");
		Iterator<Entry<Integer, Integer>> it = pricevector.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer,Integer> entry = it.next();
			System.out.print(entry.getValue() + " ");
			
		}	
		System.out.println();
	}
	
	public static void printmwmcm(HashMap <Integer, Integer> mwmcm)
	{
	//	System.out.println("Printing mwmcm");
		Iterator<Entry<Integer, Integer>> it = mwmcm.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer,Integer> entry = it.next();
			System.out.print(entry.getValue() + " ");
			
			
		}	
		
		System.out.println();
	}
	
	public static void printitemlist (ArrayList <Item> itemlist)
	{
		for (int i=0;i<itemlist.size();i++)
			{
			  Item item = itemlist.get(i);
			  System.out.println(item.itemid + " " + item.quality);
			  
			}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException
	{
		int numbid;
		String filename = args[0];

        File file = new File(filename);
		Scanner readinput =new Scanner(file);
		numbid = readinput.nextInt();
		
		ArrayList <Item> itemlist = new ArrayList <Item> ();
		ArrayList<Sbid> sbidlist = new ArrayList<Sbid>();
        
		ArrayList <Lbid> lbidlist = new ArrayList <Lbid> ();
        
		HashMap <Integer,Lbid> lbidmap = new HashMap <Integer,Lbid> ();
		
		LinkedHashMap <Integer, Integer> pricevector = new LinkedHashMap<Integer, Integer> ();
        HashMap <Integer, Integer> mwmcm = new HashMap<Integer, Integer> ();
        HashMap <Integer, Integer> mwmcmprev = new HashMap<Integer,Integer> ();
        
        HashMap <Integer, Integer> itemsortmap = new HashMap<Integer, Integer> ();
        
        int quality, bidprice, startprice;
		int bidid = 0;
		int intercept,slope,targetitemid;
		int inputid = 0;
		int globid = 0;
        
		for(int i=0; i< numbid ;i++)
		{
			quality = readinput.nextInt();
			bidprice = readinput.nextInt();
			startprice = readinput.nextInt();
			itemlist.add(new Item(i,quality, bidprice, startprice));
			pricevector.put(i, startprice);
			mwmcm.put(i,-1);
			//System.out.println(i+" "+startprice);
		}
        
        Collections.sort(itemlist);
        for (int i =0;i<itemlist.size();i++)
        {
        	itemsortmap.put(itemlist.get(i).itemid, i);
        }
       // printitemlist(itemlist);
		do
		{
			inputid = readinput.nextInt();
			
			if(inputid==1)
			{
				//System.out.println("entered here id 1");
				bidprice = readinput.nextInt();
				targetitemid = readinput.nextInt();
				sbidlist.add(new Sbid(globid,bidprice,targetitemid));
				globid++;
				//System.out.println("Printing mwmcm");
				mwmcmprev.clear();
				mwmcmprev.putAll(mwmcm);
				for(int i=0; i < numbid ;i++)
				{
					bidid = readinput.nextInt();
					mwmcm.put(i, bidid);
					itemlist.get(itemsortmap.get(i)).bidid = bidid;
				}
		//		printmwmcm(mwmcm);
		//		printmwmcm(mwmcmprev);
				
				
			}
			
			if (inputid == 2)
			{
				//System.out.println("entered here id 2");
				intercept = readinput.nextInt();
				slope = readinput.nextInt();
				lbidlist.add(new Lbid(globid,intercept,slope));
				lbidmap.put(globid,new Lbid(globid,intercept,slope));
				globid++;
				mwmcmprev.clear();
				mwmcmprev.putAll(mwmcm);
				for(int i=0; i< numbid ;i++)
				{
					bidid = readinput.nextInt();
					mwmcm.put(i,bidid);
					itemlist.get(itemsortmap.get(i)).bidid = bidid;
				}
				
			//	printmwmcm(mwmcm);
			//	printmwmcm(mwmcm);
			//	printmwmcm(mwmcmprev);
				
			}
			
			if(inputid == 3)
			{
				//System.out.println("Entered 3");
				//printoutput(pricevector);
				
					//System.out.println("New iteration change false");
				// Stability criteria 3 - check for unmatched bid
					
					
				//Search for unmatched bid
					int key = -1;
					int bidlistid = 0;
					Sbid sbid = null;
					Lbid lbid = null;
					for (int j = 0; j < sbidlist.size(); j++)
					{
						sbid = sbidlist.get(j);
						if (!mwmcm.containsValue(sbid.bidid))
							{
							 key = j;
							 bidlistid = 1;
						//	 System.out.println("Unmatched sbid "+sbid.bidid);
							 break;
							}
						
						
					}
					
					for (int j = 0; j < lbidlist.size(); j++)
					{
						lbid = lbidlist.get(j);
						if (!mwmcm.containsValue(lbid.bidid))
							{
							 key = j;
							 bidlistid = 2;
						//	 System.out.println("Unmatched lbid "+lbid.bidid);
							 break;
							}
					}
					    
					
					
					
					
					
				for (int i = 0; i < itemlist.size(); i++)
				{
					
					Item curitem = itemlist.get(i);
					//System.out.println(curitem.itemid + " "+" "+curitem.reserveprice+" "+curitem.bidid+" "+pricevector.get(i));
					if (curitem.bidid != -1 && curitem.reserveprice > pricevector.get(curitem.itemid))
						{
						
						//System.out.println("change1" + " " + curitem.itemid + " " + curitem.reserveprice );
						pricevector.put(curitem.itemid, curitem.reserveprice);
						}
					
					if (key != -1)
					{
						if (bidlistid == 1 && sbid != null)
						{
							if(sbid.targetitemid == curitem.itemid)
							{
								if (sbid.bidprice > pricevector.get(curitem.itemid))
								{
								
									pricevector.put(curitem.itemid, sbid.bidprice);
									
								}
							}
							
							
						}
						
						if (bidlistid == 2 && lbid != null)
						{
							int value = lbid.intercept + lbid.slope*curitem.quality;
							{
								if (value > pricevector.get(curitem.itemid))
								{
								
									pricevector.put(curitem.itemid, value);
									
								}
							}
						}
						
						
						
					}
					
					
				}
				
				if (key != -1 && bidlistid == 2 && lbid != null)
				{
				//	System.out.println("Removing lbid" + lbid.bidid);
					//System.out.println("Removing linear bid key "+key);
					lbidlist.remove(key);
				}
				
				if (key != -1 && bidlistid == 1 && sbid != null)
				{
				//	System.out.println("Removing sbid" + sbid.bidid);
					//System.out.println("Removing linear bid key "+key);
					sbidlist.remove(key);
				}
					
				
				//Begin bid iteration Stability criteria 2
				/*for (int j = 0; j < lbidlist.size(); j++)
				{
					Lbid lbid1 = lbidlist.get(j);
					if (mwmcm.containsValue(lbid1.bidid))
					{
					  	
					  Item asgnitem = itemlist.get(mwmcmrev.get(lbid1.bidid));
					  int assgnprice = pricevector.get(asgnitem.itemid);
					  int bidamnt = lbid1.intercept + asgnitem.quality*lbid1.slope;
					  int profit1 = bidamnt - assgnprice;
					  for (Item item : itemlist)
					  {
						  if(item.itemid == asgnitem.itemid)
							  continue;
						  else
						  { 
							  int itemprice = pricevector.get(item.itemid);
							  int amnt = lbid1.intercept + item.quality*lbid1.slope;
							  int profit2 = amnt - itemprice;
							  if(profit2 > profit1)
							  {
								 int price = pricevector.get(item.itemid) + profit2 - profit1;
								 pricevector.put(item.itemid, price);
								 change = true;
								 //System.out.println("change4");
							  }
						   }
					   }//item iteration  
					  
					}
						
				}//lbid iteration
*/						
				int gap = 0;
				Item sigmaitem = null;
				Lbid sigmabid = null;
				int oldprice,newprice;
				for (int i =0; i<itemlist.size();i++)
				{
					Item item = itemlist.get(i);
					if(sigmaitem != null)
					{
						gap = sigmabid.slope*(item.quality-sigmaitem.quality)-
								(pricevector.get(item.itemid) - pricevector.get(sigmaitem.itemid));
						if(gap>0)
						{
							oldprice = pricevector.get(item.itemid);
							newprice = oldprice + gap;
							pricevector.put(item.itemid, newprice);
						}
							
					}
										
					int sigbidid = mwmcm.get(item.itemid);
					if (lbidmap.containsKey(sigbidid))
					{
						sigmaitem = item;
						sigmabid = lbidmap.get(sigbidid);
								
					}
					
					
					
				}
				
				//System.out.println("tau item id");
				gap = 0;
				sigmaitem = null;
				sigmabid = null;
				for (int i =itemlist.size()-1; i>=0 ; i--)
				{
					Item item = itemlist.get(i);
					if(sigmaitem != null)
					{
						//System.out.println(item.itemid+" "+sigmaitem.itemid);
						gap = sigmabid.slope*(item.quality-sigmaitem.quality)-
								(pricevector.get(item.itemid) - pricevector.get(sigmaitem.itemid));
						if(gap>0)
						{
							oldprice = pricevector.get(item.itemid);
							newprice = oldprice + gap;
							pricevector.put(item.itemid, newprice);
	                        
						}
							
					}
					//else
						//System.out.println(item.itemid+ " "+itemlist.size());
					int sigbidid = mwmcm.get(item.itemid);
					if (lbidmap.containsKey(sigbidid))
					{
						sigmaitem = item;
						sigmabid = lbidmap.get(sigbidid);
								
					}
					
					
					
				}
				
				
				
		
				printoutput(pricevector);
			}
		
			
	    }while(readinput.hasNext());
		
		readinput.close();
        
        
	}
	
	
	
	
	
	
	
	
}