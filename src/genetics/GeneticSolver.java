package genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneticSolver {

	final Problem<Solution> problem;

	final Parameters parameters;

	final Random random = new Random();

	public GeneticSolver(final Problem problem, final Parameters parameters) {
		this.problem = problem;
		this.parameters = parameters;
	}

	public Solution solve() {
		List<Solution> population = this.generateInitialPopulation();
		population = this.sortByDescendingEvaluation(population);

		for (int i = 0; i < this.parameters.getNbGenerations(); i++) {
			final List<Solution> crossings = this.generateCrossings(population);
			final List<Solution> mutations = this.generateMutations(population);
			population.addAll(crossings);
			population.addAll(mutations);
			population = this.sortByDescendingEvaluation(population);
			population = population.subList(0, this.parameters.getSizePopulation());
			if (this.parameters.isDebug()) {
				population.get(0).printForDebug();
			}
		}

		return population.get(0);
	}

	private List<Solution> generateMutations(final List<Solution> population) {
		final List<Solution> mutations = new ArrayList<>();
		for (int i = 0; i < this.parameters.getNbMutations(); i++) {
			final int solutionToMutate = this.random.nextInt(population.size());
			mutations.add(this.problem.mutateSolution(population.get(solutionToMutate)));
		}
		return mutations;
	}

	private List<Solution> generateCrossings(final List<Solution> population) {
		final List<Solution> crossings = new ArrayList<>();
		for (int i = 0; i < this.parameters.getNbCrossings(); i++) {
			final int firstParent = this.random.nextInt(population.size());
			int secondParent;
			do {
				secondParent = this.random.nextInt(population.size());
			} while (secondParent == firstParent);
			crossings.add(this.problem.crossSolutions(population.get(firstParent), population.get(secondParent)));
		}
		return crossings;
	}

	private List<Solution> generateInitialPopulation() {
		final List<Solution> population = new ArrayList<>();
		for (int i = 0; i < this.parameters.getSizePopulation(); i++) {
			population.add(this.problem.generateRandomSolution());
		}
		return population;
	}

	private List<Solution> sortByDescendingEvaluation(final List<Solution> list) {
		final Map<Solution, Integer> solutionsWithEvaluations = list.stream()
				.collect(Collectors.toMap(Function.identity(), this.problem::evaluateSolution));
		final Map<Solution, Integer> solutionsWithEvaluationsSorted = MapUtils.sortByValue(solutionsWithEvaluations);
		final List<Solution> solutionsSorted = new ArrayList<>();
		solutionsWithEvaluationsSorted.forEach((k, v) -> solutionsSorted.add(k));
		Collections.reverse(solutionsSorted);
		return solutionsSorted;
	}
}
