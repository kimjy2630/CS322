package common;

import java.util.*;

public class Function<T> {

	private TreeMap<Pair<State, Symbol>, T> mapping;

	public Function() {
		mapping = new TreeMap<>();
	}

	public boolean addMapping(State prevState, Symbol symbol, T value) {
		Pair<State, Symbol> key = new Pair<>(prevState, symbol);
		if(!mapping.containsKey(key)) {
			mapping.put(key, value);
			return true;
		}
		return false;
	}

	public TreeMap<Pair<State, Symbol>, T> getMapping() {
		return mapping;
	}

	@Override
	public String toString() {
		// for(Entry c : mapping) {
		// for(String s : c)
		// System.out.print(s + " ");
		// System.out.println();
		// }
		return mapping.toString();
	}
}
