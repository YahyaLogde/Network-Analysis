package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.*;

//public methods and where the network is initialised
public class Controller {
	String newLine = System.getProperty("line.separator");
	String userDir = System.getProperty("user.dir");
	Network net1 = new Network();

	public Controller() {

	}

	//Call all the relevant controllers and containers of the GUI
	@FXML
	Button importFile, addInteraction, aveDegree, calcDegree, degreeDistBut, maxDegree, clearNetwork;
	@FXML
	TextArea output;
	@FXML
	TextField degreeField, protein1, protein2;

	/*Method to open a browse window when importFile button is pressed to select the protein interaction file and
	* once open to create a network using the relevant methods in the Network class. These are called using net1.
	* as this is the Network ID*/
	public void openFile(ActionEvent Event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Protein Interaction File");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			net1.readFile(selectedFile);
			output.appendText("Network created successfully using: " + selectedFile + newLine );
		} else {
			output.appendText("Importing file aborted/wrong file type"  + newLine);
		}
	}

	//Method that runs the network method to add user interactions when the relevant button is pressed and both fields aren't empty (error checking)
	public void userNodes(ActionEvent Event) {
		String user1 = protein1.getText();
		String user2 = protein2.getText();

		if (protein1.getText().isEmpty() || protein2.getText().isEmpty()) {
			output.appendText("Please input a protein in both fields to add an interaction" + newLine);
		} else {
			net1.userNodes(user1, user2);
			output.appendText("Protein interaction '" +  protein1.getText() + "\t" + protein2.getText() + "' has been added!" + newLine);
		}
	}

	//Method to call the nodeDegree method when relevant button is pressed and to print error message if no network exists or if a node doesnt exist in the network
	public void getDegree(ActionEvent Event) {
		String node = degreeField.getText();

		if (degreeField.getText().isEmpty()) {
			output.appendText("Please enter a node to calculate the degree" + newLine);
		} else if (net1.nodeDegree(node) == 0) {
			output.appendText(node + " is not an existing node in the Network" + newLine);
		} else {
			String degree = Integer.toString(net1.nodeDegree(node));
			output.appendText("Degree of Node " + node + " = " + degree + newLine);
		}
	}

	//Method to call the averageDegree method when relevant button is pressed and to print error message if no network exists
	public void aveDegree(ActionEvent Event) {
		if (net1.nodeSet.isEmpty()) {
			output.appendText("Please create a Network before trying to calculate the Average Degree" + newLine);
		} else {
			String aveDeg = Float.toString(net1.averageDegree());
			output.appendText("Average Degree is: " + aveDeg + newLine);
		}
	}

	//Method to call the highestDegree method when relevant button is pressed and to print error message if no network exists
	public void maxDegree(ActionEvent Event) {
		if (net1.nodeSet.isEmpty()) {
			output.appendText("Please create a Network before trying to calculate the Maximum Degree" + newLine);
		} else {
			String maxDeg = Integer.toString(net1.highestDegree());
			output.appendText("Node(s) with maximum degree of: " + maxDeg + " = " + net1.maxDegreeName + newLine);
		}
	}

	//Method to call the degreeDistribution method when relevant button is pressed and to print error message if no network exists
	public void degreeDistribution (ActionEvent Event) {
		if (net1.nodeSet.isEmpty()) {
			output.appendText("Please create a Network before trying to save the Degree Distribution" + newLine);
		} else {
			net1.degreeDistribution();
			for (int i : net1.degreeDist.keySet()) {
				output.appendText("Degree " + i + " - " + " Number of Nodes with corresponding degree  " + net1.degreeDist.get(i) + newLine);
			}
			output.appendText("DegreeDistribution.csv saved to " + userDir + newLine);
		}
	}

	/*Method to clear the network when relevant button is pressed (clear all hashsets, arraylists and hashmaps) so that a new network can be made or
	 * multiple other networks aren't merged. Error checking if there is no network to clear. */
	public void clearNet (ActionEvent Event) {
		if (net1.nodeSet.isEmpty()) {
			output.appendText("No Network exists" + newLine);
		} else {
			net1.nodeSet.clear();
			net1.edgeSet.clear();
			net1.maxDegrees.clear();
			net1.maxDegreeName.clear();
			net1.degree.clear();
			net1.degreeDist.clear();
			output.appendText("Network cleared!" + newLine);
		}
	}
}