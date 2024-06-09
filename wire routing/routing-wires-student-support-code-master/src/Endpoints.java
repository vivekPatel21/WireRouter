public class Endpoints {

	public final int   id;
	public final Coord start, end;

	public Endpoints(final int id, final Coord start, final Coord end) {
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public String toString() {
	    return start + "--" + end;
    }
}
