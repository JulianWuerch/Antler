public class Timer extends Thread {
    public void run() {
        try {
            runner();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runner() throws InterruptedException {
        while (Values.running) {
            tick();
            if (!Values.warp) {
                sleep(Values.breakTime); 
            }
            
        }
    }

    public void tick() {
        Manager.runSimulation();
        Values.iteration++;
        if (Values.iteration > 1000) {
            Values.running = false;
        }
    }
}
