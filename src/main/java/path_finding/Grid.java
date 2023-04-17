package path_finding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenRes.getWidth();
    double screenHeight = screenRes.getHeight();
    private final int width;
    private final int height;
    private static int pixels;
    public Cell[][] cells;
    private static JPanel[][] st_panels;
    private static  JLabel[][] st_labels;
    private static JPanel[][] tr_panels;
    private static JLabel[][] tr_labels;
    private static final List<Location> st_locations = new ArrayList<>();
    private static final List<Location> tr_locations = new ArrayList<>();

    //private List<Cell> cellsList = new ArrayList<>();

    //private List<JPanel> panelsList = new ArrayList<>();

    //private List<JLabel> labelsList = new ArrayList<>();
    JFrame frame = new JFrame("PathFinding");

    public Grid(int width, int height) {
        this.height = height;
        this.width = width;
        pixels = min(screenHeight/height, screenWidth/width);
        this.cells = new Cell[width][height];
        st_panels = new JPanel[width][height];
        st_labels = new JLabel[width][height];
        tr_panels = new JPanel[width][height];
        tr_labels = new JLabel[width][height];
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        //frame.setSize(width*pixels,height*pixels);
        frame.setBackground(Color.black);
        frame.setVisible(true);
        //frame.setResizable(false);


        //buttonPanel.setLayout(new GridLayout(width*10, height*10));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
                //panels[i][j] = new JPanel();
                //panels[i][j].setBounds(i*pixels, j*pixels, pixels, pixels );
                //panels[i][j].setBackground(new Color(255,255,255));
                //frame.add(panels[i][j]);
                //frame.revalidate();
                //frame.repaint();
            }
        }
        frame.pack();

    }

    private int min(double v, double v1) {
        if (v<v1){
            return (int) v;
        }else{
            return (int) v1;
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

        cells[node.location.x()][node.location.y()].addNode(node);

        Location location = new Location(node.location.x(), node.location.y());

        //cellsList.add(cells[node.location.getX()][node.location.getY()]);
        //panelsList.add(panels[node.location.getX()][node.location.getY()]);
        //labelsList.add(labels[node.location.getX()][node.location.getY()]);

        if(node.getType() == Node.Type.MANUFACTURE || node.type == Node.Type.DELIVER) {
            st_locations.add(location);
            if(st_panels[node.location.x()][node.location.y()] == null){st_panels[node.location.x()][node.location.y()] = new JPanel();}
            if(st_labels[node.location.x()][node.location.y()] == null){st_labels[node.location.x()][node.location.y()] = new JLabel();}

            st_panels[node.location.x()][node.location.y()].setBounds((node.location.x()-1)*pixels, (node.location.y()-1)*pixels, pixels*3, pixels*3);
            st_panels[node.location.x()][node.location.y()].setBackground(node.colour);
            //st_panels[node.location.getX()][node.location.getY()].setOpaque(false);
            frame.add(st_panels[node.location.x()][node.location.y()]);
        }else if(node.type == Node.Type.TRANSPORTER) {
            tr_locations.add(location);
            if(tr_panels[node.location.x()][node.location.y()] == null){tr_panels[node.location.x()][node.location.y()] = new JPanel();}
            if(tr_labels[node.location.x()][node.location.y()] == null){tr_labels[node.location.x()][node.location.y()] = new JLabel();}

            tr_panels[node.location.x()][node.location.y()].setBounds(node.location.x()*pixels, node.location.y()*pixels, pixels, pixels);
            tr_panels[node.location.x()][node.location.y()].setBackground(node.colour);
            frame.add(tr_panels[node.location.x()][node.location.y()]);

            /*
            if(node.curLocation!= null) {
                tr_panels[node.curLocation.getX()][node.curLocation.getY()].setBackground(new Color(0,255,0));
                frame.add(tr_panels[node.curLocation.getX()][node.curLocation.getY()]);
            }*/


        }
        String items;

        // iterate through list of locations of stations to update number of items
        if(!st_locations.isEmpty()) {
            for (Location loc : st_locations) {
                items = Integer.toString(cells[loc.x()][loc.y()].staItems());
                addLabel(st_panels[loc.x()][loc.y()],st_labels[loc.x()][loc.y()], items, pixels);
                st_labels[loc.x()][loc.y()].setBorder(BorderFactory.createEmptyBorder( pixels/2 /*top*/, 0, 0, 0 ));

            }
        }

        // iterate through list of locations of transporters to update number of items
        if(!tr_locations.isEmpty()) {
            for (Location loc : tr_locations) {
                items = Integer.toString(cells[loc.x()][loc.y()].traItems());
                addLabel(tr_panels[loc.x()][loc.y()],tr_labels[loc.x()][loc.y()], items, pixels/2);
            }
        }

        frame.revalidate();


    }

    public void removeNode(Node node) {

        //cellsList.remove(cells[node.location.getX()][node.location.getY()]);
        //panelsList.remove(panels[node.location.getX()][node.location.getY()]);
        //labelsList.remove(labels[node.location.getX()][node.location.getY()]);

        node.curLocation = new Location(node.location.x(), node.location.y());
        tr_locations.remove(node.curLocation);
        //frame.remove(tr_panels[node.location.getX()][node.location.getY()]);

        tr_panels[node.curLocation.x()][node.curLocation.y()].remove(tr_labels[node.curLocation.x()][node.curLocation.y()]);
        tr_panels[node.curLocation.x()][node.curLocation.y()].setBackground(new Color(255, 255, 255, 255));
        if(node.preLocation.x() < node.curLocation.x()) {
            addLabel(tr_panels[node.curLocation.x()][node.curLocation.y()], tr_labels[node.curLocation.x()][node.curLocation.y()], "→", pixels/2);
        }else if (node.preLocation.x() > node.curLocation.x()) {
            addLabel(tr_panels[node.curLocation.x()][node.curLocation.y()], tr_labels[node.curLocation.x()][node.curLocation.y()], "←", pixels/2);
        }else{
            if(node.preLocation.y() < node.curLocation.y()) {
                addLabel(tr_panels[node.curLocation.x()][node.curLocation.y()], tr_labels[node.curLocation.x()][node.curLocation.y()], "↓", pixels/2);
            }else if (node.preLocation.y() > node.curLocation.y()) {
                addLabel(tr_panels[node.curLocation.x()][node.curLocation.y()], tr_labels[node.curLocation.x()][node.curLocation.y()], "↑", pixels/2);
        }
        }
        //frame.add(tr_panels[node.location.getX()][node.location.getY()]);
        frame.repaint();
        cells[node.curLocation.x()][node.curLocation.y()].removeNode(node);
        node.preLocation = node.curLocation;
    }

    static private void addLabel(JPanel panel,JLabel label, String text, int size){

        label.setText(text);

        //label.setHorizontalAlignment(JLabel.LEFT);
        //label.setVerticalAlignment(JLabel.TOP);

        label.setForeground(new Color(0, 0, 0, 255));
        //label.setForeground(new Color(255,255,255));
        label.setFont(new Font("Courier", Font.BOLD, size));
        label.setBorder(BorderFactory.createEmptyBorder( -1*size/4 /*top*/, 0, 0, 0 ));

        //label.setBounds(0, 0, size, size);

        panel.add(label);
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