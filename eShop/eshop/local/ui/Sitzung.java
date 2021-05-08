package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eshop.local.domain.Bestand;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.User;

public class Sitzung {
	
	public static Bestand bst;
	public static Bestand wnk;
	public static User user;
	private static Eingabeverarbeitung ev;
	
	private static Artikel aktuellerArtikel;
	private static String aktuelleSitzungsNr;
	
	public Sitzung() throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new Bestand();
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new Bestand(getSitzungsNr()+".txt");

		// Jede "Sitzung", ob angemeldet oder nicht, erzeugt ein Kunden-Objekt. Zuerst aber nur mit "sitzungsNr" und wenigen Rechten.
		user = new User();
	
		// 
		ev = new Eingabeverarbeitung();
	}
	
	public Sitzung(String datei) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new Bestand(datei);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new Bestand(getSitzungsNr()+".txt");

		// Jede "Sitzung", ob angemeldet oder nicht, erzeugt ein Kunden-Objekt. Zuerst aber nur mit "sitzungsNr" und wenigen Rechten.
		user = new User();
	
		// 
		ev = new Eingabeverarbeitung();
	}
	
	public String neueSitzungsNr() {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		aktuelleSitzungsNr = sitzungsNr;
		return sitzungsNr;
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
	
	
	public static void run() throws IOException {
		
		String input = "";
		ev.setLevel("startmenue");
	
		do {
			ev.gibMenueAus();
			try {
			
				ev.einlesenUndVerarbeiten();
				input = ev.getLine();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!input.equals("q"));
	}
	
	public static String getSitzungsNr() {
		return aktuelleSitzungsNr;
	}

	public static void setAktuellerArtikel(Artikel a) {
		aktuellerArtikel = a;
	}
	
	public static Artikel getAktuellerArtikel() {
		return aktuellerArtikel;
	}
}
