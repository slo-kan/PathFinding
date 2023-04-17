package path_finding;

public record Location(int x, int y) {

    public Location moveDelta(int deltaX, int deltaY) {
        return new Location(x + deltaX, y + deltaY);
    }

    @Override
    public String toString() {
        return "(" + x() + ", " + y() + ")";
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
