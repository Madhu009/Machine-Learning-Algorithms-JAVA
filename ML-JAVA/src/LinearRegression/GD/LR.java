package LinearRegression.GD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

public class LR {

	String filePath="C:/Users/Madhu/Desktop/Test.txt";
	
	private static final double TOLERANCE = 1E-11;
	
	private List<double[]> readDataFromFile() throws IOException
	{
		FileReader file=new FileReader(filePath);
		BufferedReader reader = new BufferedReader(file);
		String line;
		List<double[]> dataArray=new ArrayList<double[]>();
		
		while(reader.readLine()!=null)
		{
			line=reader.readLine();
			String[] example=line.split(",");
			double[] data=new double[example.length];
			
			for (int i = 0; i < example.length; ++i) {
				data[i] = Double.parseDouble(example[i]);
			}
			
			dataArray.add(data);
		}
		reader.close();
		
		return dataArray;
	}
	
	private Matrix createDataMatrix() throws IOException
	{
		List<double[]> data=readDataFromFile();
		int rows=data.size();
		int cols=data.get(0).length;
		
		Matrix dataMatrix=new Matrix(rows, cols);
		
		for(int i=0;i<rows;i++)
		{
			double[] example=data.get(i);
			
			for(int j=0;j<cols;j++)
			{
				dataMatrix.set(i, j, example[j]);
			}
		}
		
		return dataMatrix;
		
	}
	
	
	
	private Matrix getFeatures(Matrix data) throws IOException
	{
		
		
		int rows=data.getRowDimension();
		int cols=data.getColumnDimension();
		Matrix featureMatrix=new Matrix(rows,cols);
		
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(j==0)
				{
					featureMatrix.set(i, j, 1);
				}
				else
				{
					featureMatrix.set(i, j, data.get(i, j-1));
				}
			}
		}
		
		return featureMatrix;
	}
	
	
	private Matrix getTarget(Matrix data)
	{
		int r=data.getRowDimension();
		int c=data.getColumnDimension();
		Matrix target =new Matrix(r,1);
		
		for(int i=0;i<r;i++)
		{
			target.set(i, 1, data.get(i, c-1));
		}
		return target;
	}
	
	public static void main(String args[]) throws IOException
	{
		LR obj=new LR();
		Matrix data,features,targets;
		double[] theta;
		data=obj.createDataMatrix();
		features=obj.getFeatures(data);
		targets=obj.getTarget(data);
		theta=obj.getThetaValues(features.getRowDimension());
		double[] finalWeights=obj.calculateGDWeights(targets, features, theta);
		
		 double output=finalWeights[0];
		 double[] testdata=new double[2];
		    testdata[0]=50;
		    testdata[1]=2;
		    
		    for(int i=1,j=0;i<=2;i++,j++)
		    {
		    	output=output+finalWeights[0]*testdata[j];
		    	
		    }
		    System.out.println(output);
	}
	
	public double[] getThetaValues(int no)
	{
		Random r=new Random();
		double[] theta=new double[no];
		double min=-1.0;
		double max=1.0;
		theta[0]=1;
		for(int i=1;i<no;i++)
		{
			theta[i] = min+(max-min)*r.nextDouble();
		}
		return theta;
	}
	
	
	//Ho(X)= Oo+O1*X1+O2*X2+.....
	
	public double findHypothesis(double[] theta,double[] features)
	{
		double h=0;
		for(int i=0;i<theta.length;i++)
		{
			h=h+(theta[i]*features[i]);
		}
		return h;
	}
	
	public double findCostFuction(Matrix features,Matrix targets,double[] theta)
	{
		int rows=features.getRowDimension();
		int cols=features.getColumnDimension();
		double sum=0;
		
		for(int i=0;i<rows;i++)
		{
			double[] example=new double[cols];
			
			for(int j=0;j<cols;j++)
			{
				example[j]=features.get(i, j);
			}
			
			sum=sum+(findHypothesis(theta, example)-targets.get(i, 0));
		}
		return (1/rows)*sum;
	}
	
	public double[] calculateGDWeights(Matrix targets, Matrix features, double[] theta)
	{
		double[] temp=new double[theta.length];
		double alpha=0;
		double tolerancecheck=0.0;
		double[] diffWeights=new double[theta.length];
		boolean status=false;
		do
		{
			for(int i=0;i<theta.length;i++)
			{
				temp[i]=(theta[i]-alpha)*findCostFuction(features, targets, theta);
			}
			
			for(int i=0;i<theta.length;i++)
			{
				diffWeights[i]=(temp[i]-theta[i]);
			}
			for(int i=0;i<theta.length;i++)
			{
				theta[i]=temp[i];
			}

			for(int i=0;i<theta.length;i++)
			{
				tolerancecheck=tolerancecheck+Math.abs(diffWeights[i]);
			}
			if(tolerancecheck>TOLERANCE)
			{
				status=true;
			}
		}
		while(status==false);
		
		return theta;
	}
	
	
	
}
