import java.util.HashSet;
import java.util.Set;

public class Aeroport {

  private String iata;
  private String nom;
  private String ville;
  private String pays;
  private Set<Vol> in;
  private Set<Vol> out;

  public Aeroport(String iata, String nom, String ville, String pays) {
    super();
    this.iata = iata;
    this.nom = nom;
    this.ville = ville;
    this.pays = pays;
    in = new HashSet<>();
    out = new HashSet<>();
  }

}
