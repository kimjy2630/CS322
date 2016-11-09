package common;

public class FiniteAutomata {
	public ComparableSet<State> Q;
	public ComparableSet<Symbol> S;
	public State q0;
	public ComparableSet<State> F;

	public FiniteAutomata(ComparableSet<State> Q, ComparableSet<Symbol> S, State q0, ComparableSet<State> F) {
		this.Q = Q;
		this.S = S;
		this.q0 = q0;
		this.F = F;
	}
}
