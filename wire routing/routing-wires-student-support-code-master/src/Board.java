import java.util.ArrayList;

public class Board {
    private int[] grid;
    private int   width;
    private int   height;

    public Board(int height, int width, ArrayList<Endpoints> points, ArrayList<Integer[]> obstacles) {
        this.height = height;
        this.width = width;
        this.grid = new int[height * width];

        // Initialize the grid
        for (int x = 0; x < height; ++x) {
            for (int y = 0; y < width; ++y) {
                this.grid[(x * width) + y] = 0;
            }
        }

        this.placeObstacles(obstacles);
        this.placeEndpoints(points);
    }

    public int getValue(Coord c) {
        return this.grid[(c.row * this.width) + c.column];
    }

    public void unset(Coord c) {
        if (this.grid[(c.row * this.width) + c.column] == -1) {
            throw new ObstacleException(c);
        } else {
            this.grid[(c.row * this.width) + c.column] = 0;
        }
    }

    public void putValue(Coord c, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Wire value must be >= 1");
        }

        if (this.grid[(c.row * this.width) + c.column] == -1) {
            // We can't overwrite obstacles.
            throw new ObstacleException(c);
        } else {
            this.grid[(c.row * this.width) + c.column] = value;
        }
    }

    public boolean isOccupied(Coord c) {
        return this.getValue(c) != 0;
    }

    public boolean isObstacle(Coord c) {
        return this.getValue(c) == -1;
    }

    public void placeWire(Wire wire) {
        // Check adjacency
        for (int step_index = 1; step_index < wire.length(); ++step_index) {
            if (!wire.get(step_index - 1).isAdjacent(wire.get(step_index))) {
                throw new WireAdjacencyException(wire.get(step_index - 1), wire.get(step_index));
            }
        }

        // Check for obstacles and other wires.
        for (Coord c : wire.getPoints()) {
            int curr_value = this.getValue(c);

            if (curr_value == -1) {
                throw new ObstacleException(c);
            } else if (curr_value > 0 && curr_value != wire.id) {
                throw new WireWireException(c);
            }
        }

        // Actually place the wire
        for (Coord c : wire.getPoints()) {
            // We avoid using the setter because we've already sanity checked
            // the wire as a whole, so we don't need to check each individual
            // point along the path.
            this.grid[(c.row * this.width) + c.column] = wire.id;
        }
    }

    public void removeWire(Wire wire) {
        for (Coord c: wire.getPoints()) {
            if (this.getValue(c) == wire.id
                && c != wire.start() && c != wire.end()) {
                this.unset(c);
            }
        }
    }

    public ArrayList<Coord> adj(Coord c) {
        ArrayList<Coord> adjs = new ArrayList<Coord>();

        if (c.row + 1 < this.height) {
            adjs.add(new Coord(c.row + 1, c.column));
        }

        if (c.row - 1 >= 0) {
            adjs.add(new Coord(c.row - 1, c.column));
        }

        if (c.column + 1 < this.width) {
            adjs.add(new Coord(c.row, c.column + 1));
        }

        if (c.column - 1 >= 0) {
            adjs.add(new Coord(c.row, c.column - 1));
        }

        return adjs;
    }

    private void placeEndpoints(ArrayList<Endpoints> points) {
        for (Endpoints endpoints : points) {
            this.putValue(endpoints.start, endpoints.id);
            this.putValue(endpoints.end, endpoints.id);
        }
    }

    private void placeObstacles(ArrayList<Integer[]> obstacles) {
        for (Integer[] corners : obstacles) {
            for (int x = corners[0]; x <= corners[2]; ++x) {
                for (int y = corners[1]; y <= corners[3]; ++y) {
                    this.grid[(x * this.width) + y] = -1;
                }
            }
        }
    }

    public void show() {
        for (int x = 0; x < this.height; ++x) {
            System.out.print("|");
            for (int y = 0; y < this.width; ++y) {
                int val = this.getValue(new Coord(x, y));

                System.out.print(String.format("%3s", val));

                if (y < this.width - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println(" |");
        }
    }

    public String toString() {
        String result = new String("");
        for (int x = 0; x < this.height; ++x) {
            result += "|";
            for (int y = 0; y < this.width; ++y) {
                int val = this.getValue(new Coord(x, y));
                result += String.format("%3s", val);
                if (y < this.width - 1) {
                    result += " ";
                }
            }
            result += " |\n";
        }
	return result;
    }
    
    public class ObstacleException extends RuntimeException {
        public ObstacleException(Coord loc) {
            super("Unable to overwrite obstacle at coordinate (" + loc.row + ", " + loc.column + ")");
        }
    }

    public class WireWireException extends RuntimeException {
        public WireWireException(Coord loc) {
            super("Unable to overwrite one wire with another at coordinate (" + loc.row + ", " + loc.column + ")");
        }
    }

    public class WireAdjacencyException extends RuntimeException {
        public WireAdjacencyException(Coord point0, Coord point1) {
            super("Consecutive wire points are non-adjacent: (" + point0.row + ", " + point0.column + ") and (" + point1.row + ", " + point1.column + ")");
        }
    }
}

