package common;

public class Symbol {
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
}
