package SVM.BinaryClass;

import Jama.Matrix;
import LinearRegression.FormulaLSD.LR;

public class svm {

	private static String fileName;

	public static void main(String args[])
	{
		LR obj=new LR();
		Matrix data_set;
		data_set=obj.readMatrix(fileName);
		Matrix x_s=obj.getDataPoints(data_set);
		Matrix y_s=obj.getTargets(data_set);
		
	}
	
	
	public double findHypotheis(double[] x,double[] theta)
	{
		double logit=0;
		for(int i=0;i<x.length;i++)
		{
			logit=logit+(x[i]*theta[i]);
		}
		double logr=1.0/(1+Math.exp(-logit));
		
		return logr;
	}
	
	
	public void findCostFuction(Matrix x_s,Matrix y_s,double[] theta)
	{
		int r=x_s.getRowDimension();
		int c=x_s.getColumnDimension();
		double finalResult = 0;
		double reg=getRegularzationFunction(theta);
		for(int i=0;i<r;i++)
		{
			double result=0;
			double[] data=getDouble(x_s, i); //example row
			result=findHypotheis(data,theta); // weights*x's for x(i)
			
			double pos=Math.log(result)*y_s.get(i, 0);
			double neg=Math.log(1-result)*(1-y_s.get(i, 0));
			finalResult=pos+neg;
		}
		finalResult=finalResult+reg;
	}
	
	public double getRegularzationFunction(double[] theta)
	{
		double result=0;
		double lambda=0.5;
		for(int i=0;i<theta.length;i++)
		{
			result=result+ (theta[i]*theta[i]);
		}
		return (lambda/2)*result;
	}
	
	public double[] getDouble(Matrix m,int index)
	{
		double[] data=new double[m.getColumnDimension()];
		int c=m.getColumnDimension();
		
		for(int i=0;i<c;i++)
		{
			data[i]=m.get(index, i);
		}
		
		return data;
	}
	
	public void minimizeTheta(double [] theta,Matrix dataset)
	{
		double smallestDistancePos=0;double smallestDistanceNeg=0;
		
		for(int e=0;e<dataset.getRowDimension();e++)
		{
			double [] x=getDouble(dataset, e);
			if(x[x.length-1]==1)
			{
				
			}
			else if(x[x.length-1]==0)
			{
				
			}
			
		}
		
	}
	
	public double calculateDistance(double[] point,double[] theta)
	{
		//findPerpendicular
		double[] pLineTheta=new double[theta.length];
		for(int i=1;i<theta.length;i++)
		{
			pLineTheta[i]=-(1/theta[i]);
		}
		//find intersect point
		
	pLineTheta[0]=0;
	double rightside=0;
	double leftside=0;
	for(int i=0;i<theta.length;i++)
	{
		 rightside=+theta[i]*point[i];
		 leftside=+point[i];
	}
	pLineTheta[0]=rightside-leftside;//y value
	
	return 0.9;
	
	
		
	}
}
