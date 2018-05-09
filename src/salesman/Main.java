package salesman;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import genetics.GeneticSolver;
import genetics.Parameters;

public class Main {

	private static final int NB_GENERATIONS = 500;
	private static final int SIZE_POPULATION = 100;
	private static final int NB_CROSSINGS = 50;
	private static final int NB_MUTATIONS = 10;

	public static void main(final String[] args) {
		final Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		final int nbCities = scanner.nextInt();
		final TravellingSalesman problem = new TravellingSalesman();
		for (int city = 0; city < nbCities; city++) {
			final int cityX = scanner.nextInt();
			final int cityY = scanner.nextInt();
			final String cityName = scanner.nextLine().substring(1);
			problem.getCities().add(new City(cityName, cityX, cityY));
		}
		final long startTime = System.nanoTime();
		final GeneticSolver solver = new GeneticSolver(problem,
				new Parameters(NB_GENERATIONS, SIZE_POPULATION, NB_CROSSINGS, NB_MUTATIONS, false));
		final TravelPath bestPath = (TravelPath) solver.solve();
		final long endTime = System.nanoTime();

		bestPath.print();
		scanner.close();
		System.out.println(String.format("%d;%d;%d;%d;%d;%d", NB_GENERATIONS, SIZE_POPULATION, NB_CROSSINGS,
				NB_MUTATIONS, -problem.evaluateSolution(bestPath), (endTime - startTime) / 1000000));
	}
}