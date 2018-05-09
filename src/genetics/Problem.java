package genetics;

public interface Problem<T extends Solution> {
	public T generateRandomSolution();

	public Integer evaluateSolution(T solution);

	public T mutateSolution(T solution);

	public T crossSolutions(T solution, T solution2);
}
