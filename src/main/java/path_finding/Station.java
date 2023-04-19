package path_finding;

import java.awt.*;

public class Station extends Node {
    private int GENERATION_TIME = 0;

    public boolean isOccupied = false;

    private StationType  stationtype;

    public enum StationType {
        MANUFACTURE, DELIVER
    }
    public Station(){

    }
    public Station(int x, int y) {
        this.colour = new Color(111,216,255);
        this.type = Type.STATION;
        this.stationtype = StationType.DELIVER;
        this.location = new Location(x,y);

    }
    public Station(int x, int y, int time) {
        this.colour = new Color(255,90,111);
        this.type = Type.STATION;
        this.stationtype = StationType.MANUFACTURE;
        this.location = new Location(x,y);
        this.GENERATION_TIME = time;

    }


    public Type getType() {
        return type;
    }

    public StationType getStationType() {
        return stationtype;
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
        if (stationtype != StationType.MANUFACTURE) {
            System.err.println("Manufacture called at non-manufacture station");
            return;
        }

        if (Main.time.getCurrentTime() % GENERATION_TIME == 0) {
            items++;
        }
    }

    public void deliver(int items) {
        if (stationtype != StationType.DELIVER) {
            System.err.println("Delivery called at non-delivery station");
            return;
        }

        this.items += items;
    }
}
