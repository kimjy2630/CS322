import java.util.*;

public class TransitionFunction {
	class Pair<F, S> {
		private F first;
		private S second;

		public Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}

		public F getFirst() {
			return first;
		}

		public S getSecond() {
			return second;
		}
	}

	private HashMap<Pair<State, Symbol>, State> mapping;

	public TransitionFunction() {
		mapping = new HashMap<>();
	}

	public boolean addMapping(State prevState, Symbol symbol, State nextState) {
		Pair<State, Symbol> key = new Pair<>(prevState, symbol);
		if(!mapping.containsKey(key)) {
			mapping.put(key, nextState);
			return true;
		}
		return false;
	}
}
