import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public abstract class GarbageCollector {
	protected List<Integer> roots;
	protected HashMap<Integer, Node> objects;

	public GarbageCollector() {
		roots = new ArrayList<Integer>();
		objects = new HashMap<Integer, Node>();
	}

	protected void readHeap(String path) {
		File data = new File(path);
		try {
			Scanner scanner = new Scanner(data);
			while (scanner.hasNext()) {
				String s = scanner.next();
				String[] tokens = s.split(",");
				try {
					objects.put(Integer.parseInt(tokens[0]),
							new Node(false, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
				} catch (NumberFormatException e) {}
			}
			scanner.close();
		} catch (IOException ex) {
			System.out.println("File not found");
		}
	}

	protected void readRoots(String path) {
		File data = new File(path);
		try {
			Scanner scanner = new Scanner(data);
			while (scanner.hasNext()) {
				String s = scanner.next();
				roots.add(Integer.parseInt(s));
			}
			scanner.close();
		} catch (IOException ex) {
			System.out.println("File not found");
		}
	}

	protected void readPointers(String path) {
		File data = new File(path);
		try {
			Scanner scanner = new Scanner(data);
			while (scanner.hasNext()) {
				String s = scanner.next();
				String[] tokens = s.split(",");
				try {
					objects.get(Integer.parseInt(tokens[0])).getChildren()
							.add(objects.get(Integer.parseInt(tokens[1])));
				} catch (NumberFormatException e) {}
			}
			scanner.close();
		} catch (IOException ex) {
			System.out.println("File not found");
		}
	}

	abstract protected void mark();

	abstract protected void writeNewHeap(String path);
}
