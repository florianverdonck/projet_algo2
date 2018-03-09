
public class Compagnie {

  private String iata;
  private String country;
  private String nom;

  public Compagnie(String iata, String country, String nom) {
    this.iata = iata;
    this.country = country;
    this.nom = nom;
  }

public String getIata() {
	return iata;
}

public void setIata(String iata) {
	this.iata = iata;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

@Override
public String toString() {
	return "Compagnie [iata=" + iata + ", country=" + country + ", nom=" + nom + "]";
}
  
  

}
