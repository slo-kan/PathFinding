package main.java;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location moveDelta(int deltaX, int deltaY) {
        return new Location(x + deltaX, y + deltaY);
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Location && this.toString().equals(obj.toString());
    }
}
