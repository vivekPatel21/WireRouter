import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Utilities {

	public static Board buildBoard(ArrayList<Endpoints> goals,
				       ArrayList<Integer[]> obstacles,
				       String filename) throws BoardConstructionException {
		try {
			final FileReader     reader         = new FileReader(filename);
			final BufferedReader bufferedReader = new BufferedReader(reader);
			// read in data
			int height   = Integer.parseInt(bufferedReader.readLine());
			int width    = Integer.parseInt(bufferedReader.readLine());
			int num_obst = Integer.parseInt(bufferedReader.readLine());

			// read obstacles and lay out obstacles
			for (int obstacle_id = 0; obstacle_id < num_obst; ++obstacle_id) {
				String line       = bufferedReader.readLine();
				String nums_str[] = line.split(" ");
				Integer corners[] = {Integer.parseInt(nums_str[0]),
						     Integer.parseInt(nums_str[1]),
						     Integer.parseInt(nums_str[2]),
						     Integer.parseInt(nums_str[3])};
				obstacles.add(corners);
			}

			// read points and lay out path origins and path destinations
			int num_paths = Integer.parseInt(bufferedReader.readLine());

			for (int path_id = 1; path_id <= num_paths; ++path_id) {
				String line    = bufferedReader.readLine();
				String nums[]  = line.split(" ");
				int    originX = Integer.parseInt(nums[0]);
				int    originY = Integer.parseInt(nums[1]);
				int    destX   = Integer.parseInt(nums[2]);
				int    destY   = Integer.parseInt(nums[3]);

				Endpoints eps = new Endpoints(path_id,
											  new Coord(originX, originY),
											  new Coord(destX, destY));

				goals.add(eps);
			}

			bufferedReader.close();
			return new Board(height, width, goals, obstacles);

		} catch (FileNotFoundException e) {
			throw new BoardConstructionException("Input file not found: " + filename);

		} catch (IOException e) {
			throw new BoardConstructionException("Error reading input file: " + filename);
		}
	}

	// Test the findPath() function against the given input file
	public static void test(String filename) {
		ArrayList<Endpoints> goals     = new ArrayList<>();
		ArrayList<Integer[]> obstacles = new ArrayList<>();

		try {
			// Build the board.
			Board board = buildBoard(goals, obstacles, filename);

			// Call the findPaths function.
			ArrayList<Wire> wires = Routing.findPaths(board, goals);

			// Check the wires for correctness.
			checkPaths(goals, wires, board);

			// Score the wires if they were all valid.
			scorePaths(wires);

		} catch (BoardConstructionException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void checkPaths(ArrayList<Endpoints> goals,
								   ArrayList<Wire> wires,
								   Board board) {

		if (wires == null || wires.contains(null) || wires.size() != goals.size()) {
		    System.out.println("failed to route all the wires");
		    board.show();
        }
        assertTrue(wires.size() == goals.size());
		assertFalse(wires.contains(null));

		int[]     checked = new int[goals.size()];
		boolean[] valid   = new boolean[goals.size()];
		for (int index = goals.size(); --index >= 0; ) {
			checked[index] = 0;
			valid[index] = false;
		}

		for (Wire wire : wires) {
			Endpoints eps = goals.get(wire.id - 1);
			valid[wire.id - 1] = validPath(wire, eps, board);
			++checked[eps.id - 1];
		}

		boolean all_valid = true;
		boolean count_error = false;
		for (int index = checked.length; --index >= 0; ) {
			if (!valid[index]) {
				System.out.println("Invalid path for goal " + (index + 1));
				all_valid = false;
			}

			if (count_error = (checked[index] == 0)) {
				System.out.println("Missing path for goal " + (index + 1));
			} else if (count_error = (checked[index] > 1)) {
				System.out.println("Multiple wires for goal " + (index + 1));
			}
		}

		assertTrue(all_valid);
		assertFalse(count_error);
	}

	private static boolean validPath(Wire wire,
	                                 Endpoints eps,
	                                 Board board) {

		if (wire.length() == 0) {
			System.out.println("Empty wire for goal " + wire.id);
			return false;

		} else if (!wire.start().equals(eps.start)) {
			System.out.println("Incorrect start of wire " + wire.id + ":\n" +
			                   "\tExpected: " + eps.start + "\n" +
			                   "\tGot:      " + wire.start());
			return false;

		} else if (!wire.end().equals(eps.end)) {
			System.out.println("Incorrect end of wire " + wire.id + ":\n" +
			                   "\tExpected: " + eps.start + "\n" +
			                   "\tGot:      " + wire.start());
			return false;
		}

		for (int step_id = 0; step_id < wire.length(); ++step_id) {
			Coord path_component = wire.get(step_id);

			Integer board_val = board.getValue(path_component);
			if (board_val != wire.id) {
				System.out.println("Incorrect wire label at " + path_component.row + ", " + path_component.column + ":\n" +
				                   "\tExpected: " + wire.id + "\n" +
				                   "\tGot:      " + board_val);
				return false;
			} else if (step_id != wire.length() - 1) {
				Coord next_path_component = wire.get(step_id + 1);

				if (!path_component.isAdjacent(next_path_component)) {
					System.out.println("Wire components " + path_component + " and " + next_path_component + " are not adjacent.");
					return false;
				}
			}
		}

		return true;
	}

	public static void scorePaths(ArrayList<Wire> wires) {
		int total_wire_length = 0;
		for (Wire wire : wires) {
			total_wire_length += wire.length();
		}

		System.err.println("Total length of all wires: " + total_wire_length);
	}

	public static class BoardConstructionException extends Exception {
		public BoardConstructionException(String msg) {
			super(msg);
		}
	}

	public static void main(String[] args) {
		switch (args.length) {
			case 1:
				if (args[0].equals("batch_test")) {
					java.io.File folder = new java.io.File("./inputs");
					for (java.io.File file : folder.listFiles()) {
						if (file.isFile()) {
							try {
								test(file.getCanonicalPath());
							} catch (java.io.IOException e) {
								System.out.println("bummer!");
							}
						}
					}
				}
				break;
			case 2:
				if (args[0].equals("test")) {
					test("inputs/" + args[1]);
				}
				break;
			default:
				test("inputs/wire3.in");
		}
	}
}
