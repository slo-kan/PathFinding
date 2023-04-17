package path_finding;

public class PathFinder {

    /*
    public PathFinder(){

    }
    public PathFinder(Transporter tra){
        List<Location> traLocations = new ArrayList<>();
        for(Station sta : tra.getStations()){
            traLocations.add(sta.getLocation());
        }
    }
    */
    public Location getNextLocation(Location start, Location end) {
        if (start.equals(end)) {
            return null;
        }
        int deltaX = 0;
        int deltaY = 0;

        if(start.x() < end.x()) {
            deltaX += 1;
        }else if (start.x() > end.x()) {
            deltaX -= 1;
        }else if (start.y() < end.y()) {
            deltaY += 1;
        }else {
            deltaY -= 1;
        }

        return start.moveDelta(deltaX, deltaY);
    }
    public Location getNextLocation(Transporter tra) {
        return null;
    }
}
