package sample;

import java.io.*;
import java.util.*;

// Main network class that contain all the methods & initialises hashsets, arraylists and hashmaps
public class Network {
	LinkedHashSet<Node> nodeSet;
	LinkedHashSet<Edge> edgeSet;
	LinkedHashSet<Integer> maxDegrees;
	ArrayList<String> maxDegreeName;
	ArrayList<Integer> degree;
	HashMap<Integer, Integer> degreeDist;

	public Network() {
		nodeSet = new LinkedHashSet<Node>();
		edgeSet = new LinkedHashSet<Edge>();
		maxDegrees = new LinkedHashSet<Integer>();
		maxDegreeName = new ArrayList<String>();
		degreeDist = new HashMap<Integer, Integer>();
		degree = new ArrayList<Integer>();
	}

	// Method to add nodes without duplicates
	public Node addNode(Node nodeString) {

		Node node = null;

		for (Node nodes : nodeSet) {
			if (nodes.getNodeID().equals(nodeString.getNodeID())){
				node = nodes;
			}
		}
		if(node == null){
			nodeSet.add(nodeString);
			node = nodeString;
		}
		return node;
	}

	//Method to add an edge using nodes using addNode and remove duplicates
	public Edge addEdge (Node node1, Node node2) {

		Edge edge = null;

		for (Edge edges : edgeSet) {
			if (edges.Node1.getNodeID().equals(node1.getNodeID()) && (edges.Node2.getNodeID().equals(node2.getNodeID())) || (edges.Node1.getNodeID().equals(node2.getNodeID())) && (edges.Node2.getNodeID().equals(node1.getNodeID()))) {
				edge = edges;
			}
		}
		if(edge == null){
			edgeSet.add(new Edge(node1, node2));
		}
		return edge;
	}

	//Method to read in the selected file from the controller filechooser and create a new network using file data
	public void readFile(File selectedFile) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(selectedFile));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] proteins = line.split("\t");

				Node protein1 = new Node(proteins[0]);
				Node protein2 = new Node(proteins[1]);

				addEdge(addNode(protein1), addNode(protein2));
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	//Method to add user interaction, input is two strings that are turned into two nodes and added as nodes and edges using the addNode & addEdge methods
	public void userNodes(String user1, String user2) {

		Node usern1 = new Node(user1);

		Node usern2 = new Node(user2);

		addEdge(addNode(usern1), addNode(usern2));
	}

	//Method to return the degree of a node using a node String as an input. Uses the getNodeID method from node construct
	public int nodeDegree(String nodeName) {
		int degree = 0;

		for (Edge edge : edgeSet) {
			if (edge.Node1.getNodeID().equals(nodeName) || edge.Node2.getNodeID().equals(nodeName)) {
				degree = degree + 1;
			}
		}
		return degree;
	}

	//Method to calculate the average degree using nodeDegree and size of the nodeSet
	public float averageDegree() {
		float allDegrees = 0;
		float degreeSize = 0;

		for (Node node : nodeSet) {
			float degree = nodeDegree(node.getNodeID());

			degreeSize = degreeSize + degree;
			allDegrees = degreeSize / nodeSet.size();
		}
		return allDegrees;
	}

	//Method to calculate the maximum degree in nodeSet
	public int highestDegree() {

		for (Node node : nodeSet) {
			maxDegrees.add(nodeDegree(node.getNodeID()));
		}

		int maxDegree = Collections.max(maxDegrees);
		int max = maxDegree;
		int degree = 0;

		for (Node node : nodeSet) {
			degree = nodeDegree(node.getNodeID());
			if (degree >= max) {
				max = degree;
				maxDegreeName.add(node.getNodeID());
			}
		}
		return maxDegree;
	}

	//Method to calculate the degree distribution using a HashMap. Counts how many nodes have a certain degree and prints them to a CSV file.
	public void degreeDistribution() {

		for (Node node : nodeSet) {
			degree.add(nodeDegree(node.getNodeID()));
		}

		for (int value : degree) {
			if (degreeDist.get(value) == null) {
				degreeDist.put(value, 1);
			} else if (degreeDist.get(value) != null) {
				int previousVal = degreeDist.get(value);
				int current = previousVal + 1;
				degreeDist.put(value, current);
			}
		}

		//Write the degree distribution to a CSV file called DegreeDistribution.csv
		try {
			PrintWriter pw = new PrintWriter(new File("DegreeDistribution.csv"));
			StringBuilder sb = new StringBuilder();

			sb.append("Degree");
			sb.append(',');
			sb.append("Nodes with Corresponding Degree");
			sb.append(',');
			sb.append("\n");

			for (int x : degreeDist.keySet()) {
				sb.append(x);
				sb.append(',');
				sb.append(degreeDist.get(x));
				sb.append("\n");
			}

			pw.write(sb.toString());
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

