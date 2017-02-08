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
	
	public Matrix getDataPoints(Matrix data_set) {
	return data_set.getMatrix(0, data_set.getRowDimension() - 1, 0, data_set.getColumnDimension() - 2);
	
	}
	
	
	public Matrix getTargets(Matrix data_set) {
	    return data_set.getMatrix(0, data_set.getRowDimension() - 1, data_set.getColumnDimension() - 1, data_set.getColumnDimension() - 1);
	}
	
	public static void main(String args[])
	{
		Network network=new Network();
		LR obj=new LR();
		Matrix data_set;
		data_set=obj.readMatrix(fileName);
		Matrix x_s=network.getDataPoints(data_set);
		Matrix y_s=network.getTargets(data_set);
		network.createNetwork(x_s, y_s);
		
	}
	
	
	public void forwardPropagation()
	{
		double outputValue=0;
		for(int j=0;j<3;j++)
		{
			double hyp=0;
			for(int i=0;i<3;i++)
			{
				hyp=hyp+(inputLayer.get(i).getWeight()[j]*inputLayer.get(i).getValue());
			}
			hiddenLayer.get(j+1).setValue(hyp);
			hiddenLayer.get(j+1).setA(getSigmoid(hyp));
			
			outputValue=outputValue+(hiddenLayer.get(j).getValue()*hiddenLayer.get(j).getWeight()[0]);
			
		}
		outputLayer.setValue(outputValue);
		outputLayer.setA(getSigmoid(outputValue));
	}
	
	
	public void backPropagation(double value)
	{
		double deltaSum=0;//change in sum value;
		double error=0;//sigmoid error
		
		error=value-outputLayer.getA();
		deltaSum=(((1-getSigmoid(outputLayer.getValue()))*getSigmoid(outputLayer.getValue()))*error);
		
		double[] deltaWeights=new double[3];
		
		for(int i=0;i<3;i++)
		{
			deltaWeights[i]=(deltaSum/hiddenLayer.get(i+1).getA());
		}
		double oldHiddenWeights[]=new double[3];
		for(int i=0;i<3;i++)
		{
			double updatedWeight=hiddenLayer.get(i+1).getWeight()[0]+deltaWeights[i];
			oldHiddenWeights[i]=hiddenLayer.get(i+1).getWeight()[0];
			hiddenLayer.get(i+1).set0Weight(updatedWeight);
		}
	
		double[] deltaHiddenSum=new double[3];
		
		for(int i=0;i<3;i++)
		{
			double sigInverse=(1-getSigmoid(hiddenLayer.get(i+1).getValue()))*getSigmoid(hiddenLayer.get(i+1).getValue());
			deltaHiddenSum[i]=(deltaSum/oldHiddenWeights[i])*sigInverse;
		}
		
		
		for(int i=0;i<2;i++)
		{
			double[] deltaInputWeights=new double[3];
			for(int j=0;j<3;j++)
			{
				deltaInputWeights[i]=deltaHiddenSum[j]/inputLayer.get(i+1).getValue();
			}
			inputLayer.get(i+1).setWeight(deltaInputWeights);
		}
		
		
	}
	
	
	public void createNetwork(Matrix x_s,Matrix y_s)
	{
		getInputLayer();
		getHiddeLayer();
		
		int rows=x_s.getRowDimension();
		
		inputLayer.get(0).setValue(1);//set bias
		
		for(int i=0;i<=10000;i++)
		{
			double [] data=getDouble(x_s, 0);//get the example
			
			 for(int j=0;j<data.length;j++)
			{
				inputLayer.get(j+1).setValue(data[j]);
			}
			 System.out.println(i);
			 if(i==290)
			 {
				 System.out.println("print");
			 }
			forwardPropagation();
			backPropagation(0);
		}
		
	}
	
	
	public void getInputLayer()
	{
		Neuron x=new Neuron(3);
		Neuron y=new Neuron(3);
		Neuron bias=new Neuron(3);
		inputLayer.add(bias);
		inputLayer.add(x);
		inputLayer.add(y);
	}
	public void getHiddeLayer()
	{
		Neuron h1=new Neuron(1);
		Neuron h2=new Neuron(1);
		Neuron h3=new Neuron(1);
		Neuron bias=new Neuron(1);
		hiddenLayer.add(bias);
		hiddenLayer.add(h1);hiddenLayer.add(h2);
		hiddenLayer.add(h3);
	}
	
	public double getSigmoid(double hyp)
	{
		 return 1.0/(1+Math.exp(-hyp));
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
}
