import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

  // Nous nous servirons de cette variable plus tard
  private String node = null;
  private String iata = null;

  private Graph graph = new Graph();

  /**
   * Redéfinition de la méthode pour intercepter les événements
   */

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {

    node = qName;

    if (qName.equals("airport")) {

      iata = attributes.getValue("iata");
      graph.ajouterAeroport(new Aeroport(iata, attributes.getValue("name"),
          attributes.getValue("city"), attributes.getValue("country"), new Coordonnees()));

    } else if (qName.equals("airline")) {

      iata = attributes.getValue("iata");
      graph.ajouterCompagnie(new Compagnie(iata, attributes.getValue("country"), null));

    } else if (qName.equals("route")) {

      Aeroport src = graph.getAeroport(attributes.getValue("source"));
      Aeroport dest = graph.getAeroport(attributes.getValue("destination"));
      Compagnie compagnie = graph.getCompagnie(attributes.getValue("airline"));

      Vol vol = new Vol(src, dest, compagnie,
          Util.distance(src.getPosition().getLatitude(), src.getPosition().getLongitude(),
              dest.getPosition().getLatitude(), dest.getPosition().getLongitude()));

      graph.ajouterVol(vol);
      graph.getAeroport(src.getIata()).getOut().add(vol);

    }

  }

  /*
   * @Override public void endElement(String uri, String localName, String qName) throws
   * SAXException {
   * 
   * if (qName.equals("airport")) {
   * 
   * graph.aeroports.put(aeroport.getIata(), aeroport);
   * 
   * } else if (qName.equals("airline")) {
   * 
   * // DEPLACER COMPAGNIES DANS LE GRAPH graph.compagnies.put(compagnie.getIata(), compagnie);
   * 
   * } else if (qName.equals("route")) {
   * 
   * graph.vols.add(vol);
   * 
   * // Ajouter ce vol dans OUT de sa source
   * 
   * graph.aeroports.get(vol.getSource().getIata()).getOut().add(vol);
   * 
   * 
   * } }
   */

  // début du parsing
  @Override
  public void startDocument() throws SAXException {
    System.out.println("Début du parsing");
  }

  // fin du parsing
  @Override
  public void endDocument() throws SAXException {
    System.out.println("Fin du parsing");
  }


  /**
   * permet de récupérer la valeur d'un nœud
   */
  @Override
  public void characters(char[] data, int start, int end) {

    String str = new String(data, start, end);

    if (node != null) {
      if (node.equals("longitude")) {
        graph.getAeroport(iata).getPosition().setLongitude(Double.parseDouble(str));
      } else if (node.equals("latitude")) {
        graph.getAeroport(iata).getPosition().setLatitude(Double.parseDouble(str));
      } else if (node.equals("airline")) {
        graph.getCompagnie(iata).setNom(str);
      }
      node = null;
    }

  }

  public Graph getGraph() {
    return this.graph;
  }

}
