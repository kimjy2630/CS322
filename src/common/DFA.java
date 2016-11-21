package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class DFA extends FiniteAutomata {
	public Function<Pair<State, Symbol>, State> func;

	public DFA(ComparableSet<State> Q, ComparableSet<Symbol> S, Function<Pair<State, Symbol>, State> func, State q0, ComparableSet<State> F) {
		super(Q, S, q0, F);
		this.func = func;
	}

	public DFA minimalize() {
		int n = Q.size();
		int[][] check = new int[n][n];

		State[] states = (State[]) Q.toArray(new State[Q.size()]);

		HashMap<State, Integer> dfaToInt = new HashMap<>();
		for(int i = 0; i < n; ++i)
			dfaToInt.put(states[i], i);

		// TODO
		// System.out.println("INIT CHECK");
		// for(int[] tmp : check)
		// System.out.println(Arrays.toString(tmp));

		for(int i = 0; i < n; ++i) {
			for(int j = i + 1; j < n; ++j) {
				if(F.contains(states[i]) && !F.contains(states[j]))
					check[i][j] = check[j][i] = 1;
				else if(!F.contains(states[i]) && F.contains(states[j]))
					check[i][j] = check[j][i] = 1;
			}
		}

		// TODO
		// System.out.println("CHECK 2");
		// for(int[] tmp : check)
		// System.out.println(Arrays.toString(tmp));

		for(int i = 0; i < n; ++i)
			check[i][i] = -1;

		boolean end = false;
		while(!end) {
			end = true;
			for(int i = 0; i < n; ++i) {
				for(int j = i + 1; j < n; ++j) {
					if(check[i][j] == 0) {
						for(Symbol s : S) {
							State q1 = func.getMapping().get(new Pair<State, Symbol>(states[i], s));
							State q2 = func.getMapping().get(new Pair<State, Symbol>(states[j], s));
							int index1 = dfaToInt.get(q1);
							int index2 = dfaToInt.get(q2);

							if(check[index1][index2] == 1) {
								check[i][j] = check[j][i] = 1;
								end = false;
								//TODO
								// System.out.println("CHECK FALSE");
								// System.out.println(index1 + " " + index2);
							}
						}
					}
				}
			}
			// System.out.println("WHILE " + end);
		}
		State[] created = new State[n];
		ComparableSet<State> state_mdfa = new ComparableSet<>();
		HashMap<Integer, State> intTomDFA = new HashMap<>();

		int cnt = 0;
		for(int i = 0; i < n; ++i) {
			if(created[i] == null) {
				State q = new State("q" + cnt++);
				state_mdfa.add(q);
				for(int j = 0; j < n; ++j) {
					// if(check[i][j] == 0) {
					if(check[i][j] <= 0) {
						created[j] = q;
						intTomDFA.put(j, q);
					}
				}
			}
		}

		Function<Pair<State, Symbol>, State> func_mdfa = new Function<>();
		for(Pair<State, Symbol> entry : func.getMapping().keySet()) {
			Pair<State, Symbol> key = new Pair<State, Symbol>(intTomDFA.get(dfaToInt.get(entry.getFirst())), entry.getSecond());
			if(!func_mdfa.getMapping().containsKey(key)) {
				// TODO
				// System.out.println("INPUT");
				// System.out.println(func.getMapping().get(entry));
				// System.out.println(dfaToInt.get(func.getMapping().get(key)));
				// System.out.println(intTomDFA.get(dfaToInt.get(func.getMapping().get(key))));

				func_mdfa.getMapping().put(key, intTomDFA.get(dfaToInt.get(func.getMapping().get(entry))));
				// TODO
				// System.out.println("INPUT END");
			}
		}

		State init_mdfa = intTomDFA.get(dfaToInt.get(q0));
		ComparableSet<State> final_mdfa = new ComparableSet<>();
		for(State q : F) {
			final_mdfa.add(intTomDFA.get(dfaToInt.get(q)));
		}

		// TODO
		// for(int[] tmp : check)
		// System.out.println(Arrays.toString(tmp));
		// System.out.println(dfaToInt);
		// System.out.println(intTomDFA);
		// System.out.println("mDFA");
		// System.out.println(state_mdfa);
		// System.out.println(func_mdfa);

		DFA mDFA = new DFA(state_mdfa, S, func_mdfa, init_mdfa, final_mdfa);
		return mDFA;
	}

	public void printToFile(String filename) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
		} catch(FileNotFoundException e) {
			System.err.println("Can't write " + filename);
			System.exit(0);
		}

		pw.println("State");
		boolean firstline = true;
		for(State q : Q) {
			if(!firstline)
				pw.print(',');
			pw.print(q);
			firstline = false;
		}
		pw.println();
		pw.println("Input symbol");
		firstline = true;
		for(Symbol s : S) {
			if(!firstline)
				pw.print(',');
			pw.print(s);
			firstline = false;
		}
		pw.println();
		pw.println("State transition function");
		for(Pair<State, Symbol> entry : func.getMapping().keySet()) {
			pw.print(entry.getFirst());
			pw.print(',');
			pw.print(entry.getSecond());
			pw.print(',');
			pw.print(func.getMapping().get(entry));
			pw.println();
		}
		pw.println("Initial state");
		pw.println(q0);
		pw.println("Final state");
		firstline = true;
		for(State q : F) {
			if(!firstline)
				pw.print(',');
			pw.print(q);
			firstline = false;
		}
		pw.println();

		pw.flush();
		pw.close();
	}
}
