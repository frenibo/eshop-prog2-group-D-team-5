package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eshop.local.domain.Bestand;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.User;

public class Sitzung {
	
	public static Bestand bst;
	public static Bestand wnk;
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
	
	public static double gibGesamtpreisAus(List<Artikel> liste) {
		double Gesamtpreis = 0.00;
		for(Artikel artikel : liste) {
			double produkt = artikel.getPreis() * artikel.getAnzahl();
			Gesamtpreis += produkt;
		}
		return Gesamtpreis;
	}
	
	
	public static void run() throws IOException {
		
		String input = "";
		cleanUp();
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
	
	public static boolean cleanUp() {
		//legt für jedes Warenkorb-Textobjekt(.txt) im Speicher eine Artikelliste an
		//und verschiebt jeden Artikel in die Bestand-Artikelliste
		//löscht dann die Warenkorb-Textobjekte(.txt)
		//und speichert dann.
		
		//Achtung! Wird der neue Warenkorb vor dem cleanup erstellt, so wird auch er gelöscht
		//dies ist momentan der Fall.
		//Warenkorb könnte in cleanUp() erstellt werden.
		
		return true;
	}
	
	public static String getSitzungsNr() {
		return aktuelleSitzungsNr;
	}
	
	public static Bestand getWarenkorb() {
		return wnk;
	}

	public static void setAktuellerArtikel(Artikel a) {
		aktuellerArtikel = a;
	}
	
	public static Artikel getAktuellerArtikel() {
		return aktuellerArtikel;
	}
}
