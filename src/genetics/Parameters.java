package genetics;

public class Parameters {
	private final int nbGenerations;
	private final int sizePopulation;
	private final int nbCrossings;
	private final int nbMutations;
	private final boolean debug;

	public Parameters(final int nbGenerations, final int sizePopulation, final int nbCrossings, final int nbMutations,
			final boolean debug) {
		this.nbGenerations = nbGenerations;
		this.sizePopulation = sizePopulation;
		this.nbCrossings = nbCrossings;
		this.nbMutations = nbMutations;
		this.debug = debug;
	}

	public int getNbGenerations() {
		return this.nbGenerations;
	}

	public int getSizePopulation() {
		return this.sizePopulation;
	}

	public int getNbMutations() {
		return this.nbMutations;
	}

	public int getNbCrossings() {
		return this.nbCrossings;
	}

	public boolean isDebug() {
		return this.debug;
	}
}
