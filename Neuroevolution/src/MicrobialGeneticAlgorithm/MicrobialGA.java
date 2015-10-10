package MicrobialGeneticAlgorithm;

import java.util.Vector;
import java.util.Random;
import NeuralNetwork.NeuralNetwork;

public class MicrobialGA 
{
	private Vector<NeuralNetwork> population = new Vector<NeuralNetwork>();
	private Random random = new Random();
	private int activeNetworkIndex;
	private int competitorNetworkIndex;
	private boolean sessionTermination;
	private int mutationRate;
	private int crossOverRate;

	public MicrobialGA(int setPopulationCount, int setInputCount, int setOutputCount, int setHiddenLayerCount, int setNeuronsPerHiddenLayer)
	{
		for(int i = 0; i < setPopulationCount; i++)
		{
			population.add(new NeuralNetwork(setInputCount, setOutputCount, setHiddenLayerCount, setNeuronsPerHiddenLayer));
		}
		crossOverRate = 50;
		mutationRate = 1;
		sessionTermination = false;
		selectGenes();
	}
	
	public Vector<Double> activate(Vector<Double> input)
	{
		return population.get(activeNetworkIndex).activate(input);
	}
	
	public void teminateSession(int score, int piecesSurvied)
	{
		population.get(activeNetworkIndex).setFitness(evaluateFitness(score, piecesSurvied));
	}
	
	public void selectGenes()
	{
		activeNetworkIndex = random.nextInt(population.size());
		competitorNetworkIndex = random.nextInt(population.size());
		while(competitorNetworkIndex == activeNetworkIndex)
		{
			competitorNetworkIndex = random.nextInt(population.size());
		}
	}
	
	public void infect(NeuralNetwork winner, NeuralNetwork loser)
	{
		Vector<Double> winnerGene = winner.getWeights();
		Vector<Double> loserGene = loser.getWeights();
		for(int i = 0; i < winnerGene.size(); i++)
		{
			if(random.nextInt(101) > crossOverRate)
			{
				loserGene.set(i, winnerGene.get(i));
			}
		}
		
	}
	
	public void mutate(NeuralNetwork nn)
	{
		
	}
	
	public double evaluateFitness(int score, int piecesSurvived)
	{
		return 0.0;
	}
	
	
	
}
