package MicrobialGeneticAlgorithm;

import java.util.Vector;
import NeuralNetwork.NeuralNetwork;

public class TestDriver 
{
    public static void main(String[] args)
    {
    	NeuralNetwork nnTest = new NeuralNetwork(1,1,1,2);
    	NeuralNetwork nnTest2 = new NeuralNetwork(1,1,1,2);
    	System.out.println("nnTest1:");
    	nnTest.printStatusToConsole();
    	System.out.println();
    	System.out.println("nnTest2:");
    	nnTest2.printStatusToConsole();
    	System.out.println();
    	Vector<Double> gene = new Vector<Double>();
    	gene = nnTest.getWeights();
    	System.out.println("Extracting nnTest1 Gene:");
    	System.out.println(gene);
    	System.out.println();
    	System.out.println("Uploading nnTest1 Gene to nnTest2:");
    	nnTest2.setWeights(gene);
    	System.out.println();
    	nnTest2.printStatusToConsole();
	
    }
}
