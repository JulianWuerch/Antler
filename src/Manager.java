import java.awt.Point;

public class Manager {

    public static void setUp(int teams, int food) {
        Values.teamHome = new Point[teams];
        Values.food = new Food[food];
        int[] xSpawn = new int[teams];
        int[] ySpawn = new int[teams];
        int dist = 0;
        while (dist < Values.GROUP_DIST) {
            dist = Integer.MAX_VALUE;
            for (int i = 0; i < teams; i++) {
                xSpawn[i] = (int) (Math.random() * Values.N_WIDTH);
                ySpawn[i] = (int) (Math.random() * Values.N_HEIGHT);
            }
            for (int i = 1; i < teams; i++) {
                dist = (int) (Math.min(dist, Math.hypot(xSpawn[i] - xSpawn[i - 1], ySpawn[i] - ySpawn[i - 1])));
            }
        }
        for (int i = 0; i < teams; i++) {
            Values.teamHome[i] = new Point(xSpawn[i], ySpawn[i]);
            System.out.println("TeamHome: " + i + " " + Values.teamHome[i]);
        }

        for (int i = 0; i < food; i++) {
            Values.food[i] = new Food((int) (Math.random() * Values.N_WIDTH), (int) (Math.random() * Values.N_HEIGHT), (float) (Math.random()));
        }
    }

    public static void spawnAnts(int count, int teams, boolean grouped) {
        Values.ants = count;
        int[] xSpawn = new int[teams];
        int[] ySpawn = new int[teams];
        if (grouped) {
            for (int i = 0; i < teams; i++) {
                xSpawn[i] = Values.teamHome[i].x;
                ySpawn[i] = Values.teamHome[i].y;
            }
        } else {
            xSpawn[0] = Values.N_WIDTH / 2;
            ySpawn[0] = Values.N_HEIGHT / 2;
        }
            
        
        Values.ant = new Ant[count];
        for (int i = 0; i < count; i++) {
            int x = 0, y = 0;
            if (grouped) {
                x = xSpawn[i % teams] - Values.GROUPING_DIST + (int) (Math.random() * Values.GROUPING_DIST * 2);
                y = ySpawn[i % teams] - Values.GROUPING_DIST + (int) (Math.random() * Values.GROUPING_DIST * 2);
            } else {
                x = (int) (Values.N_WIDTH * Math.random());
                y = (int) (Values.N_HEIGHT * Math.random());
            }
            Values.ant[i] = new Ant(x, y, i, i % teams, (float) (Math.random() * Math.PI * 2));
        }
    }

