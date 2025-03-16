public class Earthquake {
    private String id;
    private int time;
    private String place;
    private Point2D location;
    private double magnitude;

    public Earthquake(String id, int time, String place, double x, double y, double magnitude) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.location = new Point2D(x, y);
        this.magnitude = magnitude;
    }

    public String getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public Point2D getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return "Earthquake{id='" + id + "', time=" + time + ", place='" + place + "', location=" + location +
               ", magnitude=" + magnitude + '}';
    }
}
