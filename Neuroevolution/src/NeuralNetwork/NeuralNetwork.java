package NeuralNetwork;


import java.util.Vector;

public class NeuralNetwork 
{
	private Vector<NeuralNetworkLayer> neuralNetwork = new Vector<NeuralNetworkLayer>();
	private int inputCount;
	private int outputCount;
	private int hiddenLayerCount;
	private int neuronsPerHiddenLayer;
	private int ActivationResponse = 1;
	private int bias = -1;
	private double fitness;

	public NeuralNetwork(int setInputCount, int setOutputCount, int setHiddenLayerCount, int setNeuronsPerHiddenLayer)
	{
		inputCount = setInputCount;
		outputCount = setOutputCount;
		hiddenLayerCount = setHiddenLayerCount;
		neuronsPerHiddenLayer = setNeuronsPerHiddenLayer;

		if(hiddenLayerCount > 0)
		{
			neuralNetwork.add(new NeuralNetworkLayer(neuronsPerHiddenLayer, inputCount));
		}
		for(int i = 0; i < hiddenLayerCount - 1; i++)
		{
			neuralNetwork.add(new NeuralNetworkLayer(neuronsPerHiddenLayer, neuronsPerHiddenLayer));
		}
		neuralNetwork.add(new NeuralNetworkLayer(outputCount, neuronsPerHiddenLayer));
	}

	public Vector<Double> activate(Vector<Double> inputs)
	{
		Vector<Double> outputs = new Vector<Double>();

		for(int i = 0; i < hiddenLayerCount + 1; i++)
		{
			if(i > 0)
			{
				inputs = outputs;
			}
			outputs.clear();

			for(int j = 0; j < neuralNetwork.get(i).getNeuronCount(); j++)
			{
				double netInput = 0;

				for(int k = 0; k < neuralNetwork.get(i).getNeuron(j).getWeights().size() - 1; k++)
				{

					for(int x = 0; x < inputs.size(); x++)
					{
						netInput += neuralNetwork.get(i).getNeuron(j).getWeight(k) * inputs.get(x);
					}
				}

				netInput += neuralNetwork.get(i).getNeuron(j).getWeight(neuralNetwork.get(i).getNeuron(j).getWeights().size() - 1) * bias;
				outputs.add(sigmoid(netInput, ActivationResponse));
			}
		}
		return outputs;
	}

	public void setWeights(Vector<Double> newWeights)
	{
		System.out.println("updating...");
		Vector<Double> segment = new Vector<Double>();

		for(int i = 0; i < neuralNetwork.size(); i++)
		{
			for(int j = 0; j < neuralNetwork.get(i).getTotalWeightCount(); j++)
			{
				segment.add(newWeights.remove(0));
			}
			neuralNetwork.get(i).setWeights(segment);
			segment.clear();
		}
	}
	
	public void setFitness(double newFitness)
	{
		fitness = newFitness;
	}

	public double getNumberOfWeights()
	{
		double totalWeights = 0.0;
		for(int i = 0; i < neuralNetwork.size(); i++)
		{
			totalWeights += neuralNetwork.get(i).getTotalWeights();
		}
		return totalWeights;
	}

	public Vector<Double> getWeights()
	{
		Vector<Double> totalWeights = new Vector<Double>();
		for(int j = 0; j < neuralNetwork.size(); j++)
		{
			for(int i = 0; i < neuralNetwork.get(j).getNeuronCount(); i++)
			{
				for(int k = 0; k < neuralNetwork.get(j).getNeuron(i).getWeights().size(); k++)
				{
					totalWeights.add(neuralNetwork.get(j).getNeuron(i).getWeight(k));
				}
			}
		}
		return totalWeights;
	}
	
	public double getFitness()
	{
		return fitness;
	}

	public int getTotalWeightCount()
	{
		int totalWeightCount = 0;
		for(int i = 0; i < neuralNetwork.size(); i++)
		{
			totalWeightCount += neuralNetwork.get(i).getTotalWeightCount();
		}
		return totalWeightCount;
	}

	public void printStatusToConsole()
	{
		for(int i = 0; i < neuralNetwork.size(); i++)
		{
			System.out.println(neuralNetwork.get(i));
		}
	}

	private static Double sigmoid(double netInput, double response) 
	{
		return (1/( 1 + Math.exp(-netInput / response)));
	}

}
