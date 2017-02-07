package NeuralNetworks.Binary;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import LinearRegression.FormulaLSD.LR;

public class Network {

	private List<Neuron> hiddenLayer;
	private List<Neuron> inputLayer;
	Neuron outputLayer=new Neuron(1);
	private static String fileName="C:/Users/Madhu/Desktop/nn.txt";

	public Network()
	{
		hiddenLayer=new ArrayList<Neuron>();
		inputLayer=new ArrayList<Neuron>();
	}
	
	public static void main(String args[])
	{
		Network network=new Network();
		network.getInputLayer();
		network.getHiddeLayer();
		LR obj=new LR();
		Matrix data_set;
		data_set=obj.readMatrix(fileName);
		Matrix x_s=obj.getDataPoints(data_set);
		Matrix y_s=obj.getTargets(data_set);
		
	}
	
	public void forwardPropagation()
	{
		double outputValue=0;
		for(int j=0;j<3;j++)
		{
			double hyp=0;
			for(int i=0;i<2;i++)
			{
				hyp=+inputLayer.get(i).getWeight()[j]*inputLayer.get(i).getValue();
			}
			hiddenLayer.get(j).setValue(hyp);
			hiddenLayer.get(j).setA(getSigmoid(hyp));
			
			outputValue=outputValue+(hiddenLayer.get(j).getValue()*hiddenLayer.get(j).getWeight()[0]);
			
		}
		outputLayer.setValue(outputValue);
		outputLayer.setA(getSigmoid(outputValue));
	}
	
	public double getSigmoid(double hyp)
	{
		 return 1.0/(1+Math.exp(-hyp));
	}
	
	public void createNetwork(Matrix x_s)
	{
		int rows=x_s.getRowDimension();
		
		for(int i=0;i<rows;i++)
		{
			double [] data=getDouble(x_s, i);
			for(int j=0;j<data.length;j++)
			{
				inputLayer.get(j).setValue(data[j]);
			}
			
		}
		
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
	public void getInputLayer()
	{
		Neuron x=new Neuron(3);
		Neuron y=new Neuron(3);
		inputLayer.add(x);
		inputLayer.add(y);
	}
	public void getHiddeLayer()
	{
		Neuron h1=new Neuron(1);
		Neuron h2=new Neuron(1);
		Neuron h3=new Neuron(1);
		inputLayer.add(h1);inputLayer.add(h2);
		inputLayer.add(h3);
	}
}
