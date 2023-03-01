
public class Transporter extends Node {

    private int waitTime = 2;

    private boolean wait = false;
    private int currentlyWaited = 0;

    private Station source;
    private Station destination;

    public Transporter() {
    }
    public Transporter(int x, int y, Station source, Station destination) {
        this.location = new Location(x,y);
        this.source = source;
        this.destination = destination;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station station) {
        this.destination = station;
    }

    public void setLocation(Location location) {
        if (location.equals(destination.location)) {
            Station temp = source;
            source = destination;
            destination = temp;
            this.location = source.location;

            this.wait = shouldWait();
            if (this.wait && source.getType() == Station.Type.MANUFACTURE) {
                this.items = source.items;
                source.items = 0;
            }

        } else if (!wait || (currentlyWaited >= waitTime)) {
            this.location = location;
            if (source.getType() == Station.Type.DELIVER) {
                source.deliver(this.items);
                this.items = 0;
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
