package path_finding;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Transporter extends Node {
    private final int waitTime = 2;
    private boolean wait = false;
    private int currentlyWaited = 0;

    private Station source;
    private Station destination;


    private List<Station> traStations = new ArrayList<>();

    private int index =1;
    public Transporter() {
        this.type = Type.TRANSPORTER;
    }
    public Transporter(int x, int y, List<Station> stations) {
        this.colour = new Color(144,238,144);
        this.type = Type.TRANSPORTER;
        this.location = new Location(x,y);
        this.traStations = stations;
        this.source = this.traStations.get(0);
        this.destination = this.traStations.get(0);
        /*
        sortStations(traStations.subList(1, traStations.size()));
        System.out.println(traStations);
        List<Station> sortStations;
        sortStations = traStations.subList(1, traStations.size()).stream()
                .sorted((o1,o2)-> -1*(o1.getLocation().getX()-traStations.get(0).getLocation().getX())-(o2.getLocation().getX()-traStations.get(0).getLocation().getX()))
                .collect(Collectors.toList());

        sortStations = sortStations.stream()
                .sorted((o1,o2)-> -1*(o1.getLocation().getY()-traStations.get(0).getLocation().getY())-(o2.getLocation().getY()-traStations.get(0).getLocation().getY()))
                .collect(Collectors.toList());

        for(int i=1; i<traStations.size();i++){
            traStations.set(i, sortStations.get(i-1));
        }
        System.out.println(traStations);

         */

    }
    public void sortStations(List<Station> stations){

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
            setDestination(this.traStations.get(index % traStations.size()));
            this.location = source.location;
            this.source.isOccupied = true;
            index++;

            this.wait = shouldWait();

            if (this.wait && source.getType() == Station.Type.MANUFACTURE) {
                this.items = this.items + source.items;
                source.items = 0;
                //index++;
            }

        } else if (!wait || (currentlyWaited >= waitTime)) {
            this.location = location;
            this.source.isOccupied = false;
            if (this.wait && (source.getType() == Station.Type.DELIVER)) {
                source.deliver(this.items);
                this.items = 0;
                //index++;
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
