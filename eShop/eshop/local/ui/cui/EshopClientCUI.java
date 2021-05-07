package eshop.local.ui.cui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eshop.local.domain.Bestand;
import eshop.local.valueobjects.User;
import eshop.local.valueobjects.Artikel;
import eshop.local.ui.Eingabeverarbeitung;
import eshop.local.ui.Menue;

public class EshopClientCUI {
	
	public static Bestand bst;
	private String sitzungsNr = neueSitzungsNr();
	public static User user;
	public static Menue menue;
	private static Eingabeverarbeitung ev;
	
	public EshopClientCUI(String datei) throws IOException {
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new Bestand(datei);

		// Jede "Sitzung", ob angemeldet oder nicht, erzeugt ein Kunden-Objekt. Zuerst aber nur mit "sitzungsNr" und wenigen Rechten.
		user = new User(sitzungsNr);
		
		// Menue-Objekt mit initialem Menue-Level "1".
		menue = new Menue(1);
		
		// 
		ev = new Eingabeverarbeitung();
	}
	
	public static void main(String[] args) {
		EshopClientCUI cui;
		try {
			cui = new EshopClientCUI("BST");
			cui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


public void run() {
		
	// Variable für Eingaben von der Konsole
	String input = ""; 

	// Hauptschleife der Benutzungsschnittstelle
	do {
		menue.gibMenueAus();
		try {
			
			ev.liesEingabe();
			input = ev.getLine();
			ev.verarbeitung(input);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} while (!input.equals("q"));
}
	
	
	public static void gibArtikellisteAus(List<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Artikel artikel : liste) {
				System.out.println(artikel);
			}
		}
	}
	
	public String neueSitzungsNr() {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		return sitzungsNr;
	}
	
	public static void setLevel(int level) {
		menue.setMenueLevel(level);
		ev.setLevel(level);
	}
}