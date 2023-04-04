public class Ant {

    public int x, y, index, team, intrest, extrest, foodTimer, homeTimer;
    public float orientation;

    public Ant(int x, int y, int index, int team, float orientation) {
        //System.out.println("New Ant: " + x + " " + y + " " + index + " " + team + " " + orientation);
        this.x = x;
        this.y = y;
        this.index = index;
        this.team = team + 1;
        this.orientation = orientation;
        intrest = 1;
        extrest = 0;
        foodTimer = 0;
        homeTimer = 0;
    }

}
