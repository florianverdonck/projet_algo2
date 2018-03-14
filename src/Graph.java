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

  public void calculerItineraireMiniminantDistance(String source, String destination,
      String fichier) {
    /*
     * Map<String, Double> provisoire = new HashMap<>(); Map<String, Double> definitif = new
     * HashMap<>();
     * 
     * for (String iata : aeroports.keySet()) { provisoire.put(iata, null); definitif.put(iata,
     * null); }
     * 
     * for (Vol vol : aeroports.get(source).getOut()) {
     * 
     * }
     */



  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination,
      String fichier) throws PasDeVolException {
    Deque<Aeroport> file = new ArrayDeque<>();
    Set<Aeroport> visites = new HashSet<>();
    Map<Aeroport, Vol> chemins = new HashMap<>();

    Aeroport aSource = aeroports.get(source);
    Aeroport aDest;

    file.push(aSource);
    chemins.put(aSource, null);

    for (Aeroport a : file) {
      for (Vol vol : a.getOut()) {
        aDest = vol.getDestination();
        System.out.println(aDest.getIata());
        if (aDest.getIata().equals(destination)) {
          System.out.println("debut chemin");
          chemin(chemins, aDest);
          return;
        }
        if (!visites.contains(aDest)) {
          file.addLast(aDest);
          visites.add(aDest);
          chemins.put(aDest, vol);
        }
      }
    }
    throw new PasDeVolException();



  }

  private void chemin(Map<Aeroport, Vol> chemins, Aeroport dest) {
    Vol vol = chemins.get(dest);
    if (vol != null) {
      System.out.println(vol);
      chemin(chemins, vol.getSource());
    }
  }
}
