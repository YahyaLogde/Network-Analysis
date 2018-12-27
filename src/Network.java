import java.util.HashSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public class Network {
	
	HashSet<Node> nodeSet = new HashSet<Node>();
	HashSet<Edge> edgeSet = new HashSet<Edge>();
	
	public Network(HashSet<Node> setN, HashSet<Edge> setE) {
		this.nodeSet = setN;
		this.edgeSet = setE;

	}
	
	public static void readFile() {
		Path proteinFile = Paths.get("E:\\Documents\\Google Drive\\University\\Masters\\Programming Skills\\Java\\Project\\Network-Analysis\\PPInetwork.txt"); {
			
			BufferedReader reader = null;
			try {
				reader = Files.newBufferedReader(proteinFile);
				String line;
				while((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}}

