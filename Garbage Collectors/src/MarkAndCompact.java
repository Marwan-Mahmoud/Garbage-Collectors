import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class MarkAndCompact extends MarkAndSweep {

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
			int nextMemoryAddress = 0;
			while (newHeap.size() > 0) {
				Entry<Integer, Node> object = newHeap.poll();
				int size = object.getValue().getEnd() - object.getValue().getStart();
				writer.write(object.getKey() + "," + nextMemoryAddress + "," + (nextMemoryAddress + size) + "\r\n");
				nextMemoryAddress += size + 1;
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		GarbageCollector mc = new MarkAndCompact();
		mc.readHeap(args[0]);
		mc.readRoots(args[1]);
		mc.readPointers(args[2]);
		mc.mark();
		mc.writeNewHeap(args[3]);
	}
}
