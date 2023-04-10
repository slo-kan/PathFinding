package path_finding;

public class PathFinder {

    public Location getNextLocation(Location start, Location end) {
        if (start.equals(end)) {
            return null;
        }

        int deltaX = 0;
        int deltaY = 0;

        if (start.getX() < end.getX()) {
            deltaX += 1;
        } else if (start.getX() > end.getX()) {
            deltaX -= 1;
        } else if (start.getY() < end.getY()) {
            deltaY += 1;
        } else {
            deltaY -= 1;
        }


        return start.moveDelta(deltaX, deltaY);
    }
}
