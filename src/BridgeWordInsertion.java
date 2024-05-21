import java.util.List;
import java.util.Random;

public class BridgeWordInsertion {

    private final DirectedGraph graph;
    private final Random random;

    public BridgeWordInsertion(DirectedGraph graph) {
        this.graph = graph;
        this.random = new Random();
    }

    // 计算桥接词
    public String calculateBridgeWord(String word1, String word2) {
        List<String> bridgeWords = graph.InsertqueryBridgeWords(word1, word2);
        if (bridgeWords.isEmpty()) {
            return "";
        } else {
            return bridgeWords.get(random.nextInt(bridgeWords.size()));
        }
    }

    // 插入桥接词并输出结果
    public void generateNewText(String text) {
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            result.append(word1).append(" ");

            String bridgeWord = calculateBridgeWord(word1, word2);
            if (!bridgeWord.isEmpty()) {
                result.append(bridgeWord).append(" ");
            }
        }

        // 将最后一个单词添加到结果中
        result.append(words[words.length - 1]);

        // 输出结果
        System.out.println("Output: " + result.toString());
    }
}