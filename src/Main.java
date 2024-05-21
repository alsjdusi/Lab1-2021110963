import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //数据处理
        DataDeal dataDeal = new DataDeal();
        StringBuilder content = dataDeal.datadeal();
        content = new StringBuilder(content.toString().toLowerCase());

        // 将文本数据转化为有向图
        DirectedGraph graph = new DirectedGraph();
        String[] words = content.toString().split(" ");
        for (int i = 0; i < words.length - 1; i++) {
            String source = words[i];
            String destination = words[i + 1];
            graph.addEdge(source, destination);
        }
        Scanner scan = new Scanner(System.in);


        int flag = 0;
        while(flag != -1)
        {
            System.out.println("所能执行的操作：");
            System.out.println("1：打印有向图");
            System.out.println("2：查询桥接点");
            System.out.println("3：输入文本并根据桥接词更新文本");
            System.out.println("4：计算两个单词的最短路径");
            System.out.println("5：随机游走");
            System.out.println("输入操作前的序号：");
            flag = scan.nextInt();
            scan.nextLine(); // 消耗换行符
            switch (flag){
                case 1:
                    // 打印有向图
                    graph.showDirectedGrap();
                    graph.showDirectedGraph();
                    break;
                case 2:
                    //查找桥接词
                    System.out.println("输入桥接词 word1：");
                    String word1 = scan.nextLine();
                    System.out.println("输入桥接词 word2：");
                    String word2= scan.nextLine();
                    // 查询桥接词
                    List<String> bridgeWords = graph.queryBridgeWords(word1, word2);
                    // 输出结果
                    if (bridgeWords.isEmpty()) {
                        System.out.println("No bridge words from " + word1 + " to " + word2 + "!");
                    } else {
                        System.out.print("The bridge words from " + word1 + " to " + word2 + " are: ");
                        for (String word : bridgeWords) {
                            System.out.print(word + ", ");
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    // 用户输入的新文本
                    String newText = "Seek to explore new and exciting synergies";

                    // 进行桥接词插入并输出结果
                    BridgeWordInsertion insertion = new BridgeWordInsertion(graph);
                    insertion.generateNewText(newText);
                    break;
                case 4:
                    // 计算最短路径
                    System.out.println("输入单词（一个或两个）：");
                    String input = scan.nextLine();
                    String[] inputWords = input.split(" ");
                    if (inputWords.length == 1) {
                        String startWord = inputWords[0];
                        Map<String, List<String>> allPaths = graph.allShortestPaths(startWord);
                        for (Map.Entry<String, List<String>> entry : allPaths.entrySet()) {
                            String endWord = entry.getKey();
                            List<String> path = entry.getValue();
                            if (path.isEmpty()) {
                                System.out.println("No path from " + startWord + " to " + endWord);
                            } else {
                                System.out.println("Shortest path from " + startWord + " to " + endWord + ": " + String.join(" -> ", path));
                                System.out.println("Path length: " + (path.size() - 1));
                            }
                        }
                    } else if (inputWords.length == 2) {
                        String startWord = inputWords[0];
                        String endWord = inputWords[1];
                        List<String> calcShortestPath = graph.calcShortestPath(startWord, endWord);
                        if (calcShortestPath.isEmpty()) {
                            System.out.println("No path from " + startWord + " to " + endWord);
                        } else {
                            System.out.println("Shortest path from " + startWord + " to " + endWord + ": " + String.join(" -> ", calcShortestPath));
                            System.out.println("Path length: " + (calcShortestPath.size() - 1));
                        }
                        graph.show_shortDirectedGraph(calcShortestPath);
                    } else {
                        System.out.println("输入无效，请输入一个或两个单词。");
                    }
                    break;
                case 5:
                    // 随机游走
                    Thread randomWalkThread = new Thread(() -> {
                        List<String> path = graph.randomWalk();
                        System.out.println("Random walk path: " + String.join(" -> ", path));
                        graph.writePathToFile(path, "D:\\javaCreate\\homework\\homework1\\homework_pro1\\out\\file\\random_walk.txt");
                    });
                    randomWalkThread.start();
                    System.out.println("按Enter键停止遍历...");
                    scan.nextLine(); // 等待用户输入
                    randomWalkThread.interrupt(); // 停止遍历
                    break;
                default:
                    System.out.println("结束");
            }

        }

    }
}
