package common;

public class NFA extends FiniteAutomata {
	public NondeterministicFunction<Pair<State, Symbol>, State> func;

	public NFA(ComparableSet<State> Q, ComparableSet<Symbol> S,
			NondeterministicFunction<Pair<State, Symbol>, State> func, State q0, ComparableSet<State> F) {
		super(Q, S, q0, F);
		this.func = func;
	}
}
