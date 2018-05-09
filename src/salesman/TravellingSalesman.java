package salesman;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import genetics.Problem;

public class TravellingSalesman implements Problem<TravelPath> {
	final List<City> cities = new ArrayList<>();

	protected List<City> getCities() {
		return this.cities;
	}

	@Override
	public TravelPath generateRandomSolution() {
		final List<String> cityNames = this.cities.stream().map(City::getName).collect(toList());
		Collections.shuffle(cityNames);
		final TravelPath randomSolution = new TravelPath();
		randomSolution.getCities().addAll(cityNames);

		return randomSolution;
	}

	private City findCityByName(final String name) {
		return this.cities.stream().filter(city -> city.getName().equals(name)).findFirst().orElse(null);
	}

	private BigDecimal calculateDistance(final City city1, final City city2) {
		final Integer xDistance = city2.getX() - city1.getX();
		final Integer yDistance = city2.getY() - city1.getY();
		final double distanceSquared = Math.pow(xDistance, 2) + Math.pow(yDistance, 2);
		return BigDecimal.valueOf(Math.sqrt(distanceSquared));
	}

	private BigDecimal calculateDistance(final List<City> path) {
		BigDecimal distance = BigDecimal.ZERO;
		City previousCity = path.get(path.size() - 1);
		for (final City city : path) {
			final BigDecimal currentDistance = this.calculateDistance(previousCity, city);
			previousCity = city;
			distance = distance.add(currentDistance);
		}
		return distance;
	}

	private Integer calculateDistance(final TravelPath path) {
		final List<City> chosenPath = path.getCities().stream().map(this::findCityByName).collect(toList());
		final BigDecimal totalDistance = this.calculateDistance(chosenPath);
		return totalDistance.setScale(0, RoundingMode.HALF_UP).intValue();
	}

	@Override
	public Integer evaluateSolution(final TravelPath solution) {
		final Integer distance = this.calculateDistance(solution);
		return -distance;
	}

	@Override
	public TravelPath mutateSolution(final TravelPath solution) {
		final Random random = new Random();
		final int firstIndex = random.nextInt(solution.getCities().size());
		final int secondIndex = random.nextInt(solution.getCities().size());

		final TravelPath mutatedSolution = new TravelPath();
		mutatedSolution.getCities().addAll(solution.getCities());
		mutatedSolution.getCities().set(firstIndex, solution.getCities().get(secondIndex));
		mutatedSolution.getCities().set(secondIndex, solution.getCities().get(firstIndex));

		return mutatedSolution;
	}

	@Override
	public TravelPath crossSolutions(final TravelPath solution1, final TravelPath solution2) {
		final int crossingPoint = solution1.getCities().size() / 2;

		final List<String> crossedCities = new ArrayList<>(solution1.getCities().subList(0, crossingPoint));
		crossedCities.addAll(solution2.getCities().subList(crossingPoint, solution2.getCities().size()));

		final List<String> citiesNotInCrossed = this.cities.stream().map(City::getName)
				.filter(city -> !crossedCities.contains(city)).collect(toList());
		Collections.shuffle(citiesNotInCrossed);

		final List<String> citiesAlreadyVisited = new ArrayList<>();
		for (int i = 0; i < crossedCities.size(); i++) {
			final String currentCity = crossedCities.get(i);
			if (citiesAlreadyVisited.contains(currentCity)) {
				final String cityToAdd = citiesNotInCrossed.get(0);
				crossedCities.set(i, cityToAdd);
				citiesAlreadyVisited.add(cityToAdd);
				citiesNotInCrossed.remove(cityToAdd);
			} else {
				citiesAlreadyVisited.add(currentCity);
			}
		}

		final TravelPath crossedSolution = new TravelPath();
		crossedSolution.getCities().addAll(crossedCities);
		return crossedSolution;
	}
}
