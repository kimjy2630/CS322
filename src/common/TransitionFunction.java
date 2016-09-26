package common;

import java.util.*;

public class TransitionFunction {

	private TreeMap<Pair<State, Symbol>, State> mapping;

	public TransitionFunction() {
		mapping = new TreeMap<>();
	}

	public boolean addMapping(State prevState, Symbol symbol, State nextState) {
		Pair<State, Symbol> key = new Pair<>(prevState, symbol);
		if(!mapping.containsKey(key)) {
			mapping.put(key, nextState);
			return true;
		}
		return false;
	}

	public TreeMap<Pair<State, Symbol>, State> getMapping() {
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
