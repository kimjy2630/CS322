package common;

import java.util.*;

public class NondeterministicFunction<D, R> {

	private TreeMap<D, TreeSet<R>> mapping;

	public NondeterministicFunction() {
		mapping = new TreeMap<>();
	}

	public boolean addMapping(D key, R value) {
		if(!mapping.containsKey(key)) {
			TreeSet<R> list = new TreeSet<>();
			list.add(value);
			mapping.put(key, list);
			return true;
		}
		mapping.get(key).add(value);
		return false;
	}
	// public boolean addMapping(State prevState, Symbol symbol, T value) {
	// Pair<State, Symbol> key = new Pair<>(prevState, symbol);
	// if(!mapping.containsKey(key)) {
	// mapping.put(key, value);
	// return true;
	// }
	// return false;
	// }

	public TreeMap<D, TreeSet<R>> getMapping() {
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
