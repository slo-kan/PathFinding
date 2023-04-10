package path_finding;

import java.awt.*;

public class Station extends Node {
    private int GENERATION_TIME = 0;


    public Station(){

    }
    public Station(int x, int y) {
        this.colour = Color.BLUE;
        this.type = Type.DELIVER;
        this.location = new Location(x,y);

    }
    public Station(int x, int y, int time) {
        this.colour = Color.RED;
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
