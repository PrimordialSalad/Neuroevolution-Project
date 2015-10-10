package NeuralNetwork;


import java.util.Vector;

public class NeuralNetworkLayer 
{
	private int neuronCount;
	private int neuronInputCount;
	private Vector<Neuron> neuronLayer = new Vector<Neuron>();

	public NeuralNetworkLayer(int setNeuronCount, int setInputsPerNeuron)
	{
		neuronCount = setNeuronCount;
		neuronInputCount = setInputsPerNeuron;
		for(int i = 0; i < neuronCount; i++)
		{
			neuronLayer.add(new Neuron(neuronInputCount));
		}
	}

	public void setWeights(Vector<Double> weights)
	{
		Vector<Double> chromosomes = new Vector<Double>();
		for(int i = 0; i < neuronLayer.size(); i++)
		{
			for(int j = 0; j < neuronLayer.get(i).getWeights().size(); j++)
			{
				chromosomes.add(weights.remove(0));
			}
			neuronLayer.get(i).setWeights(chromosomes);
			chromosomes.clear();
		}
	}

	public Neuron getNeuron(int index)
	{
		return neuronLayer.get(index);
	}

	public int getNeuronInputCount()
	{
		return neuronInputCount;
	}

	public int getNeuronCount()
	{
		return neuronLayer.size();
	}

	public int getTotalWeightCount()
	{
		int totalWeightCount = 0;
		for(int i = 0; i < neuronLayer.size(); i++)
		{
			for(int j = 0; j < neuronLayer.get(i).getWeights().size(); j++)
			{
				totalWeightCount++;
			}
		}
		return totalWeightCount;
	}

	public double getTotalWeights()
	{
		double totalWeights = 0.0;
		for(int i = 0; i < neuronLayer.size(); i++)
		{
			totalWeights += neuronLayer.get(i).getTotalWeightSum();
		}
		return totalWeights;
	}

	public String toString()
	{
		return "Total Neuron Count: " + neuronLayer.size() + " " + neuronLayer.toString();
	}
}
