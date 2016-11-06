package common;

import java.util.*;

public class HW3 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		NondeterministicFunction<Pair<State, Symbol>, State> func = new NondeterministicFunction<>();

		ArrayList<Symbol> symbols = new ArrayList<>();
		Symbol[] s = new Symbol[2];
		Symbol epsillon;
		for (int i = 0; i < 2; ++i)
			symbols.add(s[i] = new Symbol(i + ""));
		symbols.add(epsillon = new Symbol("e"));

		State[] q = new State[14];
		ArrayList<State> states = new ArrayList<>();
		for (int i = 0; i < 14; ++i)
			states.add(q[i] = new State("q" + i));

		func.addMapping(new Pair<>(q[0], epsillon), q[2]);
		func.addMapping(new Pair<>(q[1], s[0]), q[2]);
		func.addMapping(new Pair<>(q[1], s[1]), q[2]);
		func.addMapping(new Pair<>(q[2], s[0]), q[1]);
		func.addMapping(new Pair<>(q[2], s[1]), q[1]);
		func.addMapping(new Pair<>(q[2], s[1]), q[5]);
		func.addMapping(new Pair<>(q[5], s[0]), q[7]);
		func.addMapping(new Pair<>(q[7], s[1]), q[10]);
		func.addMapping(new Pair<>(q[9], s[0]), q[10]);
		func.addMapping(new Pair<>(q[9], s[1]), q[10]);
		func.addMapping(new Pair<>(q[10], s[0]), q[9]);
		func.addMapping(new Pair<>(q[10], s[1]), q[9]);
		func.addMapping(new Pair<>(q[10], s[0]), q[13]);
		func.addMapping(new Pair<>(q[10], s[1]), q[13]);

		func.addMapping(new Pair<>(q[0], s[0]), q[3]);
		func.addMapping(new Pair<>(q[0], s[1]), q[3]);
		func.addMapping(new Pair<>(q[3], s[0]), q[4]);
		func.addMapping(new Pair<>(q[3], s[1]), q[4]);
		func.addMapping(new Pair<>(q[4], s[0]), q[3]);
		func.addMapping(new Pair<>(q[4], s[1]), q[3]);
		func.addMapping(new Pair<>(q[3], s[1]), q[6]);
		func.addMapping(new Pair<>(q[6], s[0]), q[8]);
		func.addMapping(new Pair<>(q[8], s[1]), q[11]);
		func.addMapping(new Pair<>(q[11], s[0]), q[12]);
		func.addMapping(new Pair<>(q[11], s[1]), q[12]);
		func.addMapping(new Pair<>(q[12], s[0]), q[11]);
		func.addMapping(new Pair<>(q[12], s[1]), q[11]);
		func.addMapping(new Pair<>(q[11], epsillon), q[13]);

		LinkedList<ComparableSet<State>> que = new LinkedList<>();
		ComparableSet<ComparableSet<State>> visited = new ComparableSet<>();
		ComparableSet<State> set = new ComparableSet<>();
		ComparableSet<State> zero, one;

		HashMap<ComparableSet<State>, State> state_dfa = new HashMap<>();
		int cnt = 0;

		set.add(q[0]);
		set.add(q[2]);

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
			

			zero = getEpsillonClosure(getNext(getEpsillonClosure(set, func), func, s[0]), func);
			one = getEpsillonClosure(getNext(getEpsillonClosure(set, func), func, s[1]), func);
			
			if(!state_dfa.containsKey(zero))
				state_dfa.put(zero, new State("a" + cnt++));
			if(!state_dfa.containsKey(one))
				state_dfa.put(one, new State("a" + cnt++));
			
			func_dfa.addMapping(new Pair<State,Symbol>(state_dfa.get(set), s[0]), state_dfa.get(zero));
			func_dfa.addMapping(new Pair<State,Symbol>(state_dfa.get(set), s[1]), state_dfa.get(one));
				
			System.out.println("SET " + set);
			System.out.println("ZERO " + zero);
			System.out.println("ONE " + one);
			System.out.println("VISITED" + visited);
			que.offer(zero);
			que.offer(one);
		}

		System.out.println(visited);
		System.out.println(state_dfa);
		System.out.println(func_dfa);
		

		sc.close();
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
