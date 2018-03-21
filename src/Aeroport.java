import java.util.HashSet;
import java.util.Set;

public class Aeroport {

	private String iata;
	private String nom;
	private String ville;
	private String pays;
	private Coordonnees position;
	private Set<Vol> out;
	private double cout;
	private Vol volArrivee;

	public Aeroport() {
		super();
	}

	public Aeroport(String iata, String nom, String ville, String pays, Coordonnees position) {
		super();
		this.iata = iata;
		this.nom = nom;
		this.ville = ville;
		this.pays = pays;
		this.position = position;
		this.out = new HashSet<Vol>();
		this.cout = Double.MAX_VALUE;
	}

	public String getIata() {
		return iata;
	}

	public String getNom() {
		return nom;
	}

	public String getVille() {
		return ville;
	}

	public String getPays() {
		return pays;
	}

	public Coordonnees getPosition() {
		return position;
	}

	public Set<Vol> getOut() {
		return out;
	}

	@Override
	public String toString() {
		return "Aeroport [iata=" + iata + ", nom=" + nom + ", ville=" + ville + ", pays=" + pays + ", position="
				+ position + "]";
	}

	public double getCout() {
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}

	public Vol getVolArrivee() {
		return volArrivee;
	}

	public void setVolArrivee(Vol volArrivee) {
		this.volArrivee = volArrivee;
	}

}
