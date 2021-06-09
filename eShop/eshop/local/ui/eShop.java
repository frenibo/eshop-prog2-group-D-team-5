package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.InputeventVektorListe;
import eshop.local.domain.LagerungseventVektorListe;
import eshop.local.domain.RechnungVektorListe;
import eshop.local.domain.UserVektorListe;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.cui.Main;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Inputevent;
import eshop.local.valueobjects.Lagerungsevent;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.Sitzung;
import eshop.local.valueobjects.User;

public class eShop {
	
	public static ArtikelVektorListe bst;
	public static ArtikelVektorListe wnk;
	public static UserVektorListe usr;
	public static RechnungVektorListe rch;
	public static InputeventVektorListe inp;
	public static LagerungseventVektorListe lag;
	public static LagerungseventVektorListe lagSitzung;
	public static Eingabeverarbeitung ev;
	
	private static Artikel aktuellerArtikel;
	private static String aktuelleSitzungsNr;
	private static User aktuellerUser;
	private static Inputevent aktuellesInputevent;
	private static Lagerungsevent aktuellesLagerungsevent;
	private static Rechnung aktuelleRechnung;
	
	private static String datei = "";
	
	private static boolean run = true;
	
	public eShop() throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe();
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		// 
		ev = new Eingabeverarbeitung();
		
		
	}
	
	public eShop(String dateiArtikel) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
		
		aktuellerUser = new User();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
		
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
	
		// 
		ev = new Eingabeverarbeitung();
		
		eShop.datei = dateiArtikel;
	}
	
	public eShop(String dateiArtikel, String dateiUser) throws IOException {
		
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
		
		eShop.datei = dateiArtikel;
	}
	
	public eShop(String dateiArtikel, String dateiUser, String dateiRechnungen) throws IOException {
		
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
		
		eShop.datei = dateiArtikel;
	}

	public eShop(String dateiArtikel, String dateiUser, String dateiRechnungen, String dateiInputevents) throws IOException {
	
		aktuelleSitzungsNr = neueSitzungsNr();
	
		aktuellerUser = new User();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
	
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");

		usr = new UserVektorListe(dateiUser);
	
		rch = new RechnungVektorListe(dateiRechnungen);
	
		inp = new InputeventVektorListe(dateiInputevents);
	
		// 
		ev = new Eingabeverarbeitung();
	
		eShop.datei = dateiArtikel;
	}
	
	public eShop(String dateiArtikel, String dateiUser, String dateiRechnungen, String dateiInputevents, String dateiLagerungsevents, String dateiLagerungseventsTemp) throws IOException {
		
		aktuelleSitzungsNr = neueSitzungsNr();
	
		aktuellerUser = new User();
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);
	
		// neue Warenkorb-Liste wird angelegt.
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");

		usr = new UserVektorListe(dateiUser);
	
		rch = new RechnungVektorListe(dateiRechnungen);
	
		inp = new InputeventVektorListe(dateiInputevents);
		
		lag = new LagerungseventVektorListe(dateiLagerungsevents);
		
		lagSitzung = new LagerungseventVektorListe(dateiLagerungseventsTemp);
	
		// 
		ev = new Eingabeverarbeitung();
	
		eShop.datei = dateiArtikel;
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
	
	public static String neueSitzungsNr() {
		
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		aktuelleSitzungsNr = sitzungsNr;
		return sitzungsNr;
		
		/*
		aktuelleeShopsNr = new eShopsnummer().toString();
		return aktuelleeShopsNr;
		*/
	}
	
	public static Lagerungsevent neuesLagerungsevent(String lagerung, Artikel artikel, int anzahl, String sitzungsnummer, User user) {
		Lagerungsevent event = new Lagerungsevent(lagerung, artikel, anzahl, sitzungsnummer, user);
		try {
			lag.fuegeLagerungseventEin(event);
		} catch (Exception e) {}
		try {
			lagSitzung.fuegeLagerungseventEin(event);
		} catch (Exception e) {}
		aktuellesLagerungsevent = event;
		return event;
	}
	
	public static List<Lagerungsevent> getNeueLagerungen(){
		return lagSitzung.getLagerungseventListe();
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
	
	public static boolean warenkorbKaufen() throws IOException {
		
		boolean erfolg = false;
		List<Artikel> liste = wnk.gibAlleArtikel();
		Rechnung rechnung = new Rechnung(getAktuellerUser(), liste);
		aktuelleRechnung = rechnung;
		try {
			
			rch.fuegeRechnungEin(rechnung);
			erfolg = true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return erfolg;
		}
		aktuelleSitzungsNr = neueSitzungsNr();
		wnk = new ArtikelVektorListe(getSitzungsNr()+".txt");
		speichern();
		return erfolg;
	}
	
	public static List<Artikel> produceAenderungsListe() throws IOException{
		boolean neuerArtikel = true;
		boolean gelöschterArtikel = true;
		List<Artikel> liste = eShop.bst.gibAlleArtikel();
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
		bst.verschiebeInWarenkorb(nummer, anzahl, eShop.wnk);
	}
	
	public static void verschiebeVonWarenkorbinBestand(int nummer, int anzahl) {
		wnk.verschiebeInBestand(nummer, anzahl, eShop.bst);
	}
	
	public static void warenkorbLeeren() {
		wnk.warenkorbLeeren();
	}
	
	public static void aendereAnzahlImBestand(int nummer, int anzahl) {
		bst.aendereAnzahl(nummer, anzahl);
	}
	
	public static void loescheArtikelImBestand(Artikel a) {
		neuesLagerungsevent("Auslagerung", a, a.getAnzahl(), getSitzungsNr(), getAktuellerUser());
		bst.loescheArtikel(a);
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
		Artikel artikel = new Massenartikel(name, nummer, anzahl, preis, packet);
		neuesLagerungsevent("Einlagerung", artikel, anzahl, getSitzungsNr(), getAktuellerUser());
		return bst.fuegeArtikelEin(name, nummer, anzahl, preis, packet);		
	}
	
	public static void fuegeInputeventEin(Inputevent event) throws ArtikelExistiertBereitsException {
		inp.fuegeInputeventEin(event);
		
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
		inp.schreibeInputevents();		
	}
	
	public static void speichern() throws IOException {
		
		warenkorbLeeren();
		
		bst.schreibeArtikel();
		wnk.schreibeArtikel();
		rch.schreibeRechnung();
		usr.schreibeUser();
		inp.schreibeInputevents();
		lag.schreibeLagerungsevents();
	}
	
	public static List<Artikel> getArtikellisteAusWarenkorb() {
		
		return wnk.gibAlleArtikel();
	}
	
	public static List<Artikel> DurchsucheBestandNachName(String name) {
		return bst.sucheNachName(name);
	}
	
	public static List<Artikel> DurchsucheBestandNachNummer(int nummer) {
		return bst.sucheNachNr(nummer);
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
	
	public static List<Rechnung> getRechnungsliste() {
		return rch.gibAlleRechnungen();
	}
	
	public static List<User> getUserliste() {
		return usr.gibAlleUser();
	}
	
	public static List<Lagerungsevent> getLagerungseventListe() {
		return lag.getLagerungseventListe();
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
	
	public static void setAktuelleRechnung(Rechnung r) {
		aktuelleRechnung = r;
	}
	
	public static Rechnung getAktuelleRechnung() {
		return aktuelleRechnung;
	}

	public static boolean getRun() {
		return run;
	}
	
	public static void setRun(boolean a) {
		run = a;
	}

}
