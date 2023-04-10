package path_finding;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final List<Node> nodes;

    public Cell() {this.nodes = new ArrayList<>();}

    public Cell(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public int numNodes() {
        return nodes.size();
    }

    public List<Node> getNodes() {
        return nodes;
    }
    public int numItems() {
        int items = 0;
        for(int i=0; i<nodes.size(); i++ ){
            items += nodes.get(i).items;
        }return items;
    }
    public boolean notOccupied(){
        boolean occupied = false;
        for (Node node:this.nodes){
            if(node.getType() == Node.Type.TRANSPORTER){ occupied = true;}
            else{occupied = false;}
        }return !occupied;
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
