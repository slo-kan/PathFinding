package path_finding;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final List<Node> nodes;

    public Cell() {this.nodes = new ArrayList<>();}

    public Cell(List<Node> nodes) {
        this.nodes = nodes;
    }

    private boolean isOccupied = false;


    public void addNode(Node node) {
        if (node.getType() == Node.Type.TRANSPORTER){this.isOccupied=true;}
        this.nodes.add(node);
    }

    public void removeNode(Node node) {
        if (node.getType() == Node.Type.TRANSPORTER){this.isOccupied=false;}
        nodes.remove(node);
    }

    public int numNodes() {
        return nodes.size();
    }

    public List<Node> getNodes() {
        return nodes;
    }


    public int traItems() {
        int items = 0;
        for (Node node : nodes) {
            if(node.getType() == Node.Type.TRANSPORTER){ items = node.items;}
        }
        return items;
    }
    public int staItems() {
        int items = 0;
        for (Node node : nodes) {
            if(node.getType() == Node.Type.STATION){ items = node.items;}
        }
        return items;
    }

    public int numItems() {
        int items = 0;
        for (Node node : nodes) {
            items += node.items;
        }
        return items;
    }
    public boolean notOccupied(){
        return !this.isOccupied;
    }

    public String print() {
        if (numNodes() == 0) {
            return "_";
        } else if (numNodes() == 1 && nodes.get(0) instanceof Station) {
            return "S(" + nodes.get(0).items + ")";
        } else if (numNodes() == 1 && nodes.get(0) instanceof Transporter) {
            return "T(" + nodes.get(0).items + ")";
        } else {
            return "O(" + (nodes.get(0).items + nodes.get(1).items) + ")";
        }
    }
}
