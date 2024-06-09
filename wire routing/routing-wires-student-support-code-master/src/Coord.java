public class Coord {
	public final int row;
	public final int column;

	public Coord(final int arg_x, final int arg_y) {
		this.row = arg_x;
		this.column = arg_y;
	}

	public static int compare(Coord a, Coord b) {
		if (a.row < b.row) {
			return -1;
		} else if (a.row == b.row) {
			return Integer.compare(a.column, b.column);
		} else {
			return 1;
		}
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Coord)) {
			return false;
		} else {
			Coord other = (Coord) obj;
			return this.row == other.row && this.column == other.column;
		}
	}

	public boolean isAdjacent(Coord other) {
		return this.row == other.row && this.column == (other.column + 1) ||
			   this.row == other.row && this.column == (other.column - 1) ||
			   this.row == (other.row + 1) && this.column == other.column ||
			   this.row == (other.row - 1) && this.column == other.column;
	}

	public int hashCode() {
		String repr = String.valueOf(this.row) + " " + String.valueOf(this.column);
		return repr.hashCode();
	}

	public String toString() {
		return "(" + String.valueOf(this.row) + ", " + String.valueOf(this.column) + ")";
	}
}
