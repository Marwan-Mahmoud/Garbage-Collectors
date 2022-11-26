import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MarkAndSweep extends GarbageCollector {

	@Override
	protected void mark() {
		for (Integer root : roots)
			dfs(objects.get(root));
	}

	protected void dfs(Node node) {
		if (node.getVisited())
			return;

		node.setVisited(true);
		for (Node child : node.getChildren())
			dfs(child);
	}

	@Override
	protected void writeNewHeap(String path) {
		File file = new File(path);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("object-identifier,memory-start,memory-end\n");
			for (Map.Entry<Integer, Node> object : objects.entrySet()) {
				if (object.getValue().getVisited())
					writer.write(object.getKey() + "," + object.getValue().getStart() + "," + object.getValue().getEnd() + "\r\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Couldn't write");
		}
	}

	public static void main(String[] args) {
		GarbageCollector ms = new MarkAndSweep();
		ms.readHeap(args[0]);
		ms.readRoots(args[1]);
		ms.readPointers(args[2]);
		ms.mark();
		ms.writeNewHeap(args[3]);
	}
}
