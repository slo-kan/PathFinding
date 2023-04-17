package path_finding;

import java.awt.*;

public class Station extends Node {
    private int GENERATION_TIME = 0;

    public boolean isOccupied = false;
    public Station(){

    }
    public Station(int x, int y) {
        this.colour = new Color(111,216,255);
        this.type = Type.DELIVER;
        this.location = new Location(x,y);

    }
    public Station(int x, int y, int time) {
        this.colour = new Color(255,90,111);
        this.type = Type.MANUFACTURE;
        this.location = new Location(x,y);
        this.GENERATION_TIME = time;

    }


    public Type getType() {
        return type;
    }

    public int getGENERATION_TIME() {
        return GENERATION_TIME;
    }

    public Location getLocation(){return this.location;}
    /*@Override
    public String toString() {
        return type.name();
    }*/

    public void manufacture() {
        if (type != Type.MANUFACTURE) {
            System.err.println("Manufacture called at non-manufacture station");
            return;
        }

        if (Main.time.getCurrentTime() % GENERATION_TIME == 0) {
            items++;
        }
    }

    public void deliver(int items) {
        if (type != Type.DELIVER) {
            System.err.println("Delivery called at non-delivery station");
            return;
        }

        this.items += items;
    }
}
