package path_finding;

import java.awt.*;

public abstract class Node {

    protected Type type;
    protected Location location = new Location(0, 0);
    public Location preLocation = new Location(0, 0);
    public Location curLocation = new Location(0, 0);
    protected int items = 0;

    protected Color  colour ;

    public enum Type {
        MANUFACTURE, DELIVER, TRANSPORTER
    }

    public Type getType() {
        return type;
    }

}
