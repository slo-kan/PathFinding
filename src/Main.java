
//import java.util.Random;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

public class Main {

    public static final Time time = new Time();

    private static Map<String, Object> objects = new HashMap<>();

    private static final List<Station> listManStations = new ArrayList<>();
    private static final List<Station> listDelStations = new ArrayList<>();
    private static final List<Transporter> listTransporters = new ArrayList<>();

    private static  int runTime = 0;
    //private static int genTime = 0;
    //private static final int gridSize = 10;
    //private static final Random random = new Random();
    private static final PathFinder pathfinder = new PathFinder();

    public static void main(String[] args) throws Exception {
        Grid grid = null;
        if (args.length == 0) {
            System.err.println("Usage: java path_finding <input_file>");
            System.exit(1);
        }
        String fileName = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(", ");
                if (tokens.length > 0) {
                    if ("Size".equals(tokens[0])) {
                        grid = new Grid(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                    } else if ("Time".equals(tokens[0])) {
                        runTime = Integer.parseInt(tokens[1]);
                    } else{
                        String className = tokens[0];
                        String objectName = tokens[1];
                        String[] argsString = new String[tokens.length - 2];
                        System.arraycopy(tokens, 2, argsString, 0, argsString.length);
                        try {
                            Class<?> clazz = Class.forName(className);

                            Class<?>[] argTypes = new Class[argsString.length];
                            Object[] arg = new Object[argsString.length];

                            for (int i = 0; i < argsString.length; i++) {
                                String argString = argsString[i];
                                if (isInteger(argString)) {
                                    argTypes[i] = int.class;
                                    arg[i] = Integer.parseInt(argString);
                                } else {
                                    argTypes[i] = objects.get(argString).getClass();
                                    arg[i] = objects.get(argString);
                                }
                            }

                            Constructor<?> constructor = clazz.getDeclaredConstructor(argTypes);
                            Object obj = constructor.newInstance(arg);
                            // Assign the new object to the variable
                            objects.put(objectName, obj);
                            System.out.println(obj);
                            grid.addNode((Node) obj);
                        } catch (Exception e) {
                            System.err.println("Failed to create object: " + e.getMessage());
                        }
                        //Location loc = new Location(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        //genTime = Integer.parseInt(tokens[4]);
                    }
                }
            }

        }


        System.out.println(grid.print());
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }

        for (Object obj : objects.values()) {
            if (obj instanceof Station) {
                Station station = (Station) obj;
                if(station.getType() == Station.Type.MANUFACTURE) {
                    listManStations.add((Station) obj);
                }else if (station.getType() == Station.Type.DELIVER) {
                    listDelStations.add((Station) obj);
                }
            }else if (obj instanceof Transporter) {
                listTransporters.add((Transporter) obj);
            }
        }

        //Location manufactureStationLocation = new Location(9, 9);
        //Location deliveryStationLocation = new Location(1, 3);
        //Station man = new Station(9,9, 10);
        //Station del = new Station(1,3);
        //Transporter transporter = new Transporter(1,3, del, man);
        //transporter.setDestination(man);
        //grid.addNode(man);
        //grid.addNode(del);
        //grid.addNode(transporter);

        FileWriter file = new FileWriter(fileName, true);
        PrintWriter out = new PrintWriter(file, true);
        out.println("Printing Grid");
        out.println(grid.print());
        while (true) {
            evolveGrid(grid);
            out.println(grid.print());

            time.increment();

            if (time.getCurrentTime() > runTime) {
                out.close();
                file.close();
                break;
            }
        }
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void evolveGrid(Grid grid) {
        for (Station man : Main.listManStations) {
            man.manufacture();
        }
        for (Transporter transporter : Main.listTransporters) {
            Location newLocation = pathfinder.getNextLocation(transporter.location, transporter.getDestination().location);
            grid.removeNode(transporter);
            transporter.setLocation(newLocation);
            grid.addNode(transporter);
        }
    }



    /*
    private static Location getRandomLocation() {
        return new Location(random.nextInt(gridSize), random.nextInt(gridSize));
    }*/
}