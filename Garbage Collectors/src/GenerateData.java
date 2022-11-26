import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class GenerateData {

	public int[][] generateHeap(int numObjects) {
		SortedSet<Integer> ids = new TreeSet<>();
		SortedSet<Integer> memoryAddresses = new TreeSet<>();
		while (ids.size() < numObjects) {
			ids.add((int) (Math.random() * 1000000));
		}
		while (memoryAddresses.size() < numObjects * 2) {
			memoryAddresses.add((int) (Math.random() * 1000) * ((int) (numObjects / 20) + 1));
		}
		int[][] heap = new int[numObjects][3];
		Iterator<Integer> idsIterator = ids.iterator();
		Iterator<Integer> memoryAddressesIterator = memoryAddresses.iterator();
		for (int i = 0; i < heap.length; i++) {
			heap[i][0] = idsIterator.next();
			heap[i][1] = memoryAddressesIterator.next();
			heap[i][2] = memoryAddressesIterator.next();
		}
		return heap;
	}

	public int[] generateRoots(int[][] heap) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < heap.length; i++) {
			boolean isRoot = ((int) (Math.random() * 10) % 2) == 1;
			if (isRoot) {
				list.add(heap[i][0]);
			}
		}
		int[] roots = new int[list.size()];
		Iterator<Integer> iterator = list.iterator();
		for (int i = 0; i < roots.length; i++) {
			roots[i] = iterator.next();
		}
		return roots;
	}

	public int[][] generatePointers(int[][] heap) {
		ArrayList<int[]> pointers = new ArrayList<>();
		for (int i = 0; i < heap.length; i++) {
			boolean isParent = ((int) (Math.random() * 10) % 2) == 1;
			if (isParent) {
				for (int j = 0; j < heap.length; j++) {
					if (j != i) {
						boolean isChild = ((int) (Math.random() * 10) % 4) == 3;
						if (isChild) {
							pointers.add(new int[] { heap[i][0], heap[j][0] });
						}
					}
				}
			}
		}
		return pointers.toArray(new int[pointers.size()][2]);
	}

	private String stringifyHeap(int[][] heap) {
		String data = "object-identifier,memory-start,memory-end\n";
		for (int i = 0; i < heap.length; i++) {
			data += heap[i][0];
			data += ",";
			data += heap[i][1];
			data += ",";
			data += heap[i][2];
			data += "\n";
		}
		data = data.stripTrailing();
		return data;
	}

	private String stringifyRoots(int[] roots) {
		String data = "";
		for (int i = 0; i < roots.length; i++) {
			data += roots[i];
			data += "\r\n";
		}
		data = data.stripTrailing();
		return data;
	}

	private String stringifyPointers(int[][] pointers) {
		String data = "parent-identifier,child-identifier\n";
		for (int i = 0; i < pointers.length; i++) {
			data += pointers[i][0];
			data += ",";
			data += pointers[i][1];
			data += "\n";
		}
		data = data.stripTrailing();
		return data;
	}

	private void writeFile(String fileName, String data) {
		try (PrintWriter writer = new PrintWriter(fileName)) {
			writer.write(data);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter number of objects: ");
		int numObjects = scanner.nextInt();
		scanner.close();
		GenerateData generateData = new GenerateData();
		int[][] heap = generateData.generateHeap(numObjects);
		generateData.writeFile("heap.csv", generateData.stringifyHeap(heap));
		int roots[] = generateData.generateRoots(heap);
		generateData.writeFile("roots.txt", generateData.stringifyRoots(roots));
		int[][] pointers = generateData.generatePointers(heap);
		generateData.writeFile("pointers.csv", generateData.stringifyPointers(pointers));
	}
}
