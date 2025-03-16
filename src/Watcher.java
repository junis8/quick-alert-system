public class Watcher {
    private String name;
    private Point2D location;

    public Watcher(String name, double x, double y) {
        this.name = name;
        this.location = new Point2D(x, y);
    }

    public String getName() {
        return name;
    }

    public Point2D getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Watcher{name='" + name + "', location=" + location + '}';
    }
}
