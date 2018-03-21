import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
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

  public void calculerItineraireMiniminantDistance(String iataSource, String iataDestination,String fichier) {
    
	  Aeroport source = aeroports.get(iataSource);
	  Aeroport dest = aeroports.get(iataDestination);
	  
	  source.setCout(0);
	  
	  Comparator<Aeroport> comparateurCouts = Comparator.comparingDouble(Aeroport::getCout);
	  PriorityQueue<Aeroport> aeroParCout = new PriorityQueue<>(comparateurCouts);
	  
	  aeroParCout.addAll(aeroports.values());
	  
	  while (!aeroParCout.isEmpty()) {
		  Vol vol = null;
		  
		  Aeroport aeroCourant = aeroParCout.remove();
		  
		  System.out.println(aeroCourant);
		  
		  Set<Vol> outs = aeroCourant.getOut();

		  for (Vol volParcouru : outs) {
			  if (vol == null || volParcouru.getDistance() > vol.getDistance()) {
				  System.out.println("Le meilleur vol");
				  vol = volParcouru;
			  }
			  
			  vol.getDestination().setCout(aeroCourant.getCout() + vol.getDistance());
			  
			  if (vol.getDestination().getVolArrivee() == null || vol.getDestination().getCout() > aeroCourant.getCout() + vol.getDistance()) {
				  vol.getDestination().setVolArrivee(vol);
			  }
		  }
	  }
	  
	  Aeroport aeroCourant2 = dest;
	  
	  Deque<Aeroport> trajet = new ArrayDeque<Aeroport>();
	  
	  while (aeroCourant2 != source) {
		  System.out.println("xxx");
		  System.out.println(aeroCourant2.getIata());
		  trajet.push(aeroCourant2);
		  aeroCourant2 = aeroCourant2.getVolArrivee().getSource();
	  }
	 
	  while (trajet.size() > 0) {
		  System.out.println("yyy");
		  System.out.println(trajet.pop());
	  }
	  
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
          chemin(chemins, aDest);
          return;
        }

      }
    }
    throw new PasDeVolException();



  }

  private void chemin(Map<Aeroport, Vol> chemins, Aeroport dest) {

    Vol vol = chemins.get(dest);
    if (vol != null) {
      System.out.println(vol.getSource().getIata() + " " + vol.getDestination().getIata());
      chemin(chemins, vol.getSource());
    }
  }
}
