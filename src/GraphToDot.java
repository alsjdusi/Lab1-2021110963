import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class GraphToDot {
    public static void writeDotFile(DirectedGraph graph, String filename, List<String> shortestPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("digraph G {");
            for (String vertex : graph.getVertices()) {
                for (Map.Entry<String, Integer> entry : graph.getNeighbors(vertex).entrySet()) {
                    String source = vertex;
                    String destination = entry.getKey();
                    int weight = entry.getValue();
                    boolean isShortestEdge = shortestPath.contains(source) && shortestPath.contains(destination)
                            && shortestPath.indexOf(destination) == shortestPath.indexOf(source) + 1;

                    if (isShortestEdge) {
                        writer.println("    \"" + source + "\" -> \"" + destination + "\" [label=\"" + weight + "\", color=\"red\"];");
                    } else {
                        writer.println("    \"" + source + "\" -> \"" + destination + "\" [label=\"" + weight + "\"];");
                    }
                }
            }
            writer.println("}");
        } catch (IOException e) {
            System.err.println("Error writing DOT file: " + e.getMessage());
        }
    }

}
