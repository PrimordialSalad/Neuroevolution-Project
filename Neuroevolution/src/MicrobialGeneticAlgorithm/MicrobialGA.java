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
	private final int MUTATION_RATE = 1;
	private final int CROSSOVER_RATE = 50;
	private double survalTimeFitnessWeight;
	private double piecesSurvivedFitnessWeight;
	private double scoreFitnessWeight;
	private double[] trialFitnessScore = {0, 0, 0};
	private int sessionCount;
	private int stageCount;
	private final int MAX_SESSION_COUNT = 3;

	public MicrobialGA(int setPopulationCount, int setInputCount, int setOutputCount, int setHiddenLayerCount, int setNeuronsPerHiddenLayer)
	{
		for(int i = 0; i < setPopulationCount; i++)
		{
			population.add(new NeuralNetwork(setInputCount, setOutputCount, setHiddenLayerCount, setNeuronsPerHiddenLayer));
		}
		selectGenes();
	}
	
	public Vector<Double> activate(Vector<Double> input)
	{
		return population.get(activeNetworkIndex).activate(input);
	}
	
	public void teminateSession(double score, double piecesSurvied, double time)
	{
		double sessionFitnessScore = evaluateFitness(score, piecesSurvied, time);
		System.out.println("Session number: " + sessionCount + " teminated, neural network index: " 
							+ activeNetworkIndex + " Session score: " + sessionFitnessScore);
		trialFitnessScore[sessionCount] = sessionFitnessScore;
		sessionCount++;
		if(sessionCount > MAX_SESSION_COUNT)
		{
			teminateTrial();
		}
	}
	
	private void teminateTrial()
	{
		System.out.print("Trial termination on population index" + activeNetworkIndex + " ");
		double averageFitness = 0;
		for(int i = 0; i < trialFitnessScore.length; i++)
		{
			averageFitness += trialFitnessScore[i];
		}
		averageFitness /= MAX_SESSION_COUNT;
		population.get(activeNetworkIndex).setFitness(averageFitness);
		System.out.println("fitness score: " + averageFitness);
		sessionCount = 0;
		stageCount++;
		if(stageCount > 1)
		{
			if(population.get(activeNetworkIndex).getFitness() > population.get(competitorNetworkIndex).getFitness())
			{
				infect(population.get(activeNetworkIndex), population.get(competitorNetworkIndex));
				mutate(population.get(competitorNetworkIndex));
			}
			else if(population.get(activeNetworkIndex).getFitness() < population.get(competitorNetworkIndex).getFitness())
			{
				infect(population.get(competitorNetworkIndex), population.get(activeNetworkIndex));
				mutate(population.get(activeNetworkIndex));
			}
			else
			{
				mutate(population.get(competitorNetworkIndex));
				mutate(population.get(activeNetworkIndex));
			}
			selectGenes();
		}
		else
		{
			int indexPlaceHolder = activeNetworkIndex;
			activeNetworkIndex = competitorNetworkIndex;
			competitorNetworkIndex = indexPlaceHolder;
		}
	}
	
	public double evaluateFitness(double score, double piecesSurvived, double time)
	{
		return score * scoreFitnessWeight + piecesSurvived * piecesSurvivedFitnessWeight + time * survalTimeFitnessWeight;
	}
	
	public void selectGenes()
	{
		for(int i = 0; i < trialFitnessScore.length; i++)
		{
			trialFitnessScore[i] = 0;
		}
		stageCount = 0;
		sessionCount = 0;
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
			if(random.nextInt(100) < CROSSOVER_RATE)
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
			if(random.nextInt(100) < MUTATION_RATE)
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
