
public class Vol {

  private Aeroport source;
  private Aeroport destination;
  private Compagnie compagnie;
  private int distance;

  public Vol(Aeroport source, Aeroport destination, Compagnie compagnie, int distance) {
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

  public int getDistance() {
    return distance;
  }



}
