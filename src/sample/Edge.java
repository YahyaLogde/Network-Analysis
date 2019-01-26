package sample;

//Class that creates edges between two nodes created using the Node construct
public class Edge {

	public Node Node1, Node2;

	public Node nodeName1() {
		return Node1;
	}

	public Node nodeName2() {
		return Node2;
	}

	public Edge(Node node1, Node node2) {
		Node1 = node1;
		Node2 = node2;
	}
	
}
