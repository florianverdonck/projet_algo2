import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	
	//Nous nous servirons de cette variable plus tard
	 private String node = null;
	 
	 private Aeroport aeroport;
	 private Vol vol;
	 private Coordonnees coordonnees;
	
	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */ 
	 
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

		if (qName.equals("airport")) {
			aeroport = new Aeroport(qName, qName, qName, qName);
			
		} else if (qName.equals("route")) {
			//System.out.println("Route : " + attributes.getLength());
		} else {
			System.out.println("Non identifié : " + qName);
		}
	}

	public void endElement(String uri, String localName, String qName)
	      throws SAXException{
	  //System.out.println("Fin de l'élément " + qName);       
	}
	
	

   //début du parsing
   public void startDocument() throws SAXException {
      System.out.println("Début du parsing");
   }
   //fin du parsing
   public void endDocument() throws SAXException {
      System.out.println("Fin du parsing");
   }   
	
	public Graph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
