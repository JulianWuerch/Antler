public class AntlerMain {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Antler");
        System.out.println(Math.cos(0) + " " + Math.cos(1) + " " + Math.cos(Math.PI) + " " + Math.cos(Math.PI * 1.5));
        Values.feroArray = new float[Values.N_WIDTH][Values.N_HEIGHT][3];
        Manager.setUp(1, 5);
        Window.openWindow();
        Manager.spawnAnts(200, 1, true);
        

        Values.timer = new Timer();
        Values.timer.start();
        
    }

    public static void debug() {
        Values.ant = new Ant[3];
        Values.ant[0] = new Ant(100, 170, 0, 0, 1f);
        Values.ant[1] = new Ant(110, 170, 0, 0, 0.8f);
        Values.ant[2] = new Ant(120, 170, 0, 0, 0.4f);
        Values.ants = 3;

        for (int i = 0; i < 400000; i++) {
            Values.feroArray[i][200][1] = 1f;
        }
    }
}
