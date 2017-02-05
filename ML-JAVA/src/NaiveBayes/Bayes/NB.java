package NaiveBayes.Bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NB {

	List<String[]> list=new ArrayList<String[]>();
	int total=0;
	int totalYes=0;
	int totalNo=0;
	
	Map<String, String> dictionary;
	List<String> featureClasses1;
	List<String> featureClasses2;
	List<String> featureClasses3;
	List<String> featureClasses4;
	List<List<String>> table;
	
	public NB()
	{
		dictionary = new HashMap<String, String>();
		featureClasses1=new ArrayList<String>();
		featureClasses2=new ArrayList<String>();
		featureClasses3=new ArrayList<String>();
		featureClasses4=new ArrayList<String>();
		table=new ArrayList<List<String>>();
		table.add(featureClasses1);
		table.add(featureClasses2);table.add(featureClasses3);
		table.add(featureClasses4);

	}
	public void createDataArray() throws Exception
	{
		BufferedReader reader=new BufferedReader(new FileReader
				("C:/Users/Madhu/Desktop/weather.txt"));
		String line;
		while((line=reader.readLine())!=null)
		{
			total++;
			
			String array[]=line.split(",");
		
			if(array[array.length-1].equals("yes"))
			{
				
				totalYes++;
				for(int i=0;i<array.length-1;i++)
				{
					if(dictionary.containsKey(array[i]))
					{
						String val = dictionary.get(array[i]);
						int yesCount=getYesCount(val);
						String updatedval= setYesCount(yesCount,val);
						dictionary.put(array[i], updatedval);
					}
					else
					{
						dictionary.put(array[i], "1,0");
						table.get(i).add(array[i]);
					}
						
				}
			}
				
			else
			{
				totalNo++;
				for(int i=0;i<array.length-1;i++)
				{
					if(dictionary.containsKey(array[i]))
					{
						String val = dictionary.get(array[i]);
						int noCount=getNoCount(val);
						String updatedval= setNoCount(noCount,val);
						dictionary.put(array[i], updatedval);
					}
					else
					{
						dictionary.put(array[i], "0,1");
						table.get(i).add(array[i]);
					}
						
				}
			}
				
			
			list.add(array);
		}
		reader.close();
	}
	
	private String setNoCount(int noCount, String val) {
		String[] no=val.split(",");
		
		String result= no[0]+","+Integer.toString(noCount);
		return result;
	}

	private int getNoCount(String val) {
		String[] no=val.split(",");
		int value=Integer.parseInt(no[1]);
		value++;
		return value;
	}

	private String setYesCount(int yesCount, String val) {
		
		String[] no=val.split(",");
		
		String result= Integer.toString(yesCount)+","+no[1];
		return result;
	}

	private int getYesCount(String val) {
		
		String[] no=val.split(",");
		int value=Integer.parseInt(no[0]);
		value++;
		return value;
	}
	
	public void calculateInputProb(List<String> input)
	{
		double yesProbability=((double)totalYes/(double)total)
				,noProbability=((double)totalNo/(double)total);
		
		for(int i=0;i<input.size();i++)
		{
			String value=dictionary.get(input.get(i));
			
			int yes=getYesCount(value);
			double yesP=((double)yes/(double)totalYes); 
			yesProbability=yesProbability*yesP;
			
			int no=getNoCount(value);
			double noP=((double)no/(double)totalNo); 
			noProbability=noProbability*noP;
			
		}
		
		System.out.println(yesProbability+" "+noProbability);
	}

	public void print()
	{
		for(int i=0;i<list.size();i++)
		{
			String[] a=list.get(i);
			
			for(int j=0;j<a.length;j++)
			{
				System.out.print(a[j]+" ");
			}
			
			System.out.println();
		}
		System.out.println();
		double result1=(double)totalYes/(double)total;
		double result2=(double)totalNo/(double)total;
		System.out.print("total examples are = "+total+"\n" );
		System.out.println("prior probability of yes ("+ totalYes +"/" +total+") = " +result1);
		System.out.println("prior probability of No ("+ totalNo +"/" +total+") = " +result2);
	}
	public void printTable()
	{
		for(int i=0;i<table.size();i++)
		{
			List<String> fc=table.get(i);
			
			for(int j=0;j<fc.size();j++)
			{
				System.out.print(fc.get(j)+" ");
				System.out.print(dictionary.get(fc.get(j))+" "+"\n");
			}
			System.out.println();
		}
	}
	
	
	public static void main(String args[]) throws Exception
	{
		NB nb=new NB();
		nb.createDataArray();
		//nb.print();
		nb.printTable();
		System.out.println("calculating problities");
		List<String> input=new ArrayList<>();
		input.add("sunny");input.add("cool");input.add("high"); input.add("TRUE");
		nb.calculateInputProb(input);
	}
}
