import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class DataDeal {
    public static void datadeal() {
        // 指定要读取的文件路径
        String filePath = "D:\\javaCreate\\homework\\homework1\\homework1\\test\\test1.txt";

        // 创建一个字符串来保存文件内容
        StringBuilder content = new StringBuilder();

        // 使用try-with-resources语句创建文件读取流和缓冲读取器
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            // 逐行读取文件内容并追加到字符串中
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("无法读取文件: " + e.getMessage());
        }

        // 输出字符串中的内容
        System.out.println(content);

        // 将换行/回车符替换为空格
        content = new StringBuilder(content.toString().replaceAll("[\\n\\r]", " "));

        // 将标点符号替换为空格
        content = new StringBuilder(content.toString().replaceAll("[\\p{Punct}]", " "));

        // 将非字母字符忽略
        content = new StringBuilder(content.toString().replaceAll("[^a-zA-Z ]", ""));

        // 将连续的空格替换为一个空格
        content = new StringBuilder(content.toString().replaceAll("\\s+", " "));

        // 输出处理后的文本内容
        System.out.println(content);


    }
}
