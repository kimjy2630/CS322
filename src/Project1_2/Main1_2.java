package Project1_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import common.Pair;
import common.State;
import common.Symbol;
import common.Function;

public class Main1_2 {
	public static void main(String[] args) {
		// TODO
		Scanner sc = null;
		try {
			sc = new Scanner(new File("mealy.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No input: mealy.txt");
			System.exit(0);
		}

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("State"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in mealy.txt");
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
			System.err.println("Bad format in mealy.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ArrayList<Symbol> symbols = new ArrayList<>();
		for (String s : tmparr)
			symbols.add(new Symbol(s));

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("State transition function"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in mealy.txt");
			System.exit(0);
		}

		String temp;
		Function<Pair<State, Symbol>, State> transFunc = new Function<>();

		while (sc.hasNextLine() && !(temp = sc.nextLine().trim()).equals("Output symbol")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			State nextState = new State(tmparr[2]);
			transFunc.addMapping(new Pair<>(prevState, symbol), nextState);
		}
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in mealy.txt");
			System.exit(0);
		}

		// while(sc.hasNextLine() && !sc.nextLine().trim().equals("Output
		// symbol"))
		// ;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in mealy.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ArrayList<Symbol> outputSymbols = new ArrayList<>();
		for (String s : tmparr)
			outputSymbols.add(new Symbol(s));

		while (sc.hasNextLine() && !sc.nextLine().trim().equals("Output function"))
			;
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in mealy.txt");
			System.exit(0);
		}

		Function<Pair<State, Symbol>, Symbol> outputFunc = new Function<>();

		while (sc.hasNextLine() && !(temp = sc.nextLine().trim()).equals("Initial state")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			Symbol outputSymbol = new Symbol(tmparr[2]);
			outputFunc.addMapping(new Pair<>(prevState, symbol), outputSymbol);
		}
		if (!sc.hasNextLine()) {
			System.err.println("Bad format in mealy.txt");
			System.exit(0);
		}

		State initState = new State(sc.nextLine().trim());

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

		// System.out.println();
		// System.out.println(initState);
		// System.out.println();
		// for(State s : finalStates)
		// System.out.print(s + " ");

		boolean firstline = true;

		while (sc.hasNextLine()) {
			temp = sc.nextLine();
			if (temp.equals("end"))
				break;

			if (firstline)
				firstline = false;
			else
				pw.println();

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
			ArrayList<Symbol> output = new ArrayList<>();

			// for(String s : inputs)
			// System.out.print(s + " ");
			// System.out.println();

			State present = initState;

			boolean flag = true;

			TreeMap<Pair<State, Symbol>, State> mapping = transFunc.getMapping();
			TreeMap<Pair<State, Symbol>, Symbol> outMapping = outputFunc.getMapping();
			asdf: for (String s : inputs) {
				Pair<State, Symbol> key = new Pair<>(present, new Symbol(s));
				// System.out.println(key);
				if (mapping.containsKey(key)) {
					present = mapping.get(key);
					output.add(outMapping.get(key));
					continue asdf;
				}

				flag = false;
				break;
			}
			// System.out.println("FINAL: " + present);
			if (flag) {
				// pw.println(output);
				for (Symbol s : output)
					pw.print(s);
			} else
				pw.print("No path exists!");
		}

		// TODO
		pw.flush();
		pw.close();

		// TODO
		sc.close();

	}
}
