package NaiveBayes.WEKA;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;

public class NB {

	public static void main(String[] args) throws Exception {
	
		Instances dataset= new Instances(new BufferedReader(new FileReader("C:/Users/Madhu/Desktop/weather.arff")));
		dataset.setClassIndex(dataset.numAttributes()-1);
		
		
		NaiveBayes obj=new NaiveBayes();
		obj.buildClassifier(dataset);
		System.out.println(obj);
		
		Instance last=dataset.lastInstance();
		double result=obj.classifyInstance(last);
		
		System.out.println(last+"-->"+result);

	}

}
