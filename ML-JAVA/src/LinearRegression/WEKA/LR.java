package LinearRegression.WEKA;
import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;

public class LR{
	
public static void main(String args[]) throws Exception{
	
//load data
Instances data = new Instances(new BufferedReader(new
FileReader("C:/Users/Madhu/Desktop/Test.arff")));
data.setClassIndex(data.numAttributes() - 1);


//build model
LinearRegression obj = new LinearRegression();
obj.buildClassifier(data);
System.out.println(obj);


//classify the last instance
Instance test = data.lastInstance();
double predictedOutput = obj.classifyInstance(test);
System.out.println("My house ("+test+"): "+predictedOutput);
}
}