package LogisticRegression.BinaryClass.WEKA;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.functions.Logistic;
import weka.core.Instance;
import weka.core.Instances;

public class LOR {

	public static void main(String[] args) throws Exception {
		Instances data = new Instances(new BufferedReader(new
				FileReader("C:/Users/Madhu/Desktop/Test2.arff")));
				data.setClassIndex(data.numAttributes() - 1);


				//build model
				Logistic obj = new Logistic();
				obj.buildClassifier(data);
				System.out.println(obj);


				//classify the last instance
				Instance test = data.lastInstance();
				double predictedOutput = obj.classifyInstance(test);
				System.out.println(test+" --> "+predictedOutput);
				}
	

}
