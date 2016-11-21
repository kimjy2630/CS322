package Project2_1;

import java.io.File;
import java.io.FileNotFoundException;
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
		} catch(FileNotFoundException e) {
			System.err.println("No input: e-nfa.txt");
			System.exit(0);
		}

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("State"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		String[] tmparr;

		tmparr = sc.nextLine().trim().split(",");

		ComparableSet<State> states = new ComparableSet<>();
		for(String s : tmparr)
			states.add(new State(s));

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("Input symbol"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ComparableSet<Symbol> symbols = new ComparableSet<>();
		for(String s : tmparr)
			symbols.add(new Symbol(s));

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("State transition function"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in e-ndfa.txt");
			System.exit(0);
		}

		String temp;
		NondeterministicFunction<Pair<State, Symbol>, State> transFunc = new NondeterministicFunction<>();

		while(sc.hasNextLine() && !(temp = sc.nextLine().trim()).equals("Initial state")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			State nextState = new State(tmparr[2]);
			transFunc.addMapping(new Pair<>(prevState, symbol), nextState);
		}
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		State initState = new State(sc.nextLine().trim());
		while(sc.hasNextLine() && !sc.nextLine().trim().equals("Final state"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		ComparableSet<State> finalStates = new ComparableSet<>();
		tmparr = sc.nextLine().trim().split(",");
		for(String s : tmparr)
			finalStates.add(new State(s));

		// TODO
		sc.close();
		try {
			sc = new Scanner(new File("input.txt"));
		} catch(FileNotFoundException e) {
			System.err.println("No input: input.txt");
			System.exit(0);
		}

		NFA enfa = new NFA(states, symbols, transFunc, initState, finalStates);

		DFA dfa = eNFAToDFA(enfa);

		// dfa.printToFile("dfa.txt");

		// TODO dead state
		// State state_dead = new State("DEAD");
		// dfa.Q.add(state_dead);
		//
		// for(State q : dfa.Q)
		// for(Symbol s : dfa.S) {
		// Pair<State, Symbol> key = new Pair<State, Symbol>(q, s);
		// if(!dfa.func.getMapping().containsKey(key)) {
		// dfa.func.getMapping().put(key, state_dead);
		// }
		// }

		DFA mDFA = dfa.minimalize();
		mDFA.printToFile("m-dfa.txt");

		// TODO
		sc.close();
	}

	public static DFA eNFAToDFA(NFA enfa) {
		LinkedList<ComparableSet<State>> que = new LinkedList<>();
		ComparableSet<ComparableSet<State>> visited = new ComparableSet<>();
		ComparableSet<State> set = new ComparableSet<>();
		ComparableSet<State> init = new ComparableSet<>();

		HashMap<ComparableSet<State>, State> state_dfa = new HashMap<>();
		int cnt = 0;

		set.add(enfa.q0);
		set = getEpsillonClosure(set, enfa.func);
		init = set;

		que.add(set);

		Function<Pair<State, Symbol>, State> func_dfa = new Function<>();

		// TODO
		// System.out.println(enfa.func);

		state_dfa.put(set, new State("a" + cnt++));

		while(!que.isEmpty()) {
			set = que.poll();
			if(visited.contains(set)) {
				continue;
			}
			visited.add(set);

			for(Symbol s : enfa.S) {
				ComparableSet<State> next = getEpsillonClosure(getNext(getEpsillonClosure(set, enfa.func), enfa.func, s), enfa.func);

				// TODO
				// System.out.println("NEXT " + s + " " + next);
				if(!state_dfa.containsKey(next))
					state_dfa.put(next, new State("a" + cnt++));

				func_dfa.addMapping(new Pair<State, Symbol>(state_dfa.get(set), s), state_dfa.get(next));

				que.offer(next);
			}

			// TODO
			// System.out.println("SET " + set);
			// System.out.println("VISITED" + visited);
		}

		ComparableSet<State> finalStates = new ComparableSet<>();
		for(ComparableSet<State> q : visited) {
			ComparableSet<State> tmp = new ComparableSet<>();
			tmp.addAll(q);
			tmp.retainAll(enfa.F);
			if(!tmp.isEmpty())
				finalStates.add(state_dfa.get(q));
		}

		// TODO
		// System.out.println(visited);
		// System.out.println(state_dfa);
		// System.out.println(func_dfa);
		// System.out.println(finalStates);

		DFA dfa = new DFA(new ComparableSet<>(state_dfa.values()), enfa.S, func_dfa, state_dfa.get(init), finalStates);
		return dfa;
	}

	public static ComparableSet<State> getNext(ComparableSet<State> states, NondeterministicFunction<Pair<State, Symbol>, State> transFunc, Symbol symbol) {
		ComparableSet<State> ret = new ComparableSet<>();
		TreeMap<Pair<State, Symbol>, TreeSet<State>> map = transFunc.getMapping();
		for(State s : states) {
			Pair<State, Symbol> key = new Pair<>(s, symbol);
			if(map.containsKey(key)) {
				ret.addAll(map.get(key));
			}
		}
		return ret;
	}

	public static ComparableSet<State> getEpsillonClosure(ComparableSet<State> states, NondeterministicFunction<Pair<State, Symbol>, State> transFunc) {
		ComparableSet<State> ret = new ComparableSet<>(states);
		Symbol epsillon = new Symbol("E");
		TreeMap<Pair<State, Symbol>, TreeSet<State>> map = transFunc.getMapping();
		for(State s : states) {
			Pair<State, Symbol> key = new Pair<>(s, epsillon);
			if(map.containsKey(key)) {
				ret.addAll(map.get(key));
			}
		}
		if(states.containsAll(ret))
			return ret;
		return getEpsillonClosure(ret, transFunc);
	}
}
