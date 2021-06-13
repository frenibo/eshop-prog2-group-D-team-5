package eshop.local.client;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CUI {
	private BufferedReader in;  // Konsolen-Input

	private eShopInterface e;  // Fassade zur Anwendung auf dem Server
	
	public CUI (String h, int p) {

		e = new eShopFassade(h, p);	// EchoDienstFassade "implementiert" EchoDienstInterface, also ok.
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	void protokollStart() {
		String line="", lineS="";
        do {
        	
   		    try {
				line = in.readLine();		// lese line von Konsole
			} catch (Exception e1) {}	
   		    try {
   		    	// hier Menue auswerten. Aber es gibt nur "echo()"
   		    	eShopInterface.gibMenueAus();
				eShopInterface.einlesenUndVerarbeiten(line);		// e Objekt f�hrt "Dienst" echo aus (mit Parameter line)	    
			} catch (Exception e) {}
   		    // hier evtl weitere Men�-Auswahl...
   		} while (!lineS.equals("#"));
	}
	
 	public static void main(String[] args) {
        String host = "localhost";
        int port = 123; 
 		CUI client = new CUI(host, port);
        client.protokollStart();
 		
} } 
