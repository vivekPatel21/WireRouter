import java.util.*;

public class Routing {

    public static ArrayList<Wire>
    findPaths(Board board, ArrayList<Endpoints> goals) {
        ArrayList<Wire> wires = new ArrayList<>();
        ArrayList<Wire> bestWires = new ArrayList<>();
        int redoCount = goals.size() * 2; // Maximum number of attempts
        Set<Coord> occupiedCoords = new HashSet<>();

        for (int i = 0; i < goals.size(); i++) {
            Endpoints goal = goals.get(i);
            Wire wire = bfsFindPath(board, goal.start, goal.end, goal.id, occupiedCoords);

            if (wire == null) { // Cannot connect given other paths
                // Move path to front
                if (i > 0) {
                    goals.add(0, goals.get(i));
                    goals.remove(i + 1);
                } else { // Move to the back if it is impossible without any other wires
                    goals.add(goals.get(i));
                    goals.remove(i);
                }
                i = -1; // Reset loop counter

                wires.clear();
                occupiedCoords.clear();

                // Stop infinite loop
                if (redoCount < 0)
                    break;
                redoCount--;
            } else {
                wires.add(wire);
                occupiedCoords.addAll(wire.getPoints());

                if (bestWires.size() < wires.size()) {
                    bestWires = new ArrayList<>(wires);
                }
            }
        }

        // Swap order
        for (int i = 0; i < bestWires.size(); i++) {
            for (int j = i + 1; j < bestWires.size(); j++) {
                if (bestWires.get(i).id > bestWires.get(j).id) {
                    Endpoints tempep = goals.get(i);
                    Wire temp = bestWires.get(i);
                    goals.set(i, goals.get(j));
                    bestWires.set(i, bestWires.get(j));
                    goals.set(j, tempep);
                    bestWires.set(j, temp);
                }
            }
        }

        for (Wire w : bestWires) {
            board.placeWire(w);
        }

        return bestWires;
    }

    private static Wire
    bfsFindPath(Board board, Coord start, Coord end, int wireId,
                                    Set<Coord> occupiedCoords) {
        if (board.isObstacle(start) || board.isObstacle(end)) {
            return null; // No path if start or end is an obstacle
        } else if(start.equals(end)){
            List<Coord> path = new ArrayList<>();
            path.add(start);
            return new Wire(wireId,path);
        }

        Map<Coord, Coord> prev = new HashMap<>();
        Queue<Coord> queue = new LinkedList<>();
        Set<Coord> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        prev.put(start, null); // Start has no predecessor

        boolean found = false;
        while (!queue.isEmpty() && !found) {
            Coord current = queue.poll();

            for (Coord neighbor : board.adj(current)) {
                if (!visited.contains(neighbor) && !board.isObstacle(neighbor) &&
                        (!board.isOccupied(neighbor) || board.getValue(neighbor) == wireId) &&
                        !occupiedCoords.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    prev.put(neighbor, current);

                    if (neighbor.equals(end)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!found) {
            return null; // No path found
        }

        // Reconstruct path from end to start using `prev` map
        List<Coord> path = new ArrayList<>();
        for (Coord at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path); // Since we've reconstructed it backwards

        return new Wire(wireId, path);
    }
}