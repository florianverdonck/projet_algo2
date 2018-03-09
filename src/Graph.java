import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    for (Vol vol : aeroports.get(source).getOut()) {

    }



  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination,
      String fichier) throws PasDeVolException {
    Deque<Aeroport> file = new ArrayDeque<>();
    Set<Aeroport> visites = new HashSet<>();
    Map<Aeroport, Vol> chemins = new HashMap<>();
    Vol[] vols;

    Aeroport aSource = aeroports.get(source);
    Aeroport aDest;

    file.push(aSource);
    chemins.put(aSource);

    for (Aeroport a : file) {
      for (Vol vol : a.getOut()) {
        aDest = vol.getDestination();
        if (aDest.getNom().equals(destination)) {

          break;
        }
        if (!visites.contains(aDest)) {
          file.addLast(aDest);
          visites.add(aDest);
          chemins.put(aDest, vol);
        }
      }
      file.removeFirst();
    }

    if (file.isEmpty())
      throw new PasDeVolException();



  }
}
