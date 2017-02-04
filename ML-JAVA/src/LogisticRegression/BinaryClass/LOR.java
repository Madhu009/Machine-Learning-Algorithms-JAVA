package LogisticRegression.BinaryClass;

import Jama.Matrix;
import LinearRegression.FormulaLSD.LR;

public class LOR {
	
	private Double lambda;
	private String trainingFile;

	public LOR() {
		lambda = 1.0;
		trainingFile = "C:/Users/Madhu/Desktop/test2.txt";
	}
	
	public static void main(String[] args) {
		LOR lor = new LOR();
		LR lr= new LR();
		try {
			Matrix training = lr.readMatrix(lor.trainingFile);
			
			/** get the actual features, meanwhile add a N*1 column vector with value being all 1 as the first column of the features */
			Matrix trainingData = lr.getDataPoints(training);
			
			Matrix trainingTargets = lr.getTargets(training);
		    
		    // Train the model.
		    Matrix weights = lr.trainLinearRegressionModel(trainingData, trainingTargets, lor.lambda);
		    for (int i = 0; i < weights.getRowDimension(); i++) {
			System.out.println(weights.get(i, 0));
		    }
		    
		    double[] testdata=new double[2];
		    testdata[0]=53;
		    testdata[1]=31;

		    // Evaluate the model using training and testing data.
		    double training_error = lr.evaluateLinearRegressionModel(trainingData, trainingTargets, weights);
		   // double testing_error = lr.evaluateLinearRegressionModel(testingData, testingTargets, weights);

		    System.out.println(training_error);
		    
		    double output=weights.get(0, 0);
		    
		    for(int i=1,j=0;i<=2;i++,j++)
		    {
		    	output=output+weights.get(i, 0)*testdata[j];
		    	
		    }
		    
		    System.out.println(output);
		   
		   double logr=1.0/(1+Math.exp(-output));
			  
		    System.out.println("Predicted Score is: "+logr);
		    
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);			
		}
	
		
		
		
	}
}
