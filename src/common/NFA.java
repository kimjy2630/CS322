package common;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

public class NFA extends FiniteAutomata {
	public static final Symbol EPSILON = new Symbol("E");

	public NondeterministicFunction<Pair<State, Symbol>, State> func;

	public NFA(ComparableSet<State> Q, ComparableSet<Symbol> S,
			NondeterministicFunction<Pair<State, Symbol>, State> func, State q0, ComparableSet<State> F) {
		super(Q, S, q0, F);
		this.func = func;
	}

	public static NFA getTerminalNFA(String str) {
		State[] q = new State[str.length() + 1];
		for (int i = 0; i < q.length; ++i)
			q[i] = new State("q" + i);
		ComparableSet<State> Q = new ComparableSet<>();
		for (State s : q)
			Q.add(s);

		ComparableSet<Symbol> S = new ComparableSet<>();
		char[] ch = str.toCharArray();
		for (char c : ch)
			S.add(new Symbol(String.valueOf(c)));

		NondeterministicFunction<Pair<State, Symbol>, State> func = new NondeterministicFunction<>();
		for (int i = 0; i < str.length(); ++i)
			func.addMapping(new Pair<State, Symbol>(q[i], new Symbol(String.valueOf(ch[i]))), q[i + 1]);
		ComparableSet<State> F = new ComparableSet<>();
		F.add(q[q.length - 1]);
		return new NFA(Q, S, func, q[0], F);
	}

	public static NFA getEpsilonNFA() {
		State q0 = new State("q0");
		State q1 = new State("q1");
		ComparableSet<State> Q = new ComparableSet<>();
		Q.add(q0);
		Q.add(q1);

		ComparableSet<Symbol> S = new ComparableSet<>();

		NondeterministicFunction<Pair<State, Symbol>, State> func = new NondeterministicFunction<>();
		func.addMapping(new Pair<State, Symbol>(q0, EPSILON), q1);
		ComparableSet<State> F = new ComparableSet<>();
		F.add(q1);
		return new NFA(Q, S, func, q0, F);
	}

	public static NFA getUnion(NFA n1, NFA n2) {
		HashMap<State, State> n1tonfa = new HashMap<>();
		HashMap<State, State> n2tonfa = new HashMap<>();
		for (State s : n1.Q)
			n1tonfa.put(s, new State("u1_" + s.getName()));
		for (State s : n2.Q)
			n2tonfa.put(s, new State("u2_" + s.getName()));

		State q0 = new State("u_q0");
		State q1 = new State("u_q1");
		ComparableSet<State> Q = new ComparableSet<>();
		Q.addAll(n1tonfa.values());
		Q.addAll(n2tonfa.values());
		Q.add(q0);
		Q.add(q1);

		ComparableSet<Symbol> S = new ComparableSet<>();
		S.addAll(n1.S);
		S.addAll(n2.S);

		NondeterministicFunction<Pair<State, Symbol>, State> func = new NondeterministicFunction<>();
		for (Entry<Pair<State, Symbol>, TreeSet<State>> e : n1.func.getMapping().entrySet()) {
			Pair<State, Symbol> key = new Pair<>(n1tonfa.get(e.getKey().getFirst()), e.getKey().getSecond());
			for (State st : e.getValue())
				func.addMapping(key, n1tonfa.get(st));
		}
		for (Entry<Pair<State, Symbol>, TreeSet<State>> e : n2.func.getMapping().entrySet()) {
			Pair<State, Symbol> key = new Pair<>(n2tonfa.get(e.getKey().getFirst()), e.getKey().getSecond());
			for (State st : e.getValue())
				func.addMapping(key, n2tonfa.get(st));
		}
		func.addMapping(new Pair<>(q0, EPSILON), n1tonfa.get(n1.q0));
		func.addMapping(new Pair<>(q0, EPSILON), n2tonfa.get(n2.q0));
		func.addMapping(new Pair<>(n1tonfa.get(n1.F.first()), EPSILON), q1);
		func.addMapping(new Pair<>(n2tonfa.get(n2.F.first()), EPSILON), q1);

		ComparableSet<State> F = new ComparableSet<>();
		F.add(q1);

		return new NFA(Q, S, func, q0, F);
	}

	public static NFA getConcat(NFA n1, NFA n2) {
		HashMap<State, State> n1tonfa = new HashMap<>();
		HashMap<State, State> n2tonfa = new HashMap<>();
		for (State s : n1.Q)
			n1tonfa.put(s, new State("c1_" + s.getName()));
		for (State s : n2.Q)
			n2tonfa.put(s, new State("c2_" + s.getName()));

		ComparableSet<State> Q = new ComparableSet<>();
		Q.addAll(n1tonfa.values());
		Q.addAll(n2tonfa.values());

		ComparableSet<Symbol> S = new ComparableSet<>();
		S.addAll(n1.S);
		S.addAll(n2.S);

		NondeterministicFunction<Pair<State, Symbol>, State> func = new NondeterministicFunction<>();
		for (Entry<Pair<State, Symbol>, TreeSet<State>> e : n1.func.getMapping().entrySet()) {
			Pair<State, Symbol> key = new Pair<>(n1tonfa.get(e.getKey().getFirst()), e.getKey().getSecond());
			for (State st : e.getValue())
				func.addMapping(key, n1tonfa.get(st));
		}
		for (Entry<Pair<State, Symbol>, TreeSet<State>> e : n2.func.getMapping().entrySet()) {
			Pair<State, Symbol> key = new Pair<>(n2tonfa.get(e.getKey().getFirst()), e.getKey().getSecond());
			for (State st : e.getValue())
				func.addMapping(key, n2tonfa.get(st));
		}
		func.addMapping(new Pair<>(n1tonfa.get(n1.F.first()), EPSILON), n2tonfa.get(n2.q0));

		ComparableSet<State> F = new ComparableSet<>();
		F.add(n2tonfa.get(n2.F.first()));

		return new NFA(Q, S, func, n1tonfa.get(n1.q0), F);
	}

	public static NFA getClosure(NFA nfa) {
		State q0 = new State("cl_" + nfa.q0.getName());
		State q1 = new State("cl_" + nfa.F.first().getName());
		ComparableSet<State> Q = new ComparableSet<>(nfa.Q);
		Q.add(q0);
		Q.add(q1);

		ComparableSet<Symbol> S = new ComparableSet<>(nfa.S);

		NondeterministicFunction<Pair<State, Symbol>, State> func = new NondeterministicFunction<>();
		func.getMapping().putAll(nfa.func.getMapping());
		func.addMapping(new Pair<State, Symbol>(q0, EPSILON), q1);
		func.addMapping(new Pair<State, Symbol>(q0, EPSILON), nfa.q0);
		func.addMapping(new Pair<State, Symbol>(nfa.F.first(), EPSILON), q1);
		func.addMapping(new Pair<State, Symbol>(nfa.F.first(), EPSILON), nfa.q0);

		ComparableSet<State> F = new ComparableSet<>();
		F.add(q1);

		return new NFA(Q, S, func, q0, F);
	}
}
