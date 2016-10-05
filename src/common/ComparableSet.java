package common;

import java.util.*;

import javax.net.ssl.*;

public class ComparableSet<T extends Comparable<T>> extends TreeSet<T> implements Comparable<ComparableSet<T>> {

	public ComparableSet() {
		super();
	}

	public ComparableSet(Collection<? extends T> c) {
		super(c);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof ComparableSet<?>) {
			ComparableSet<T> c = (ComparableSet<T>) o;
			return this.containsAll(c) && c.contains(this);
		}
		return false;
	}

	@Override
	public int compareTo(ComparableSet<T> o) {
		Iterator<T> it1 = this.iterator();
		Iterator<T> it2 = o.iterator();

		while(it1.hasNext() && it2.hasNext()) {
			int cmp = it1.next().compareTo(it2.next());
			if(cmp != 0)
				return cmp;
		}
		if(it1.hasNext())
			return 1;
		return -1;
	}
}
