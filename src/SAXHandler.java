import java.util.HashMap;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	
	//Nous nous servirons de cette variable plus tard
	 private String node = null;
	 
	 private Aeroport aeroport;
	 private Coordonnees coordonnees;
	 private Compagnie compagnie;
	 private Vol vol;
	 
	 private Graph graph = new Graph();

	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */ 
	 
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		
		node = qName;

		if (qName.equals("airport")) {
			
			aeroport = new Aeroport(
					attributes.getValue("iata"),
					attributes.getValue("name"),
					attributes.getValue("city"),
					attributes.getValue("country"),
					new Coordonnees()
					);
			
		} else if (qName.equals("airline")) {
			
			compagnie = new Compagnie(attributes.getValue("iata"), attributes.getValue("country"), null);
			
		} else if (qName.equals("route")) {
			
			Aeroport src = graph.aeroports.get(attributes.getValue("source"));
			Aeroport dest = graph.aeroports.get(attributes.getValue("destination"));
			Compagnie compagnie = graph.compagnies.get(attributes.getValue("airline"));
			
			vol = new Vol(src, dest, compagnie,-1.0);
			
		}
		
	}

	public void endElement(String uri, String localName, String qName)
	      throws SAXException{
		
		if (qName.equals("airport")) {
			
			graph.aeroports.put(aeroport.getIata(), aeroport);
			
		} else if (qName.equals("airline")) {
			
			// DEPLACER COMPAGNIES DANS LE GRAPH
			graph.compagnies.put(compagnie.getIata(), compagnie);
			
		} else if (qName.equals("route")) {
			
			graph.vols.add(vol);
			
			// Ajouter ce vol dans OUT de sa source
			
			graph.aeroports.get(vol.getSource().getIata()).getOut().add(vol);
			
			
		}
	}
	
   //début du parsing
   public void startDocument() throws SAXException {
      System.out.println("Début du parsing");
   }
   //fin du parsing
   public void endDocument() throws SAXException {
      System.out.println("Fin du parsing");
   }  
	
   
   /**
    * permet de récupérer la valeur d'un nœud
    */  
   public void characters(char[] data, int start, int end){ 
	   
	   String str = new String(data, start, end);
	   
	   
	   if (node != null) {
		   if (node.equals("longitude")) {
			   aeroport.getPosition().setLongitude(str);
		   }
		   else if (node.equals("latitude")) {
			   aeroport.getPosition().setLatitude(str);
		   }
		   else if (node.equals("airline")) {
			   compagnie.setNom(str);
		   }
		   node = null;
	   }
      
   }
   
	public Graph getGraph() {
		return this.graph;
	}

}
