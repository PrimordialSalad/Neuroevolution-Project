package NeuralNetwork;


import java.util.Vector;
import java.util.Random;

public class Neuron 
{
	private static Random random = new Random();
	private int inputCount;
	private Vector<Double> inputWeights = new Vector<Double>();

	public Neuron(int setInputSize)
	{
		inputCount = setInputSize;
		for(int i = 0; i < inputCount + 1; i++)
		{
			inputWeights.add((double) randomClamped());
		}
	}

	public void setWeights(Vector<Double> newInputWeights)
	{
		for(int i = 0; i < inputWeights.size(); i++)
		{
			inputWeights.set(i, newInputWeights.get(i));
		}
	}

	public void setInputWeight(int index, double weight)
	{
		inputWeights.set(index, weight);
	}

	public Vector<Double> getWeights()
	{
		return inputWeights;
	}

	public double getWeight(int index)
	{
		return inputWeights.get(index);
	}

	public double getTotalWeightSum()
	{
		double totalWeights = 0;
		for(int i = 0; i < inputWeights.size(); i++)
		{
			totalWeights += inputWeights.get(i);
		}
		return totalWeights;
	}

	public String toString()
	{
		return "Input Weight: " + inputWeights;
	}

	private float randomClamped()
	{
		return (float) ((random.nextDouble() * (1.0-(-1.0))) - 1.0);
	}

}
