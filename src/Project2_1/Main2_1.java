package Project2_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import common.ComparableSet;
import common.Function;
import common.NondeterministicFunction;
import common.Pair;
import common.State;
import common.Symbol;

public class Main2_1 {
	public static void main(String[] args) {

	}

	public static void eNFAToDFA(ArrayList<State> Q, ArrayList<Symbol> S, State q,
			NondeterministicFunction<Pair<State, Symbol>, State> func) {
		LinkedList<ComparableSet<State>> que = new LinkedList<>();
		ComparableSet<ComparableSet<State>> visited = new ComparableSet<>();
		ComparableSet<State> set = new ComparableSet<>();
//		ComparableSet<State> zero, one;

		HashMap<ComparableSet<State>, State> state_dfa = new HashMap<>();
		int cnt = 0;

		set.add(q);
		set = getEpsillonClosure(set, func);

		que.add(set);

		Function<Pair<State, Symbol>, State> func_dfa = new Function<>();

		System.out.println(func);

		// visited.add(set);
		// System.out.println(visited);
		// System.out.println(visited.contains(set));
		// set = new ComparableSet<>();
		// set.add(q[0]);
		// set.add(q[2]);
		// System.out.println(visited.contains(set));
		// System.exit(0);

		state_dfa.put(set, new State("a" + cnt++));

		while (!que.isEmpty()) {
			set = que.poll();
			if (visited.contains(set)) {
				// System.out.println("ASDFASDFASDF");
				continue;
			}
			visited.add(set);

			for (Symbol s : S) {
				ComparableSet<State> next = getEpsillonClosure(getNext(getEpsillonClosure(set, func), func, s), func);
				
				if (!state_dfa.containsKey(next))
					state_dfa.put(next, new State("a" + cnt++));
				
				func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set), s), state_dfa.get(next));
				
				que.offer(next);
			}

//			zero = getEpsillonClosure(getNext(getEpsillonClosure(set, func), func, s[0]), func);
//			one = getEpsillonClosure(getNext(getEpsillonClosure(set, func), func, s[1]), func);

//			if (!state_dfa.containsKey(zero))
//				state_dfa.put(zero, new State("a" + cnt++));
//			if (!state_dfa.containsKey(one))
//				state_dfa.put(one, new State("a" + cnt++));

//			func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set), s[0]), state_dfa.get(zero));
//			func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set), s[1]), state_dfa.get(one));

			System.out.println("SET " + set);
//			System.out.println("ZERO " + zero);
//			System.out.println("ONE " + one);
			System.out.println("VISITED" + visited);
//			que.offer(zero);
//			que.offer(one);
		}

		System.out.println(visited);
		System.out.println(state_dfa);
		System.out.println(func_dfa);

	}

	public static ComparableSet<State> getNext(ComparableSet<State> states,
			NondeterministicFunction<Pair<State, Symbol>, State> transFunc, Symbol symbol) {
		ComparableSet<State> ret = new ComparableSet<>();
		TreeMap<Pair<State, Symbol>, TreeSet<State>> map = transFunc.getMapping();
		for (State s : states) {
			Pair<State, Symbol> key = new Pair<>(s, symbol);
			if (map.containsKey(key)) {
				ret.addAll(map.get(key));
			}
		}
		return ret;
	}

	public static ComparableSet<State> getEpsillonClosure(ComparableSet<State> states,
			NondeterministicFunction<Pair<State, Symbol>, State> transFunc) {
		ComparableSet<State> ret = new ComparableSet<>(states);
		Symbol epsillon = new Symbol("e");
		TreeMap<Pair<State, Symbol>, TreeSet<State>> map = transFunc.getMapping();
		for (State s : states) {
			Pair<State, Symbol> key = new Pair<>(s, epsillon);
			if (map.containsKey(key)) {
				ret.addAll(map.get(key));
			}
		}
		if (states.containsAll(ret))
			return ret;
		return getEpsillonClosure(ret, transFunc);
	}
}
