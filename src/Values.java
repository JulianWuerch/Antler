
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Values {
    public static boolean running = true, warp = false;
    public static Timer timer;
    public static int FPS = 70, iteration = 0;
    public static int breakTime = 1000 / FPS;
    public static int N_WIDTH = 300, N_HEIGHT = 300;
    public static BufferedImage imageToDraw;
    public static float[][][] feroArray;
    public static Ant[] ant;
    public static int ants, antSpeed = 4, antSensorDist = 3, antExtrestTimerMax = 400;
    public static float antSpinSpeed = 0.7f;
    public static int GROUPING_DIST = N_WIDTH / 160, GROUP_DIST = N_WIDTH / 2;
    public static int spreadRange = 1;
    public static int colorNumberNormal = Color.RED.getRGB();
    public static int colorNumberFood = Color.GREEN.getRGB();
    public static int colorNumberHome = Color.BLUE.getRGB();
    public static Point[] teamHome;
    public static Food[] food;
}
