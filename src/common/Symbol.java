package common;

public class Symbol implements Comparable<Symbol> {
	private String name;

	public Symbol(String name) {
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
		if(o instanceof Symbol)
			return name.equals(((State) o).getName());
		return false;
	}

	@Override
	public int compareTo(Symbol s) {
		return name.compareTo(s.getName());
	}
}
