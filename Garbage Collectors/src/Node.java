import java.util.ArrayList;
import java.util.List;

public class Node {
	private boolean visited;
	private int start;
	private int end;
	private List<Node> children;

	public Node(boolean visited, int start, int end) {
		this.visited = visited;
		this.start = start;
		this.end = end;
		this.children = new ArrayList<Node>();
	}

	public boolean getVisited() {
		return this.visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return this.end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public List<Node> getChildren() {
		return this.children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
}
