import java.io.IOException;

public class GraphvizRunner {
    public static void generateGraphImage(String dotFilePath, String outputFilePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotFilePath, "-o", outputFilePath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error generating graph image: " + e.getMessage());
        }
    }
}
