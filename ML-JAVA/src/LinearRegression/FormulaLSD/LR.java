package LinearRegression.FormulaLSD;



import Jama.Matrix;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;


public class LR {
	private Double lambda;
	private String trainingFile;
	private String testingFile;
	
	public LR() {
		lambda = 1.0;
		trainingFile = "C:/Users/Madhu/Desktop/test.txt";
		//testingFile = "C:/Users/Madhu/Desktop/WEKA_AL/regressiondata/one.csv";
	}
	
	
	private Matrix readMatrix(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			List<double[]> data_array = new ArrayList<double[]>();

			String line;
			while ((line = reader.readLine()) != null) {
				String fields[] = line.split(",");
				double data[] = new double[fields.length];
				for (int i = 0; i < fields.length; ++i) {
					data[i] = Double.parseDouble(fields[i]);
				}
				data_array.add(data);
			}

			if (data_array.size() > 0) {
				int cols = data_array.get(0).length;
				int rows = data_array.size();
				Matrix matrix = new Matrix(rows, cols);
				for (int r = 0; r < rows; ++r) {
					for (int c = 0; c < cols; ++c) {
						matrix.set(r, c, data_array.get(r)[c]);
					}
				}
				return matrix;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	    return new Matrix(0, 0);
	}
	
	
	private Matrix getDataPoints(Matrix data_set) {
		Matrix features = data_set.getMatrix(0, data_set.getRowDimension() - 1, 0, data_set.getColumnDimension() - 2);
		int rows = features.getRowDimension();
		int cols = features.getColumnDimension() + 1;
		Matrix modifiedFeatures = new Matrix(rows, cols);
		for (int r = 0; r < rows; ++r) {
			for (int c = 0; c < cols; ++c) {
				if (c == 0) {
					modifiedFeatures.set(r, c, 1.0);
				} else {
					modifiedFeatures.set(r, c, features.get(r, c-1));
				}
			}
		}
		return modifiedFeatures;
	}
	
	
	private Matrix getTargets(Matrix data_set) {
	    return data_set.getMatrix(0, data_set.getRowDimension() - 1, data_set.getColumnDimension() - 1, data_set.getColumnDimension() - 1);
	}
	
	/**
	
	 * Linear regression with L2 regularizer has the close form as follows:
	 * w = (x^{T} * X + \lambda * I)^{-1} * X^{T} * t 
	 * 
	 */
	
	private Matrix trainLinearRegressionModel(Matrix data, Matrix targets, Double lambda) {
		int row = data.getRowDimension();
		int column = data.getColumnDimension();
		Matrix identity = Matrix.identity(column, column);
		identity.times(lambda);
		Matrix dataCopy = data.copy();
		Matrix transponseData = dataCopy.transpose();
		Matrix norm = transponseData.times(data);
		Matrix circular = norm.plus(identity);
		Matrix circularInverse = circular.inverse();
		Matrix former = circularInverse.times(data.transpose());
		Matrix weight = former.times(targets);
		
	    return weight;
	}
	
	
	private double evaluateLinearRegressionModel(Matrix data, Matrix targets, Matrix weights) {
		double error = 0.0;
		int row = data.getRowDimension();
		int column = data.getColumnDimension();
		assert row == targets.getRowDimension();
		assert column == weights.getColumnDimension();
		
		Matrix predictTargets = predict(data, weights);
		for (int i = 0; i < row; i++) {
			error = (targets.get(i, 0) - predictTargets.get(i, 0)) * (targets.get(i, 0) - predictTargets.get(i, 0));
		}

		return 0.5 * error;
	}
	

	private Matrix predict(Matrix data, Matrix weights) {
		int row = data.getRowDimension();
		Matrix predictTargets = new Matrix(row, 1);
		for (int i = 0; i < row; i++) {
			double value = multiply(data.getMatrix(i, i, 0, data.getColumnDimension() -1 ), weights);
			//System.out.println(value);
			predictTargets.set(i, 0, value);
		}
		return predictTargets;
	}
	
	
	protected Double multiply(Matrix data, Matrix weights) {
		Double sum = 0.0;
		int column = data.getColumnDimension();
		for (int i = 0; i <column; i++) {
			sum += data.get(0, i) * weights.get(i, 0);
		}
		return sum;
	}
	
	
	public static void main(String[] args) {
		LR lr = new LR();
		try {
			Matrix training = lr.readMatrix(lr.trainingFile);
			//Matrix testing = lr.readMatrix(lr.testingFile);
			
			/** get the actual features, meanwhile add a N*1 column vector with value being all 1 as the first column of the features */
			Matrix trainingData = lr.getDataPoints(training);
//			Matrix testingData = lr.getDataPoints(testing);
			
			Matrix trainingTargets = lr.getTargets(training);
		    //Matrix testingTargets = lr.getTargets(testing);
		    
		    // Train the model.
		    Matrix weights = lr.trainLinearRegressionModel(trainingData, trainingTargets, lr.lambda);
		    for (int i = 0; i < weights.getRowDimension(); i++) {
			System.out.println(weights.get(i, 0));
		    }
		    
		    double[] testdata=new double[2];
		    testdata[0]=30;
		    testdata[1]=40;

		    // Evaluate the model using training and testing data.
		    double training_error = lr.evaluateLinearRegressionModel(trainingData, trainingTargets, weights);
		   // double testing_error = lr.evaluateLinearRegressionModel(testingData, testingTargets, weights);

		    System.out.println(training_error);
		   // System.out.println(testing_error);
		    
		    double output=weights.get(0, 0);
		    
		    for(int i=1,j=0;i<=2;i++,j++)
		    {
		    	output=output+weights.get(i, 0)*testdata[j];
		    	
		    }
		    
		   System.out.println(output);
		    
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);			
		}
	
		
		
		
	}
	
}
