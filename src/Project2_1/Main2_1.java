package Project2_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import common.ComparableSet;
import common.DFA;
import common.Function;
import common.NFA;
import common.NondeterministicFunction;
import common.Pair;
import common.State;
import common.Symbol;

public class Main2_1 {
	public static void main(String[] args) {

		// TODO
		Scanner sc = null;
		try {
			sc = new Scanner(new File("e-nfa.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No input: e-nfa.txt");
			System.exit(0);
		}

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("State"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in dfa.txt");
			System.exit(0);
		}

		String[] tmparr;

		tmparr = sc.nextLine().trim().split(",");

		ComparableSet<State> states = new ComparableSet<>();
		for (String s : tmparr)
			states.add(new State(s));

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("Input symbol"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ComparableSet<Symbol> symbols = new ComparableSet<>();
		for (String s : tmparr)
			symbols.add(new Symbol(s));

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("State transition function"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format ine-ndfa.txt");
			System.exit(0);
		}

		String temp;
		NondeterministicFunction<Pair<State, Symbol>, State> transFunc = new NondeterministicFunction<>();

		while (sc.hasNextLine() && !(temp = sc.nextLine().trim()).equals("Initial state")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			State nextState = new State(tmparr[2]);
			transFunc.addMapping(new Pair<>(prevState, symbol), nextState);
		}
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		State initState = new State(sc.nextLine().trim());
		while (sc.hasNextLine() && !sc.nextLine().trim().equals("Final state"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		ComparableSet<State> finalStates = new ComparableSet<>();
		tmparr = sc.nextLine().trim().split(",");
		for (String s : tmparr)
			finalStates.add(new State(s));

		// TODO
		sc.close();
		try {
			sc = new Scanner(new File("input.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No input: input.txt");
			System.exit(0);
		}

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("dfa.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("Can't write dfa.txt");
			System.exit(0);
		}

		NFA enfa = new NFA(states, symbols, transFunc, initState, finalStates);

		// eNFAToDFA(states, symbols, transFunc, initState, finalStates);
		DFA dfa = eNFAToDFA(enfa);

		DFA mDFA = dfa.minimalize();
		mDFA.printToFile("m-dfa.txt");

		// for(State s : states)
		// System.out.print(s + " ");
		// System.out.println();
		// for(Symbol s : symbols)
		// System.out.print(s + " ");
		// System.out.println();
		// System.out.println(transFunc);
		//
		// System.out.println();
		// System.out.println(initState);
		// System.out.println();
		// for(State s : finalStates)
		// System.out.print(s + " ");

		// TODO
		pw.flush();
		pw.close();

		// TODO
		sc.close();
	}

	// public static void eNFAToDFA(ArrayList<State> Q, ArrayList<Symbol> S,
	// NondeterministicFunction<Pair<State, Symbol>, State> func, State q0,
	// ArrayList<State> F) {
	public static DFA eNFAToDFA(NFA enfa) {
		LinkedList<ComparableSet<State>> que = new LinkedList<>();
		ComparableSet<ComparableSet<State>> visited = new ComparableSet<>();
		ComparableSet<State> set = new ComparableSet<>();
		ComparableSet<State> init = new ComparableSet<>();
		// ComparableSet<State> zero, one;

		HashMap<ComparableSet<State>, State> state_dfa = new HashMap<>();
		int cnt = 0;

		set.add(enfa.q0);
		set = getEpsillonClosure(set, enfa.func);
		init = set;

		que.add(set);

		Function<Pair<State, Symbol>, State> func_dfa = new Function<>();

		System.out.println(enfa.func);

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

			for (Symbol s : enfa.S) {
				ComparableSet<State> next = getEpsillonClosure(
						getNext(getEpsillonClosure(set, enfa.func), enfa.func, s), enfa.func);

				System.out.println("NEXT " + s + " " + next);
				// System.out.println(next.hashCode());
				// System.out.println("KEY "+state_dfa.keySet());
				if (!state_dfa.containsKey(next))
					state_dfa.put(next, new State("a" + cnt++));

				// System.out.println(state_dfa.get(next));

				func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set), s), state_dfa.get(next));

				que.offer(next);
			}

			// zero = getEpsillonClosure(getNext(getEpsillonClosure(set, func),
			// func, s[0]), func);
			// one = getEpsillonClosure(getNext(getEpsillonClosure(set, func),
			// func, s[1]), func);

			// if (!state_dfa.containsKey(zero))
			// state_dfa.put(zero, new State("a" + cnt++));
			// if (!state_dfa.containsKey(one))
			// state_dfa.put(one, new State("a" + cnt++));

			// func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set),
			// s[0]), state_dfa.get(zero));
			// func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set),
			// s[1]), state_dfa.get(one));

			System.out.println("SET " + set);
			// System.out.println("ZERO " + zero);
			// System.out.println("ONE " + one);
			System.out.println("VISITED" + visited);
			// que.offer(zero);
			// que.offer(one);
		}

		ComparableSet<State> finalStates = new ComparableSet<>();
		for (ComparableSet<State> q : visited) {
			ComparableSet<State> tmp = new ComparableSet<>();
			tmp.addAll(q);
			tmp.retainAll(enfa.F);
			if (!tmp.isEmpty())
				finalStates.add(state_dfa.get(q));
		}

		System.out.println(visited);
		System.out.println(state_dfa);
		System.out.println(func_dfa);
		System.out.println(finalStates);

		DFA dfa = new DFA(new ComparableSet<>(state_dfa.values()), enfa.S, func_dfa, state_dfa.get(init), finalStates);
		return dfa;
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
		Symbol epsillon = new Symbol("E");
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
