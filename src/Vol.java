public class Vol {

  private Aeroport source;
  private Aeroport destination;
  private Compagnie compagnie;
  private double distance;

  public Vol(Aeroport source, Aeroport destination, Compagnie compagnie, double distance) {
    super();
    this.source = source;
    this.destination = destination;
    this.compagnie = compagnie;
    this.distance = distance;
  }

  public Aeroport getSource() {
    return source;
  }

  public Aeroport getDestination() {
    return destination;
  }

  public Compagnie getCompagnie() {
    return compagnie;
  }

  public double getDistance() {
    return distance;
  }



}
