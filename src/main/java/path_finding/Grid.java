package path_finding;

import javax.swing.*;
import java.awt.*;

public class Grid {
    private int width;
    private int height;

    private int pixels = 5;
    private Cell[][] cells;
    private JPanel[][] panels;

    JFrame frame = new JFrame();

    public Grid(int width, int height) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[width][height];
        panels = new JPanel[width][height];
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(width*pixels,height*pixels);
        frame.setVisible(true);
        //frame.setResizable(false);


        //buttonPanel.setLayout(new GridLayout(width*10, height*10));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
                panels[i][j] = new JPanel();
                panels[i][j].setBounds(i*pixels, j*pixels, pixels, pixels );
                //panels[i][j].setBackground(new Color(255,255,255));
                frame.add(panels[i][j]);
                frame.revalidate();
                frame.repaint();
            }
        }
        frame.pack();

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
        if(node.getClass() == Station.class) {
            //panels[node.location.getX()][node.location.getY()].setBounds(node.location.getX()*pixels, node.location.getY()*pixels, 15, 15);
            panels[node.location.getX()][node.location.getY()].setBackground(Color.red);
        }else if(node.getClass() == Transporter.class) {
            panels[node.location.getX()][node.location.getY()].setBounds(node.location.getX()*pixels, node.location.getY()*pixels, pixels, pixels);
            panels[node.location.getX()][node.location.getY()].setBackground(Color.blue);
        }
    }

    public void removeNode(Node node) {

        cells[node.location.getX()][node.location.getY()].removeNode(node);
        if( cells[node.location.getX()][node.location.getY()].numNodes() > 0) {
            panels[node.location.getX()][node.location.getY()].setBackground(Color.red);
        }else {
            panels[node.location.getX()][node.location.getY()].setBackground(Color.white);
        }

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