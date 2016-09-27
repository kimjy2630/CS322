package Project1_1;

import java.io.*;
import java.util.*;
import common.*;

public class Main {
	public static void main(String[] args) {

		// TODO
		Scanner sc = null;
		try {
			sc = new Scanner(new File("dfa.txt"));
		} catch(FileNotFoundException e) {
			System.err.println("No input: dfa.txt");
			System.exit(0);
		}

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("State"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa.txt");
			System.exit(0);
		}

		String[] tmparr;

		tmparr = sc.nextLine().trim().split(",");

		ArrayList<State> states = new ArrayList<>();
		for(String s : tmparr)
			states.add(new State(s));

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("Input symbol"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ArrayList<Symbol> symbols = new ArrayList<>();
		for(String s : tmparr)
			symbols.add(new Symbol(s));

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("State transition function"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa.txt");
			System.exit(0);
		}

		String temp;
		Function<State> transFunc = new Function<>();

		while(sc.hasNextLine() && !(temp = sc.nextLine().trim()).equals("Initial state")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			State nextState = new State(tmparr[2]);
			transFunc.addMapping(prevState, symbol, nextState);
		}
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa.txt");
			System.exit(0);
		}

		State initState = new State(sc.nextLine().trim());
		while(sc.hasNextLine() && !sc.nextLine().trim().equals("Final state"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa.txt");
			System.exit(0);
		}

		ArrayList<State> finalStates = new ArrayList<>();
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

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("output.txt"));
		} catch(FileNotFoundException e) {
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

		fdsa: while(sc.hasNextLine()) {
			temp = sc.nextLine();
			ArrayList<String> inputs = new ArrayList<>();
			while(!temp.isEmpty()) {
				for(Symbol s : symbols) {
					if(temp.startsWith(s.getName())) {
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
			asdf: for(String s : inputs) {
				Pair<State, Symbol> key = new Pair<>(present, new Symbol(s));
				// System.out.println(key);
				if(mapping.containsKey(key)) {
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
			if(flag) {
				if(finalStates.contains(present)) {
					// System.out.println("��");
					pw.println("��");
					continue fdsa;
				}
				// else
				// System.out.println("ASDF");
			}
			// System.out.println("ASDF");
			// System.out.println("�ƴϿ�");
			pw.println("�ƴϿ�");
		}

		// TODO
		pw.flush();
		pw.close();

		// TODO
		sc.close();
	}
}
