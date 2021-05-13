package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.RechnungVektorListe;
import eshop.local.domain.UserVektorListe;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.User;

public class Sitzung {
	
	public static ArtikelVektorListe bst;
	public static ArtikelVektorListe wnk;
	public static UserVektorListe usr;
	public static RechnungVektorListe rch;
	private static Eingabeverarbeitung ev;
	
	private static Artikel aktuellerArtikel;
	private static String aktuelleSitzungsNr;
	private static User aktuellerUser;
	
	private static String datei = "";
	
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
	
	public Sitzung(String dateiArtikel) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		
		aktuellerUser = new User();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		// 
		ev = new Eingabeverarbeitung();
		
		this.datei = dateiArtikel;
	}
	
	public Sitzung(String dateiArtikel, String dateiUser) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		
		aktuellerUser = new User();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		usr = new UserVektorListe(dateiUser);
		
		// 
		ev = new Eingabeverarbeitung();
		
		this.datei = dateiArtikel;
	}
	
public Sitzung(String dateiArtikel, String dateiUser, String dateiRechnungen) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		
		aktuellerUser = new User();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		usr = new UserVektorListe(dateiUser);
		
		rch = new RechnungVektorListe(dateiRechnungen);
		
		// 
		ev = new Eingabeverarbeitung();
		
		this.datei = dateiArtikel;
	}
	
	public String neueSitzungsNr() {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		aktuelleSitzungsNr = sitzungsNr;
		return sitzungsNr;
	}
	
	public static void gibArtikellisteUnsortiertAus(List<Artikel> vectorListe) {
		if (vectorListe.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Artikel artikel : vectorListe) {
				System.out.println(artikel);
			}
		}
	}

	public static void gibArtikellisteAlphabetischAus(List<Artikel> vectorListe) {

		int anzahl = vectorListe.size();
		List<Artikel> tempListe = new Vector<Artikel>();
		
		//Sortieren
		for(int loop = 0; loop <= anzahl; loop++) {
			Artikel tempArtikel = new Artikel();
			for (Artikel artikel : vectorListe) {
				if(tempArtikel.getName().isEmpty()) {
					tempArtikel = new Artikel(artikel.getName(), artikel.getNummer(), artikel.getAnzahl(), artikel.getPreis());
				}
				//deep copy, not just pointer:
				int vergleichen = artikel.getName().toLowerCase().compareTo(tempArtikel.getName().toLowerCase());
				if(vergleichen <= 0){
					
					tempArtikel = new Artikel(artikel.getName(), artikel.getNummer(), artikel.getAnzahl(), artikel.getPreis());
				}
			}
			if(!tempArtikel.getName().isEmpty()) {
				tempListe.add(tempArtikel);
			}
			vectorListe.remove(tempArtikel);
		}
		//Sortierte Liste ausgeben
		for (Artikel artikel : tempListe) {
			System.out.println(artikel);
		}

	}
	
	public static void gibArtikellisteNummerischAus(List<Artikel> vectorListe) {

		int anzahl = vectorListe.size();
		List<Artikel> tempListe = new Vector<Artikel>();

		//Sortieren
		for(int loop = 0; loop <= anzahl; loop++) {
			Artikel tempArtikel = new Artikel();
			for (Artikel artikel : vectorListe) {
				if(tempArtikel.getNummer() == 0) {
					//pointer sufficient but why not make a deep copy
					tempArtikel = new Artikel(artikel.getName(), artikel.getNummer(), artikel.getAnzahl(), artikel.getPreis());
				}
				if(artikel.getNummer() <= tempArtikel.getNummer()){
					//deep copy necessary
					tempArtikel = new Artikel(artikel.getName(), artikel.getNummer(), artikel.getAnzahl(), artikel.getPreis());
				}
			}
			if(!tempArtikel.getName().isEmpty()) {
				tempListe.add(tempArtikel);
			}
			vectorListe.remove(tempArtikel);
		}
		//Sortierte Liste ausgeben
		for (Artikel artikel : tempListe) {
			System.out.println(artikel);
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
	
	public static List<Artikel> produceAenderungsListe() throws IOException{
		List<Artikel> liste = Sitzung.bst.gibAlleArtikel();
		ArtikelVektorListe listeOriginal = new ArtikelVektorListe(datei);
		List<Artikel> aenderungsListe = new Vector<Artikel>();
		int aenderung = 0;
		for(Artikel neu : liste) {
			for(Artikel alt : listeOriginal.gibAlleArtikel()) {
				if(neu.getNummer() == alt.getNummer()) {
					aenderung = neu.getAnzahl() - alt.getAnzahl();
					Artikel aenderungArtikel = new Artikel(neu.getName(), neu.getNummer(), aenderung, neu.getPreis());
					aenderungsListe.add(aenderungArtikel);
				}
			}
		}
		return aenderungsListe;
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
	
	public static void setAktuellerUser(User u) {
		aktuellerUser = u;
	}
	
	public static User getAktuellerUser() {
		return aktuellerUser;
	}
}
