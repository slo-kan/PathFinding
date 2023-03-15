
import java.util.Arrays;

public class Grid {
    private int width;
    private int height;

    private Cell[][] cells;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
            }
        }

    }

    public Grid(int size) {
        this(size, size);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addNode(Node node) {
        cells[node.location.getX()][node.location.getY()].addNode(node);
    }

    public void removeNode(Node node) {
        cells[node.location.getX()][node.location.getY()].removeNode(node);
    }

    public String getRowAsString(int row) {
        Cell[] rowCells = new Cell[width];
        for (int i = 0; i < width; i++) {
            rowCells[i] = cells[row][i];
        }

        String out = "";

        for (Cell cell : rowCells) {
            out += pad(cell.print()) + " | ";
        }

        return out;
    }

    public String print() {
        String out = "";
        for (int i = 0; i < getWidth(); i++) {
            out += getRowAsString(i);
            out += "\n";
        }

        return out;
    }

    public String pad(String s) {
        return String.format("%5s", s).replace(' ', '_');
    }
}