import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
    public static JFrame window = new JFrame("Antler");
    public static Draw draw;

    public static void openWindow() {
        draw = new Draw();
        draw.setPreferredSize(new Dimension(Values.N_WIDTH, Values.N_HEIGHT));
        window.add(draw);
        window.addKeyListener(new Keyboard());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.requestFocus();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
