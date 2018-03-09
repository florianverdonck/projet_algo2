import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

  Set<Vol> vols;
  Map<String, Aeroport> aeroports;

  public Graph() {
    super();
    vols = new HashSet<>();
    aeroports = new HashMap<>();
  }

  public void ajouterVol(Vol vol) {
    Util.checkObject(vol);
    vols.add(vol);
  }

  public void ajouterAeroport(Aeroport aero) {
    Util.checkObject(aero);
    aeroports.put(aero.getNom(), aero);
  }

  public void calculerItineraireMiniminantDistance(String source, String destination,
      String fichier) {
    Map<String, Double> provisoire = new HashMap<>();
    Map<String, Double> definitif = new HashMap<>();

    for (String nom : aeroports.keySet()) {
      provisoire.put(nom, null);
      definitif.put(nom, null);
    }

    aeroports.get(source).getOut();



  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination,
      String fichier) {



  }
}
