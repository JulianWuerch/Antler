import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Graphics;

public class Draw extends JLabel {
    protected void paintComponent (Graphics g) {
        for (int x = 0; x < Values.N_WIDTH; x++) {
            for (int y = 0; y < Values.N_HEIGHT; y++) {
                g.setColor(new Color(255, 50, 50, (int) (255 * Values.feroArray[x][y][0])));
                g.fillRect(x, y, 1, 1);  
                g.setColor(new Color(50, 255, 50, (int) (255 * Values.feroArray[x][y][1])));
                g.fillRect(x, y, 1, 1);  
                g.setColor(new Color(50, 50, 255, (int) (255 * Values.feroArray[x][y][2])));
                g.fillRect(x, y, 1, 1);               
            }
        }
        g.setColor(new Color(100, 255, 100, 100));
        for (int i = 0; i < Values.food.length; i++) {
            g.fillOval(Values.food[i].x - 4, Values.food[i].y - 4, 8, 8);
        }
        g.setColor(new Color(100, 100, 255, 100));
        for (int i = 0; i < Values.teamHome.length; i++) {
            g.fillOval(Values.teamHome[i].x - 4, Values.teamHome[i].y - 4, 8, 8);
        }
        if (Values.ant[0] != null) {
            g.setColor(Color.yellow);
            g.drawOval(Values.ant[0].x - 7, Values.ant[0].y - 7, 15, 15);
        }
        
    }
}
