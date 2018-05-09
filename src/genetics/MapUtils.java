package genetics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Credits to user Carter Page on stackoverflow :
 * https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
 *
 */
public class MapUtils {

	private MapUtils() {
		// Utils class
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
		final List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		final Map<K, V> result = new LinkedHashMap<>();
		for (final Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}
