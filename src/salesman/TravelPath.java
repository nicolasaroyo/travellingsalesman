package salesman;

import java.util.ArrayList;
import java.util.List;

import genetics.Solution;

public class TravelPath implements Solution {
	private final List<String> cities = new ArrayList<>();

	public List<String> getCities() {
		return this.cities;
	}

	@Override
	public void printForDebug() {
		final StringBuilder sb = new StringBuilder(this.cities.get(0));
		for (int i = 1; i < this.cities.size(); i++) {
			sb.append(";").append(this.cities.get(i));
		}
		System.out.println(sb.toString());
	}

	public void print() {
		this.cities.forEach(System.out::println);
	}

	public boolean check() {
		return this.cities.size() == 51;
	}
}
