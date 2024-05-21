import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DirectedGraph {

    private final Map<String, Map<String, Integer>> adjacencyMap;

    public DirectedGraph() {
        this.adjacencyMap = new HashMap<>();
    }

    // 获取所有顶点
    public Set<String> getVertices() {
        return adjacencyMap.keySet();
    }

    // 获取某个顶点的邻居节点
    public Map<String, Integer> getNeighbors(String source) {
        return adjacencyMap.getOrDefault(source, new HashMap<>());
    }

    // 获取某条边的权重
    public int getWeight(String source, String destination) {
        if (adjacencyMap.containsKey(source)) {
            Map<String, Integer> neighbors = adjacencyMap.get(source);
            return neighbors.getOrDefault(destination, 0);
        }
        return 0;
    }
    // 添加有向边
    public void addEdge(String source, String destination) {
        Map<String, Integer> neighbors = adjacencyMap.getOrDefault(source, new HashMap<>());
        neighbors.put(destination, neighbors.getOrDefault(destination, 0) + 1);
        adjacencyMap.put(source, neighbors);
    }

    // 打印有向图
    public void showDirectedGrap() {
        for (String source : adjacencyMap.keySet()) {
            Map<String, Integer> neighbors = adjacencyMap.get(source);
            for (String destination : neighbors.keySet()) {
                int weight = neighbors.get(destination);
                System.out.println(source + " -> " + destination + ": " + weight);
            }
        }
    }
    // 查找桥接词
    // 查找桥接词
    public List<String> queryBridgeWords(String word1, String word2) {
        List<String> bridgeWords = new ArrayList<>();

        // 如果 word1 或 word2 不在图中出现，则返回空列表
        if (!adjacencyMap.containsKey(word1) || !adjacencyMap.containsKey(word2)) {
            System.out.println("No word1 or word2 in the graph!");
            return bridgeWords;
        }

        // 遍历 word1 的邻居节点
        Map<String, Integer> neighbors1 = adjacencyMap.get(word1);

        // 寻找桥接词
        for (String bridgeWord : neighbors1.keySet()) {
            // 如果 word1 → 桥接词 和 桥接词 → word2 都存在，则添加桥接词到列表中
            if (adjacencyMap.containsKey(bridgeWord) && adjacencyMap.get(bridgeWord).containsKey(word2)) {
                bridgeWords.add(bridgeWord);
            }
        }


        return bridgeWords;
    }

    public List<String> InsertqueryBridgeWords(String word1, String word2) {
        List<String> bridgeWords = new ArrayList<>();

        // 如果 word1 或 word2 不在图中出现，则返回空列表
        if (!adjacencyMap.containsKey(word1) || !adjacencyMap.containsKey(word2)) {
            return bridgeWords;
        }
        // 遍历 word1 的邻居节点
        Map<String, Integer> neighbors1 = adjacencyMap.get(word1);
        // 寻找桥接词
        for (String bridgeWord : neighbors1.keySet()) {
            // 如果 word1 → 桥接词 和 桥接词 → word2 都存在，则添加桥接词到列表中
            if (adjacencyMap.containsKey(bridgeWord) && adjacencyMap.get(bridgeWord).containsKey(word2)) {
                bridgeWords.add(bridgeWord);
            }
        }
        return bridgeWords;
    }

    // Dijkstra 算法实现，返回距离和前驱节点映射
    public Map<String, String> dijkstra(String start, Map<String, Integer> distances) {
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String vertex : adjacencyMap.keySet()) {
            if (vertex.equals(start)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            priorityQueue.add(vertex);
        }

        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();
            if (distances.get(current) == Integer.MAX_VALUE) break; // 剪枝，如果当前最短距离是无穷大，后面的节点也不用处理了

            for (Map.Entry<String, Integer> neighbor : getNeighbors(current).entrySet()) {
                String neighborVertex = neighbor.getKey();
                int weight = neighbor.getValue();
                int currentDist = distances.getOrDefault(current, Integer.MAX_VALUE);
                int newDist = currentDist + weight;
                if (newDist < distances.getOrDefault(neighborVertex, Integer.MAX_VALUE)) {
                    priorityQueue.remove(neighborVertex);
                    distances.put(neighborVertex, newDist);
                    previous.put(neighborVertex, current);
                    priorityQueue.add(neighborVertex);
                }
            }
        }

        return previous;
    }

    public List<String> calcShortestPath(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = dijkstra(start, distances);
        List<String> path = new ArrayList<>();
        if (distances.getOrDefault(end, Integer.MAX_VALUE) == Integer.MAX_VALUE) {
            return path; // 不可达
        }

        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public Map<String, List<String>> allShortestPaths(String start) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = dijkstra(start, distances);
        Map<String, List<String>> paths = new HashMap<>();

        for (String vertex : adjacencyMap.keySet()) {
            if (!vertex.equals(start)) {
                List<String> path = new ArrayList<>();
                if (distances.getOrDefault(vertex, Integer.MAX_VALUE) != Integer.MAX_VALUE) {
                    for (String at = vertex; at != null; at = previous.get(at)) {
                        path.add(at);
                    }
                    Collections.reverse(path);
                }
                paths.put(vertex, path);
            }
        }
        return paths;
    }

    // 随机游走
    public List<String> randomWalk() {
        List<String> visited = new ArrayList<>();
        Set<String> visitedEdges = new HashSet<>();
        Random random = new Random();
        List<String> vertices = new ArrayList<>(getVertices());

        if (vertices.isEmpty()) {
            return visited;
        }

        String current = vertices.get(random.nextInt(vertices.size()));
        visited.add(current);

        while (true) {
            Map<String, Integer> neighbors = getNeighbors(current);
            if (neighbors.isEmpty()) {
                break;
            }

            List<String> neighborList = new ArrayList<>(neighbors.keySet());
            String next = neighborList.get(random.nextInt(neighborList.size()));
            String edge = current + " -> " + next;

            if (visitedEdges.contains(edge)) {
                break;
            }

            visitedEdges.add(edge);
            visited.add(next);
            current = next;
        }

        return visited;
    }

    // 将路径写入文件
    public void writePathToFile(List<String> path, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String node : path) {
                writer.write(node + "\n");
            }
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }

    public void showDirectedGraph() {
        String dotFilePath = "D:\\javaCreate\\homework\\homework1\\homework_pro1\\out\\file\\graph.dot";
        String outputFilePath = "D:\\javaCreate\\homework\\homework1\\homework_pro1\\out\\file\\graph.png";
        List<String> shortestPath = new ArrayList<>();

        GraphToDot.writeDotFile(this, dotFilePath,shortestPath);
        GraphvizRunner.generateGraphImage(dotFilePath, outputFilePath);

        SwingUtilities.invokeLater(() -> {
            GraphDisplay display = new GraphDisplay(outputFilePath);
            display.setVisible(true);
        });
    }

    public void show_shortDirectedGraph(List<String> Path) {
        String dotFilePath = "D:\\javaCreate\\homework\\homework1\\homework_pro1\\out\\file\\graph_short.dot";
        String outputFilePath = "D:\\javaCreate\\homework\\homework1\\homework_pro1\\out\\file\\graph_short.png";

        GraphToDot.writeDotFile(this, dotFilePath,Path);
        GraphvizRunner.generateGraphImage(dotFilePath, outputFilePath);

        SwingUtilities.invokeLater(() -> {
            GraphDisplay display = new GraphDisplay(outputFilePath);
            display.setVisible(true);
        });
    }
}
