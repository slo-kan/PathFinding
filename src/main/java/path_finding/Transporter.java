package path_finding;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Transporter extends Node {


    private int waitTime = 2;

    private boolean wait = false;
    private int currentlyWaited = 0;

    private Station source;
    private Station destination;

    List<Station> traStations = new ArrayList<>();

    private int index =1;
    public Transporter() {
        this.type = Type.TRANSPORTER;
    }
    public Transporter(int x, int y, List<Station> stations) {
        this.colour = Color.GREEN;
        this.type = Type.TRANSPORTER;
        this.location = new Location(x,y);
        this.traStations = stations;
        this.source = this.traStations.get(0);
        this.destination = this.traStations.get(0);
    }

    public Type getType() {
        return type;
    }

    public Station getDestination() {
        return destination;
    }

    public List<Station> getStations(){return traStations;}

    public void setDestination(Station station) {
        this.destination = station;
    }

    public void setLocation(Location location) {
        if (location.equals(destination.location)) {

            source = destination;
            destination = this.traStations.get(index % traStations.size());
            this.location = source.location;

            this.wait = shouldWait();

            if (this.wait && source.getType() == Station.Type.MANUFACTURE) {
                this.items = this.items + source.items;
                source.items = 0;
                index++;
            }

        } else if (!wait || (currentlyWaited >= waitTime)) {
            this.location = location;
            if (this.wait && (source.getType() == Station.Type.DELIVER)) {
                source.deliver(this.items);
                this.items = 0;
                index++;
            }

            currentlyWaited = 0;
            wait = false;

        } else {
            currentlyWaited++;
        }
    }

    private boolean shouldWait() {
        if (source.getType() == Station.Type.MANUFACTURE) {
            return source.items > 0;
        } else {
            return this.items > 0;
        }
    }
}