    public static void runSimulation() {
        for (int i = 0; i < Values.ants; i++) {
            //Bewegung
            Ant ant = Values.ant[i];
            if (i == 0) {
                //System.out.println(ant.x + " " + ant.y + " " + ant.orientation);
            }
            //Rotation
            float orChange = 0;
            float leftFero = 0, middleFero = 0, rightFero = 0;
            int xMess = ant.x + (int) (Math.cos(ant.orientation) * Values.antSensorDist);
            int yMess = ant.y + (int) (Math.sin(ant.orientation) * Values.antSensorDist);

            for (int ii = -2; ii < 3; ii++) {
                for (int iii = -2; iii < 3; iii++)  {
                    leftFero += 100 * Values.feroArray[(xMess + (int) (Values.antSensorDist * Math.sin(ant.orientation)) + Values.N_WIDTH + ii) % Values.N_WIDTH][(yMess - (int) (Values.antSensorDist * Math.cos(ant.orientation)) + Values.N_HEIGHT + iii) % Values.N_HEIGHT][ant.intrest];
                    middleFero += Math.max(Values.feroArray[(xMess + Values.N_WIDTH + ii) % Values.N_WIDTH][(yMess + Values.N_HEIGHT + iii) % Values.N_HEIGHT][ant.intrest] * 1000, 0.01f);
                    rightFero += 100 * Values.feroArray[(xMess + (int) (Values.antSensorDist * Math.sin(ant.orientation)) + Values.N_WIDTH + ii) % Values.N_WIDTH][(yMess + (int) (Values.antSensorDist * Math.cos(ant.orientation)) + Values.N_HEIGHT + iii) % Values.N_HEIGHT][ant.intrest];
                }
            }
            leftFero /= 5f;
            middleFero /= 5f;
            rightFero /= 5f;
            orChange = Math.min(Math.max((rightFero - leftFero) / middleFero, -1), 1) * Values.antSpinSpeed;
            float wander = ((float) Math.random() - 0.5f) * 0.15f * Values.antSpinSpeed;
            //wander = 0;
            if (i == 0) {
                //System.out.println(ant.x + " " + ant.y + " " + ant.orientation + " " + ((xMess - (int) (Values.antSensorDist * Math.sin(ant.orientation)) + Values.N_WIDTH) % Values.N_WIDTH) + " " + ((yMess - (int) (Values.antSensorDist * Math.cos(ant.orientation)) + Values.N_HEIGHT) % Values.N_HEIGHT) + " " + ((xMess + Values.N_WIDTH) % Values.N_WIDTH) + " " + ((yMess + Values.N_HEIGHT) % Values.N_HEIGHT));
                
                //System.out.println(wander);
            }
            //System.out.println(ant.orientation + " " + orChange + " " + leftFero + " " + rightFero + " " + middleFero + " " + Math.cos(ant.orientation) + " " + Math.sin(ant.orientation));
            //ant.orientation = (ant.orientation + 0.1f) % 6.28f;
            ant.orientation += (float) Math.random() * orChange + wander; 

            //Detect Food
            if (ant.intrest == 1) {
                for (int f = 0; f < Values.food.length; f++) {
                    if (Math.hypot(Values.food[f].x - ant.x, Values.food[f].y - ant.y) < Values.antSensorDist * 4) {
                        ant.intrest = 2;
                        ant.extrest = 1;
                        ant.foodTimer = Values.antExtrestTimerMax;
                        ant.orientation += Math.PI;
                    } else if (Math.hypot(Values.food[f].x - ant.x, Values.food[f].y - ant.y) < Values.antSensorDist) {
                        ant.orientation = (float) Math.atan((Values.food[f].y - ant.y) / Math.max((Values.food[f].x - ant.x), 0.1));
                    }
                }
            }

            //Detect Home
            if (ant.intrest == 2 || ant.extrest == 0) {
                for (int f = 0; f < Values.teamHome.length; f++) {
                    if (Math.hypot(Values.teamHome[f].x - ant.x, Values.teamHome[f].y - ant.y) < Values.antSensorDist * 4) {
                        ant.intrest = 1;
                        ant.extrest = 2;
                        ant.homeTimer = Values.antExtrestTimerMax;
                        ant.orientation += Math.PI;
                    } else if (Math.hypot(Values.teamHome[f].x - ant.x, Values.teamHome[f].y - ant.y) < Values.antSensorDist) {
                        ant.orientation = (float) Math.atan((Values.teamHome[f].y - ant.y) / Math.max((Values.teamHome[f].x - ant.x), 0.1));
                    }
                }
            }
            if (ant.foodTimer > 0) {
                ant.foodTimer--;
            } else {
                if (ant.extrest == 1) {
                    ant.extrest = 0;
                }
            }

            if (ant.homeTimer > 0) {
                ant.homeTimer--;
            } else {
                if (ant.extrest == 2) {
                    ant.extrest = 0;
                }
            }

            if (i == 0) {
                //System.out.println(ant.intrest);
            }

            
           
            //Bewegung
            ant.x += (int) (Math.cos(ant.orientation) * Values.antSpeed);
            ant.y += (int) (Math.sin(ant.orientation) * Values.antSpeed);

            if (ant.x >= Values.N_WIDTH) {
                ant.x -= Values.N_WIDTH;
            }
            if (ant.y >= Values.N_HEIGHT) {
                ant.y -= Values.N_HEIGHT;
            }
            if (ant.x < 0) {
                ant.x += Values.N_WIDTH;
            }
            if (ant.y < 0) {
                ant.y += Values.N_HEIGHT;
            }

            //Stoff ausbringen
            if (ant.extrest == 0) {
                Values.feroArray[ant.x][ant.y][ant.extrest] = 1;
            } else {
                Values.feroArray[ant.x][ant.y][ant.extrest] = 1 * ((ant.foodTimer + ant.homeTimer) / Values.antExtrestTimerMax);
            }
            
        }

        float[][][] newStoff = new float[Values.N_WIDTH][Values.N_HEIGHT][Values.feroArray[0][0].length];
        //Stoff verteilen
        for (int i = 0; i < Values.feroArray[0][0].length; i++) {
            for (int x = 0; x < Values.N_WIDTH; x++) {
                for (int y = 0; y < Values.N_HEIGHT; y++) {
                    float newValue = 0;
                    for (int xx = -Values.spreadRange; xx <= Values.spreadRange; xx++) {
                        for (int yy = -Values.spreadRange; yy <= Values.spreadRange; yy++) {
                            newValue += Values.feroArray[(x + xx + Values.N_WIDTH) % Values.N_WIDTH][(y + yy + Values.N_HEIGHT) % Values.N_HEIGHT][i];
                        }
                    }
                    newValue += Values.feroArray[x][y][i] * 30;
                    newStoff[x][y][i] = newValue / 40;
                }
            }
        }
        
        Values.feroArray = newStoff;
        Window.draw.repaint();
    }
}
