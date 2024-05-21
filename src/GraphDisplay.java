import javax.swing.*;
import java.awt.*;

public class GraphDisplay extends JFrame {

    public GraphDisplay(String imagePath) {
        setTitle("Graph Display");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel(new ImageIcon(imagePath));
        JScrollPane scrollPane = new JScrollPane(label);

        add(scrollPane, BorderLayout.CENTER);
    }


}
