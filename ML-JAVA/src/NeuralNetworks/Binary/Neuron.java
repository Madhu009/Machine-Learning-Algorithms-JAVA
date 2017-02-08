package NeuralNetworks.Binary;

import java.util.Random;

public class Neuron {

	private double value;
	
	private double[] weight;
	
	private double A;
	public double getA() {
		return A;
	}



	public void setA(double a) {
		A = a;
	}

	Random r;
	double epsilon = Math.max(Math.ulp(0), Math.ulp(0));
	public Neuron(int weightValue)
	{
		A=0;
		r=new Random();
		double min=0.0;
		double max=1.0;
		weight=new double[weightValue];
		for(int i=0;i<weightValue;i++)
		{
			weight[i]=min+(max-min)*r.nextDouble();
		}
	}
	
	

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double[] getWeight() {
		return weight;
	}

	public void setWeight(double[] weight) {
		this.weight = weight;
	}
	
	public void set0Weight(double weight) {
		this.weight[0] = weight;
	}
	
}
