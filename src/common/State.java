package common;

public class State {
	private String name;

	public State(String name) {
		this.name = name;
	}
	
	public String getName(){
		return toString();
	}

	@Override
	public String toString() {
		return name;
	}

}
