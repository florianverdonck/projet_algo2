import java.io.File;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Graph {

  Set<Vol> vols;
  Map<String, Aeroport> aeroports;
  Map<String, Compagnie> compagnies;

  public Graph() {
    super();
    vols = new HashSet<>();
    aeroports = new HashMap<>();
    compagnies = new HashMap<>();
  }

  public void ajouterVol(Vol vol) {
    Util.checkObject(vol);
    vols.add(vol);
  }

  public void ajouterAeroport(Aeroport aero) {
    Util.checkObject(aero);
    aeroports.put(aero.getIata(), aero);
  }

  public void ajouterCompagnie(Compagnie comp) {
    compagnies.put(comp.getIata(), comp);
  }

  public Aeroport getAeroport(String iata) {
    return aeroports.get(iata);
  }

  public Compagnie getCompagnie(String iata) {
    return compagnies.get(iata);
  }

  public void afficher() {
    int cpt = 0;
    for (Aeroport aero : aeroports.values()) {
      System.out.println(aero.toString());
      cpt++;
    }
    System.out.println(cpt);
  }

  public void calculerItineraireMiniminantDistance(String iataSource, String iataDestination,
      String fichier) {

    Aeroport source = aeroports.get(iataSource);
    Aeroport dest = aeroports.get(iataDestination);

    source.setCout(0);

    Comparator<Aeroport> comparateurCouts = Comparator.comparingDouble(Aeroport::getCout);
    PriorityQueue<Aeroport> aeroParCout = new PriorityQueue<>(comparateurCouts);

    // J'ajoute tous les aéroports
    aeroParCout.addAll(aeroports.values());

    while (!aeroParCout.isEmpty()) {

      Aeroport aeroCourant = aeroParCout.remove();

      Set<Vol> volsVoisins = aeroCourant.getOut();

      for (Vol volVoisin : volsVoisins) {

        Aeroport aeroVoisin = volVoisin.getDestination();

        if (aeroVoisin.getCout() > aeroCourant.getCout() + volVoisin.getDistance()) {

          aeroVoisin.setCout(aeroCourant.getCout() + volVoisin.getDistance());

          aeroVoisin.setVolArrivee(volVoisin);
        }
      }
    }

    Deque<Vol> trajets = new ArrayDeque<Vol>();

    Vol volArrivee = dest.getVolArrivee();

    double distance = 0;
    while (volArrivee.getSource() != source) {
      trajets.addFirst(volArrivee);
      System.out.println("Ajout de (" + volArrivee.getSource().getIata() + " <-"
          + volArrivee.getDestination().getIata() + ")" + "[" + volArrivee.getDistance() + "]");
      distance += volArrivee.getDistance();
      volArrivee = volArrivee.getSource().getVolArrivee();
    }

    trajets.addFirst(volArrivee);
    distance += volArrivee.getDistance();
    System.out.println("Ajout de (" + volArrivee.getSource().getIata() + " <-"
        + volArrivee.getDestination().getIata() + ")" + "[" + volArrivee.getDistance() + "]");
    System.out.println(distance);

    ecrireXML(trajets, source, dest, fichier);



  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination,
      String fichier) throws PasDeVolException {
    Deque<Aeroport> file = new ArrayDeque<>();
    Set<Aeroport> visites = new HashSet<>();
    Map<Aeroport, Vol> chemins = new HashMap<>();

    Aeroport aSource = aeroports.get(source);
    Aeroport aDest;

    file.push(aSource);
    visites.add(aSource);
    chemins.put(aSource, null);


    while (file.size() > 0) {
      for (Vol vol : file.removeFirst().getOut()) {
        aDest = vol.getDestination();
        if (visites.add(aDest)) {
          file.addLast(aDest);
          chemins.put(aDest, vol);
        }
        if (aDest.getIata().equals(destination)) {
          Deque<Vol> volsOrdonnes = new ArrayDeque<>();
          Vol volOrdre = chemins.get(aDest);
          while (volOrdre != null) {
            volsOrdonnes.push(volOrdre);
            volOrdre = chemins.get(volOrdre.getSource());
          }
          ecrireXML(volsOrdonnes, aSource, aDest, fichier);
          return;
        }

      }
    }
    throw new PasDeVolException();



  }

  private void ecrireXML(Deque<Vol> volsOrdonnes, Aeroport aSource, Aeroport aDest,
      String fichier) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder;
      docBuilder = docFactory.newDocumentBuilder();

      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("trajet");
      rootElement.setAttribute("depart", aSource.getNom());
      rootElement.setAttribute("destination", aDest.getNom());
      doc.appendChild(rootElement);
      double distanceTotale = 0;
      int numero = 1;

      Vol vol = volsOrdonnes.pop();
      while (vol != null) {
        Element volElement = doc.createElement("vol");
        volElement.setAttribute("compagnie", vol.getCompagnie().getNom());
        volElement.setAttribute("nombreKm", String.valueOf(vol.getDistance()));
        distanceTotale += vol.getDistance();
        volElement.setAttribute("numero", String.valueOf(numero));
        Element sourceElement = doc.createElement("source");
        sourceElement.setAttribute("iataCode", vol.getSource().getIata());
        sourceElement.setAttribute("pays", vol.getSource().getPays());
        sourceElement.setAttribute("ville", vol.getSource().getVille());
        sourceElement.setTextContent(vol.getSource().getNom());
        volElement.appendChild(sourceElement);
        Element destElement = doc.createElement("destination");
        destElement.setAttribute("iataCode", vol.getDestination().getIata());
        destElement.setAttribute("pays", vol.getDestination().getPays());
        destElement.setAttribute("ville", vol.getDestination().getVille());
        destElement.setTextContent(vol.getDestination().getNom());
        volElement.appendChild(destElement);
        rootElement.appendChild(volElement);
        numero++;
        if (volsOrdonnes.isEmpty())
          vol = null;
        else
          vol = volsOrdonnes.pop();
      }

      rootElement.setAttribute("distance", String.valueOf(distanceTotale));

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(fichier));
      transformer.transform(source, result);
      System.out.println("File saved!");

    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TransformerConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TransformerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
