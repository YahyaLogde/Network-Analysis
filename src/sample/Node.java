package sample;

//Class that creates nodes using the node construct
public class Node {

	public String NodeID;

	//Method that returns the Node name
	public String getNodeID() {
		return NodeID;
	}

	public Node() {

		this.NodeID = "";
	}

	public Node(String n) {

		this.NodeID = n;
	}

}
