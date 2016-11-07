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
import common.Function;
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

		ArrayList<State> states = new ArrayList<>();
		for (String s : tmparr)
			states.add(new State(s));

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("Input symbol"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in e-nfa.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ArrayList<Symbol> symbols = new ArrayList<>();
		for (String s : tmparr)
			symbols.add(new Symbol(s));

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("State transition function"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format ine-ndfa.txt");
			System.exit(0);
		}

		String temp;
		Function<Pair<State, Symbol>, State> transFunc = new Function<>();

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

		ArrayList<State> finalStates = new ArrayList<>();
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
			pw = new PrintWriter(new File("output.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("Can't write output.txt");
			System.exit(0);
		}

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

		boolean firstline = true;

		fdsa: while (sc.hasNextLine()) {
			if (firstline)
				firstline = false;
			else
				pw.println();
			temp = sc.nextLine();
			ArrayList<String> inputs = new ArrayList<>();
			while (!temp.isEmpty()) {
				for (Symbol s : symbols) {
					if (temp.startsWith(s.getName())) {
						inputs.add(s.getName());
						temp = temp.substring(s.getName().length());
						break;
					}
				}
			}

			// for(String s : inputs)
			// System.out.print(s + " ");
			// System.out.println();

			State present = initState;

			boolean flag = true;

			TreeMap<Pair<State, Symbol>, State> mapping = transFunc.getMapping();
			asdf: for (String s : inputs) {
				Pair<State, Symbol> key = new Pair<>(present, new Symbol(s));
				// System.out.println(key);
				if (mapping.containsKey(key)) {
					present = mapping.get(key);
					continue asdf;
				}

				// for (String[] c : transFunc) {
				// if (c.length >= 3) {
				// if (c[0].equals(present) && c[1].equals(s)) {
				// present = c[2];
				// continue asdf;
				// }
				// }
				// }
				flag = false;
				break;
			}
			// System.out.println("FINAL: " + present);
			if (flag) {
				if (finalStates.contains(present)) {
					// System.out.println("��");
					pw.print("��");
					continue fdsa;
				}
				// else
				// System.out.println("ASDF");
			}
			// System.out.println("ASDF");
			// System.out.println("�ƴϿ�");
			pw.print("�ƴϿ�");
		}

		// TODO
		pw.flush();
		pw.close();

		// TODO
		sc.close();
	}

	public static void eNFAToDFA(ArrayList<State> Q, ArrayList<Symbol> S, State q,
			NondeterministicFunction<Pair<State, Symbol>, State> func) {
		LinkedList<ComparableSet<State>> que = new LinkedList<>();
		ComparableSet<ComparableSet<State>> visited = new ComparableSet<>();
		ComparableSet<State> set = new ComparableSet<>();
		// ComparableSet<State> zero, one;

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
