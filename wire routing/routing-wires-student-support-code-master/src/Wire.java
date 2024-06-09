import java.util.ArrayList;
import java.util.List;

public class Wire {

	public final int id;
	private ArrayList<Coord> points;

	Wire(final int id, List<Coord> points) {
		this.id = id;
		this.points = new ArrayList<>(points);
	}

	Wire(final int id) {
		this.id = id;
		this.points = new ArrayList<>();
	}

	public int length() {
		return this.points.size();
	}

	public Coord get(int step_id) {
		return this.points.get(step_id);
	}

	public void add(Coord step) {
		this.points.add(step);
	}

	public Coord start() {
		return this.points.get(0);
	}

	public Coord end() {
		return this.points.get(this.points.size() - 1);
	}

	public ArrayList<Coord> getPoints() {
		return new ArrayList<>(this.points);
	}

	public String toString() {
		String rep = new String();

		for (Coord coord : this.points) {
			rep += coord.toString() + " ";
		}

		return rep;
	}
}
