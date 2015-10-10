package MicrobialGeneticAlgorithm;

import java.util.Vector;
import java.util.Random;
import NeuralNetwork.NeuralNetwork;

public class MicrobialGA 
{
	private Random random = new Random();
	private Vector<NeuralNetwork> population = new Vector<NeuralNetwork>();
	private int activeNetworkIndex;
	private int competitorNetworkIndex;
	private boolean sessionTermination;
	private int mutationRate;
	private int crossOverRate;
	private double survalTimeFitnessWeight;
	private double piecesSurvivedFitnessWeight;
	private double scoreFitnessWeight;
	private int trialCount;
	

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
	
	public void teminateSession(double score, double piecesSurvied, double time)
	{
		population.get(activeNetworkIndex).setFitness(evaluateFitness(score, piecesSurvied, time));
		trialCount++;
	}
	
	public double evaluateFitness(double score, double piecesSurvived, double time)
	{
		return score * scoreFitnessWeight + piecesSurvived * piecesSurvivedFitnessWeight + time * survalTimeFitnessWeight;
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
			if(random.nextInt(101) < crossOverRate)
			{
				double newChromosome = winnerGene.get(i);
				System.out.println("Crossover at gene index: " + i + ", now set to: " + newChromosome);
				loserGene.set(i, newChromosome);
			}
		}
		loser.setWeights(loserGene);
		System.out.println("Weights after crossover:");
		loser.printStatusToConsole();
	}
	
	public void mutate(NeuralNetwork nn)
	{
		Vector<Double> nnGene = nn.getWeights();
		for(int i = 0; i < nnGene.size(); i++)
		{
			if(random.nextInt(101) < (mutationRate / (double)nn.getWeights().size()))
			{
				double newChromosome = random.nextDouble() * (1.0-(-1.0)) - 1.0;
				System.out.println("Mutation at gene index: " + i + ", now set to: " + newChromosome);
				nnGene.set(i, newChromosome);
			}
		}
		nn.setWeights(nnGene);
		System.out.println("Weights after mutation:");
		nn.printStatusToConsole();
	}
	
	
	
	
	
}
