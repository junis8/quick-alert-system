import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class Main {
    public static void main(String[] args) throws Exception {
        KDTree2D kdTree = new KDTree2D();
        HeapPriorityQueue<Double, Earthquake> earthquakeQueue = new HeapPriorityQueue<>();

        // Komutlar
        String[] commands = {
            "0 add -105.7 -24.3 Tom",
            "1 add 21.2 -38.6 Jane",
            "4 add -11.0 63.1 Taylor",
            "5 add -79.2 37.3 John",
            "6 add -125.1 -38.5 Henry",
            "8 delete Taylor",
            "10 query-largest"
        };

        for (String command : commands) {
            processCommand(command, kdTree, earthquakeQueue);
        }

        // Deprem verilerini yükle
        loadEarthquakes("earthquake.xml", earthquakeQueue);

        // En büyük depremi sorgula
        System.out.println("Largest Earthquake: " + earthquakeQueue.max().getValue());
    }

    public static void processCommand(String command, KDTree2D kdTree, HeapPriorityQueue<Double, Earthquake> earthquakeQueue) {
        String[] parts = command.split(" ");
        int time = Integer.parseInt(parts[0]);
        String action = parts[1];

        switch (action) {
            case "add":
                double x = Double.parseDouble(parts[2]);
                double y = Double.parseDouble(parts[3]);
                String name = parts[4];
                kdTree.insert(new Point2D(x, y));
                System.out.println("Added Watcher: " + name + " at (" + x + ", " + y + ")");
                break;

            case "delete":
                name = parts[2];
                List<Point2D> points = kdTree.getAllPoints();
                for (Point2D point : points) {
                    // Custom logic to find and delete based on Watcher name if stored
                }
                System.out.println("Deleted Watcher: " + name);
                break;

            case "query-largest":
                System.out.println("Largest Earthquake: " + earthquakeQueue.max().getValue());
                break;

            default:
                System.out.println("Unknown command: " + command);
        }
    }

    public static void loadEarthquakes(String filename, HeapPriorityQueue<Double, Earthquake> earthquakeQueue) throws Exception {
        File file = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        NodeList earthquakeList = doc.getElementsByTagName("earthquake");

        for (int i = 0; i < earthquakeList.getLength(); i++) {
            Node node = earthquakeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getElementsByTagName("id").item(0).getTextContent();
                int time = Integer.parseInt(element.getElementsByTagName("time").item(0).getTextContent());
                String place = element.getElementsByTagName("place").item(0).getTextContent();
                String[] coords = element.getElementsByTagName("coordinates").item(0).getTextContent().split(", ");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                double magnitude = Double.parseDouble(element.getElementsByTagName("magnitude").item(0).getTextContent());

                Earthquake earthquake = new Earthquake(id, time, place, x, y, magnitude);
                earthquakeQueue.insert(magnitude, earthquake);
            }
        }
    }
}
