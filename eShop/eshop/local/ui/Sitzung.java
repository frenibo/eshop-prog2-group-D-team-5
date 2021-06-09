package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.EventVektorListe;
import eshop.local.domain.RechnungVektorListe;
import eshop.local.domain.UserVektorListe;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.cui.Main;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Event;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.Sitzungsnummer;
import eshop.local.valueobjects.User;

public class Sitzung {
	
	public static ArtikelVektorListe bst;
	public static ArtikelVektorListe wnk;
	public static UserVektorListe usr;
	public static RechnungVektorListe rch;
	public static EventVektorListe evt;
	public static Eingabeverarbeitung ev;
	
	private static Artikel aktuellerArtikel;
	private static String aktuelleSitzungsNr;
	private static User aktuellerUser;
	private static Event aktuellesEvent;
	
	private static String datei = "";
	
	private static boolean run = true;
	
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
		
		Sitzung.datei = dateiArtikel;
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
		
		Sitzung.datei = dateiArtikel;
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
		
		Sitzung.datei = dateiArtikel;
	}

public Sitzung(String dateiArtikel, String dateiUser, String dateiRechnungen, String dateiEvents) throws IOException {
	
	aktuelleSitzungsNr = neueSitzungsNr();
	
	aktuellerUser = new User();
	// die Bst-Verwaltung erledigt die Aufgaben, 
	// die nichts mit Ein-/Ausgabe zu tun haben
	bst = new ArtikelVektorListe(dateiArtikel);
	
	// neue Warenkorb-Liste wird angelegt.
	wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");

	usr = new UserVektorListe(dateiUser);
	
	rch = new RechnungVektorListe(dateiRechnungen);
	
	evt = new EventVektorListe(dateiEvents);
	
	// 
	ev = new Eingabeverarbeitung();
	
	Sitzung.datei = dateiArtikel;
}
	
	public String neueSitzungsNr() {
		
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		aktuelleSitzungsNr = sitzungsNr;
		return sitzungsNr;
		
		/*
		aktuelleSitzungsNr = new Sitzungsnummer().toString();
		return aktuelleSitzungsNr;
		*/
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

	public static void gibArtikellisteNummerischAus(List<Artikel> vectorListe) {

		int anzahl = vectorListe.size();
		List<Artikel> tempListe = new Vector<Artikel>();

		//Sortieren
		for(int loop = 0; loop <= anzahl; loop++) {
			Artikel tempArtikel = new Massenartikel();
			for (Artikel artikel : vectorListe) {
				if(tempArtikel.getNummer() == 0) {
					//pointer sufficient but why not make a deep copy
					tempArtikel = new Massenartikel(artikel.getName(), artikel.getNummer(), artikel.getAnzahl(), artikel.getPreis(), artikel.getPacketgroeße());
				}
				if(artikel.getNummer() <= tempArtikel.getNummer()){
					//deep copy necessary
					tempArtikel = new Massenartikel(artikel.getName(), artikel.getNummer(), artikel.getAnzahl(), artikel.getPreis(), artikel.getPacketgroeße());
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
	
	public static void run() throws IOException, ArtikelExistiertBereitsException {
		
		String input = "";

		ev.setLevel("startmenue");
	
		do {
			ev.gibMenueAus();
			try {
			
				ev.einlesenUndVerarbeiten();
				input = ev.getInput();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (run == true);
	}
	
	public static Rechnung warenkorbKaufen() throws IOException {
		bst.schreibeArtikel();
		wnk.schreibeArtikel();
		List<Artikel> liste = wnk.gibAlleArtikel();
		Rechnung rechnung = new Rechnung(getAktuellerUser(), liste);
		try {
			//sollte man so wahrscheinlich nicht machen:
			rch.fuegeRechnungEin(rechnung);
			
		} catch (ArtikelExistiertBereitsException e) {
			// Hier Fehlerbehandlung...
			e.printStackTrace();
			return rechnung = new Rechnung();
		}
		usr.schreibeUser();
		evt.schreibeEvents();
		return rechnung;
	}
	
	public static List<Artikel> produceAenderungsListe() throws IOException{
		boolean neuerArtikel = true;
		boolean gelöschterArtikel = true;
		List<Artikel> liste = Sitzung.bst.gibAlleArtikel();
		ArtikelVektorListe listeOriginal = new ArtikelVektorListe(datei);
		List<Artikel> aenderungsListe = new Vector<Artikel>();
		int aenderung = 0;
		//Änderungen in der Artikel-Anzahl finden
		for(Artikel neu : liste) {
			neuerArtikel = true;
			for(Artikel alt : listeOriginal.gibAlleArtikel()) {
				if(neu.getNummer() == alt.getNummer()) {
					neuerArtikel = false;
					aenderung = neu.getAnzahl() - alt.getAnzahl();
					if(aenderung != 0) {
						Artikel aenderungArtikel = new Massenartikel(neu.getName(), neu.getNummer(), aenderung, neu.getPreis(), neu.getPacketgroeße());
						aenderungsListe.add(aenderungArtikel);
					}
				}
			}
			//Wenn der Artikel neu eingefügt wurde
			if(neuerArtikel == true) {
				aenderungsListe.add(neu);
			}
		}
		
	//Gelöschte Artikel finden
		for(Artikel alt : listeOriginal.gibAlleArtikel()) {
			gelöschterArtikel = true;
			for(Artikel neu : liste) {
				if(neu.getNummer() == alt.getNummer()) {
					gelöschterArtikel = false;
				}
				
			}
			if(gelöschterArtikel == true) {
				Artikel aenderungArtikel = new Massenartikel(alt.getName(), alt.getNummer(), 0 - alt.getAnzahl(), alt.getPreis(), alt.getPacketgroeße());
				aenderungsListe.add(aenderungArtikel);
			}
		}
		
		return aenderungsListe;
	}
	
	public static void verschiebeVonBestandInWarenkorb(int nummer, int anzahl) {
		bst.verschiebeInWarenkorb(nummer, anzahl, Sitzung.wnk);
	}
	
	public static void verschiebeVonWarenkorbinBestand(int nummer, int anzahl) {
		wnk.verschiebeInBestand(nummer, anzahl, Sitzung.bst);
	}
	
	public static void warenkorbLeeren() {
		wnk.warenkorbLeeren();
	}
	
	public static void aendereAnzahlImBestand(int nummer, int anzahl) {
		bst.aendereAnzahl(nummer, anzahl);
	}
	
	public static void loescheArtikelImBestand(String name, int nummer) {
		bst.loescheArtikel(name, nummer);
	}
	
	public static List<User> getUserliste() {
		
		return usr.gibAlleUser();
	}
	
	public static List<Artikel> getArtikellisteAusBestand() {
		
		return bst.gibAlleArtikel();
	}
	
	public static void fuegeRechnungEin(Rechnung rechnung) throws ArtikelExistiertBereitsException {
		rch.fuegeRechnungEin(rechnung);
	}
	
	public static void fuegeUserEin(int nummer, String name) throws ArtikelExistiertBereitsException {
		usr.fuegeUserEin(nummer, name);
	}
	
		public static String fuegeArtikelEin(String name, int nummer, int anzahl, double preis, int packet) throws ArtikelExistiertBereitsException {
		return bst.fuegeArtikelEin(name, nummer, anzahl, preis, packet);
	}
	
	public static void fuegeEventEin(Event event) throws ArtikelExistiertBereitsException {
		// TODO Auto-generated method stub
		evt.fuegeEventEin(event);
		
	}
	
	public static void speichereBestand() throws IOException {
		bst.schreibeArtikel();
	}
	
	public static void speichereRechnungsliste() throws IOException {
		rch.schreibeRechnung();
	}
	
	public static void speichereUserliste() throws IOException {
		usr.schreibeUser();
	}
	
	public static void speichereEventliste() throws IOException {
		evt.schreibeEvents();		
	}
	
	public static void speichern() throws IOException {
		
		warenkorbLeeren();
		
		bst.schreibeArtikel();
		wnk.schreibeArtikel();
		rch.schreibeRechnung();
		usr.schreibeUser();
		evt.schreibeEvents();
	}
	
	public static List<Artikel> getArtikellisteAusWarenkorb() {
		
		return wnk.gibAlleArtikel();
	}
	
	public static List<Artikel> DurchsucheBestandNachName(String name) {
		return bst.sucheNachName(name);
	}
	
	public static List<Rechnung> DurchsucheRechnungslisteNachNr(int nummer) {
		return rch.sucheNachNr(nummer);
	}
	
	public static void loescheArtikel(Artikel artikel) {
		bst.loescheArtikel(artikel);
	}
	
	public static int getUserAnzahl() {
		return usr.gibUserAnzahl();
	}
	
	public static double getGesamtpreisWarenkorb() {
		return wnk.gibGesamtpreis();
	}
	
	public static double getGesamtpreisBestand() {
		return bst.gibGesamtpreis();
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

	public static boolean getRun() {
		return run;
	}
	
	public static void setRun(boolean a) {
		run = a;
	}

}
