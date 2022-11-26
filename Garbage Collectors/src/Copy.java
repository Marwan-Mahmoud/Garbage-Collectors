import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class Copy extends GarbageCollector {
	private int nextMemoryAddress = 0;

	@Override
	protected void mark() {
		Node dummy = new Node(true, -1, -1);
		List<Node> temp = new LinkedList<>();
		for (Integer root : roots)
			temp.add(objects.get(root));

		dummy.setChildren(temp);
		bfs(dummy);
	}

	private void bfs(Node node) {
		Queue<Node> queue = new LinkedList<>();
		queue.add(node);
		while (queue.size() > 0) {
			node = queue.poll();
			for (Node child : node.getChildren()) {
				if (!child.getVisited()) {
					child.setVisited(true);
					copy(child);
					queue.add(child);
				}
			}
		}
	}

	private void copy(Node node) {
		int size = node.getEnd() - node.getStart();
		node.setStart(nextMemoryAddress);
		node.setEnd(node.getStart() + size);
		nextMemoryAddress = node.getEnd() + 1;
	}

	@Override
	protected void writeNewHeap(String path) {
		File file = new File(path);
		PriorityQueue<Map.Entry<Integer, Node>> newHeap = new PriorityQueue<>((i, j) -> i.getValue().getStart() - j.getValue().getStart());
		for (Map.Entry<Integer, Node> object : objects.entrySet()) {
			if (object.getValue().getVisited())
				newHeap.add(object);
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("object-identifier,memory-start,memory-end\n");
			while (newHeap.size() > 0) {
				Entry<Integer, Node> object = newHeap.poll();
				writer.write(object.getKey() + "," + object.getValue().getStart() + "," + object.getValue().getEnd() + "\r\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		GarbageCollector copy = new Copy();
		copy.readHeap(args[0]);
		copy.readRoots(args[1]);
		copy.readPointers(args[2]);
		copy.mark();
		copy.writeNewHeap(args[3]);
	}
}
