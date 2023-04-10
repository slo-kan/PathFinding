package path_finding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenRes.getWidth();
    double screenHeight = screenRes.getHeight();
    private int width;
    private int height;

    private int pixels = 15;
    private Cell[][] cells;
    private JPanel[][] st_panels;
    private JLabel[][] st_labels;

    private JPanel[][] tr_panels;
    private JLabel[][] tr_labels;

    private boolean canRemove;

    private static final List<Location> st_locations = new ArrayList();
    private static final List<Location> tr_locations = new ArrayList();

    //private List<Cell> cellsList = new ArrayList<>();

    //private List<JPanel> panelsList = new ArrayList<>();

    //private List<JLabel> labelsList = new ArrayList<>();
    JFrame frame = new JFrame();

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
        frame.setSize(width*pixels,height*pixels);
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

        cells[node.location.getX()][node.location.getY()].addNode(node);



        Location location = new Location(node.location.getX(), node.location.getY());

        //cellsList.add(cells[node.location.getX()][node.location.getY()]);
        //panelsList.add(panels[node.location.getX()][node.location.getY()]);
        //labelsList.add(labels[node.location.getX()][node.location.getY()]);

        if(node.getType() == Node.Type.MANUFACTURE || node.type == Node.Type.DELIVER) {
            st_locations.add(location);
            st_panels[node.location.getX()][node.location.getY()] = new JPanel();
            st_labels[node.location.getX()][node.location.getY()] = new JLabel();
            st_panels[node.location.getX()][node.location.getY()].setBounds((node.location.getX()-1)*pixels, (node.location.getY()-1)*pixels, pixels*3, pixels*3);
            st_panels[node.location.getX()][node.location.getY()].setBackground(node.colour);
            frame.add(st_panels[node.location.getX()][node.location.getY()]);
        }else if(node.type == Node.Type.TRANSPORTER) {
            tr_locations.add(location);
            tr_panels[node.location.getX()][node.location.getY()] = new JPanel();
            tr_labels[node.location.getX()][node.location.getY()] = new JLabel();
            tr_panels[node.location.getX()][node.location.getY()].setBounds(node.location.getX()*pixels, node.location.getY()*pixels, pixels, pixels);
            tr_panels[node.location.getX()][node.location.getY()].setBackground(node.colour);
            frame.add(tr_panels[node.location.getX()][node.location.getY()]);
        }
        String items = "";

        // iterate through list of locations of stations
        if(!st_locations.isEmpty()) {
            for (int i = 0; i < st_locations.size(); i++) {

                items = Integer.toString(cells[st_locations.get(i).getX()][st_locations.get(i).getY()].numItems());

                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setText(items);

                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setHorizontalAlignment(JLabel.CENTER);
                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setVerticalAlignment(JLabel.CENTER);
                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setForeground(Color.white);
                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setFont(new Font("Courier", Font.BOLD, pixels));

                st_panels[st_locations.get(i).getX()][st_locations.get(i).getY()].add(st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()]);
            }
        }

        // iterate through list of locations of transporters

        if(!tr_locations.isEmpty()) {
            for (int i = 0; i < tr_locations.size(); i++) {

                items = Integer.toString(cells[tr_locations.get(i).getX()][tr_locations.get(i).getY()].numItems());

                tr_labels[tr_locations.get(i).getX()][tr_locations.get(i).getY()].setText(items);

                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setHorizontalAlignment(JLabel.LEFT);
                st_labels[st_locations.get(i).getX()][st_locations.get(i).getY()].setVerticalAlignment(JLabel.TOP);
                tr_labels[tr_locations.get(i).getX()][tr_locations.get(i).getY()].setForeground(Color.white);
                tr_labels[tr_locations.get(i).getX()][tr_locations.get(i).getY()].setFont(new Font("Courier", Font.BOLD, pixels));
                //tr_labels[tr_locations.get(i).getX()][tr_locations.get(i).getY()].setBounds(0, 0, pixels, pixels);

                tr_panels[tr_locations.get(i).getX()][tr_locations.get(i).getY()].add(tr_labels[tr_locations.get(i).getX()][tr_locations.get(i).getY()], BorderLayout.NORTH);
            }
        }

        frame.revalidate();

    }

    public void removeNode(Node node) {

        //cellsList.remove(cells[node.location.getX()][node.location.getY()]);
        //panelsList.remove(panels[node.location.getX()][node.location.getY()]);
        //labelsList.remove(labels[node.location.getX()][node.location.getY()]);
        Location location = new Location(node.location.getX(), node.location.getY());

        tr_locations.remove(location);

        frame.remove(tr_panels[node.location.getX()][node.location.getY()]);
        frame.repaint();

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