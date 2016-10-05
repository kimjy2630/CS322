package common;

public class Pair<F extends Comparable<F>, S extends Comparable<S>> implements Comparable<Pair<F, S>> {
	private F first;
	private S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Pair<?, ?>)
			return first.equals(((Pair<?, ?>) o).getFirst()) && second.equals(((Pair<?, ?>) o).getSecond());
		return false;
	}

	@Override
	public int compareTo(Pair<F, S> o) {
		int comp1 = first.compareTo(o.getFirst());
		if(comp1 == 0)
			return second.compareTo(o.getSecond());
		return comp1;
	}
}