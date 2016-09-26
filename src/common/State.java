package common;

public class State implements Comparable<State> {
	private String name;

	public State(String name) {
		this.name = name;
	}

	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof State)
			return this.name.equals(((State) o).getName());
		return false;
	}

	@Override
	public int compareTo(State s) {
		return this.name.compareTo(s.getName());
	}

}
