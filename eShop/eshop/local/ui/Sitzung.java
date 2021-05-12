package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.UserVektorListe;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.User;

public class Sitzung {
	
	public static ArtikelVektorListe bst;
	public static ArtikelVektorListe wnk;
	public static UserVektorListe usr;
	private static Eingabeverarbeitung ev;
	
	private static Artikel aktuellerArtikel;
	private static String aktuelleSitzungsNr;
	
	public Sitzung() throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe();
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		// 
		ev = new Eingabeverarbeitung();
	}
	
	public Sitzung(String datei) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(datei);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		// 
		ev = new Eingabeverarbeitung();
	}
	
	public Sitzung(String dateiArtikel, String dateiUser) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		usr = new UserVektorListe(dateiUser);
		
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
	
	public static void gibUserlisteAus(List<User> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (User user : liste) {
				System.out.println(user);
			}
		}
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
	
	public static ArtikelVektorListe getWarenkorb() {
		return wnk;
	}

	public static void setAktuellerArtikel(Artikel a) {
		aktuellerArtikel = a;
	}
	
	public static Artikel getAktuellerArtikel() {
		return aktuellerArtikel;
	}
}
