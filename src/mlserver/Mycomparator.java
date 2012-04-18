package mlserver;

import java.util.Comparator;

public class Mycomparator implements Comparator<Rank> {
	public int compare(Rank o1, Rank o2) {
		Rank p1 = (Rank) o1;
		Rank p2 = (Rank) o2;
		if (p1.getValue() < p2.getValue())
			return 1;
		else
			return 0;
	}
}
