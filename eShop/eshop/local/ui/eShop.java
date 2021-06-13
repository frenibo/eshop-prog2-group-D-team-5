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
import eshop.local.domain.SitzungVektorListe;
import eshop.local.domain.UserVektorListe;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.server.eShopInterface;
import eshop.local.ui.cui.Eingabeverarbeitung;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Inputevent;
import eshop.local.valueobjects.Lagerungsevent;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.Sitzung;
import eshop.local.valueobjects.User;

public class eShop implements eShopInterface {
	
	//TODO: save & quit, Ereigniselement, remove saves from running program, 
		// Sitzung in eShop umbenennen, eShopArtikelFunktionen + eShopUserFunktionen usw.
		//Sitzungsnummer zu valueobject machen, dass nicht mehr pro eShop bzw Sitzungs-Objekt erstellt wird, sondern pro Warenkorb
		//Warum muss jede Methode in Sitzung/eShop static sein?
		//Sitzung()
		//Lagerungsevent hat Bugs
		//serializable
		//threads
		//serializable
	
	public static ArtikelVektorListe bst;
	public static UserVektorListe usr;
	public static RechnungVektorListe rch;
	public static InputeventVektorListe inp;
	public static LagerungseventVektorListe lag;
	public static Eingabeverarbeitung ev;
	public static SitzungVektorListe stz;
		
	public static String aktuelleSitzungsNr;
		
	private static String datei = "";
	
	private static boolean run = true;
	
public eShop() throws IOException, ArtikelExistiertBereitsException {
		
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe("BST_B.txt");

		usr = new UserVektorListe("USR_B.txt");
	
		rch = new RechnungVektorListe("RCH_B.txt");
	
		inp = new InputeventVektorListe("INP_B.txt");
		
		lag = new LagerungseventVektorListe("LAG_B.txt");
		
		stz = new SitzungVektorListe("STZ_B.txt");
		
		neueSitzung();
			
		// 
		ev = new Eingabeverarbeitung();
	
		eShop.datei = "BST_B.txt";
	}
	
	public eShop(String dateiArtikel, String dateiUser, String dateiRechnungen, String dateiInputevents, String dateiLagerungsevents, String dateiSitzungen) throws IOException, ArtikelExistiertBereitsException {
		
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new ArtikelVektorListe(dateiArtikel);

		usr = new UserVektorListe(dateiUser);
	
		rch = new RechnungVektorListe(dateiRechnungen);
	
		inp = new InputeventVektorListe(dateiInputevents);
		
		lag = new LagerungseventVektorListe(dateiLagerungsevents);
		
		stz = new SitzungVektorListe(dateiSitzungen);
		
		neueSitzung();
			
		// 
		ev = new Eingabeverarbeitung();
	
		eShop.datei = dateiArtikel;
	}
	
	public static void main(String[] args) throws ArtikelExistiertBereitsException {
		eShop esp;
		try {
			esp = new eShop("BST_B.txt", "USR_B.txt", "RCH_B.txt", "INP_B.txt", "LAG_B.txt", "STZ_B.txt");
			eShop.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	public static void run() throws IOException, ArtikelExistiertBereitsException {
	
		String input = "";

		ev.setLevel("startmenue");

		do {
			gibMenueAus();
			try {
		
				ev.einlesenUndVerarbeiten();
				input = getInput();
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (run == true);
	}
	
	public String echo(String s) throws Exception {
		// wenn jetzt ein anderer thread dran kommt, dann schreibt der den Text eines anderen Client!
		return (" "+s+" "+s);
	}
	
	
	public static String getInput() {
		return ev.getInput();
	}
	
	public static void gibMenueAus() {
		ev.gibMenueAus();
	}
	
	public static void einlesenUndVerarbeiten() throws IOException, ArtikelExistiertBereitsException {
		ev.einlesenUndVerarbeiten();
	}
	
	public static void einlesenUndVerarbeiten(String input) throws IOException, ArtikelExistiertBereitsException {
		ev.einlesenUndVerarbeiten(input);
	}
	
	public static Sitzung neueSitzung() throws IOException, ArtikelExistiertBereitsException {
		Sitzung sitzung = new Sitzung(neueSitzungsNr());
		stz.fuegeSitzungEin(sitzung);
		aktuelleSitzungsNr = sitzung.getAktuelleSitzungsNr();
		return sitzung;
	}
	
	public static Sitzung neueSitzung(User user) throws IOException, ArtikelExistiertBereitsException {
		Sitzung sitzung = new Sitzung(neueSitzungsNr(), user);
		stz.fuegeSitzungEin(sitzung);
		aktuelleSitzungsNr = sitzung.getAktuelleSitzungsNr();
		return sitzung;
	}
	
	public static String neueSitzungsNr() {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		return sitzungsNr;
	}
	
	public static Sitzung getAktuelleSitzung(String sitzungsNr) throws IOException {
		for(Sitzung s : stz.getSitzungListe()) {
			if(s.getAktuelleSitzungsNr() == sitzungsNr) {
				return s;
			}
		}
		return null;
	}
	
	public static Lagerungsevent neuesLagerungsevent(String lagerung, Artikel artikel, int anzahl, String sitzungsnummer, User user) throws IOException {
		Lagerungsevent event = new Lagerungsevent(lagerung, artikel, anzahl, sitzungsnummer, user);
		try {
			lag.fuegeLagerungseventEin(event);
		} catch (Exception e) {}
		try {
			getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerLagerungsevents().fuegeLagerungseventEin(event);
		} catch (Exception e) {}
		getAktuelleSitzung(aktuelleSitzungsNr).setAktuellesLagerungsevent(event);
		return event;
	}
	
	public static List<Lagerungsevent> getNeueLagerungen() throws IOException{
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerLagerungsevents().getLagerungseventListe();
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
	
	public static Rechnung warenkorbKaufen() throws IOException, ArtikelExistiertBereitsException {
		
		List<Artikel> liste = getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb().gibAlleArtikel();
		Rechnung rechnung = new Rechnung(getAktuellerUser(), liste);
		getAktuelleSitzung(aktuelleSitzungsNr).setAktuelleRechnung(rechnung);
		try {
			
			rch.fuegeRechnungEin(rechnung);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		User user = getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerUser();
		neueSitzung(user);
		speichern();
		return rechnung;
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
	
	public static void verschiebeVonBestandInWarenkorb(int nummer, int anzahl) throws IOException {
		bst.verschiebeInWarenkorb(nummer, anzahl, getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb());
	}
	
	public static void verschiebeVonWarenkorbinBestand(int nummer, int anzahl) throws IOException {
		getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb().verschiebeInBestand(nummer, anzahl, bst);
	}
	
	public static void warenkorbLeeren() throws IOException {
		getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb().warenkorbLeeren();
	}
	
	public static void aendereAnzahlImBestand(int nummer, int anzahl) throws IOException {
		bst.aendereAnzahl(nummer, anzahl);
	}
	
	public static void loescheArtikelImBestand(Artikel a) throws IOException {
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
	
	public static String fuegeArtikelEin(String name, int nummer, int anzahl, double preis, int packet) throws ArtikelExistiertBereitsException, IOException {
		Artikel artikel = new Massenartikel(name, nummer, anzahl, preis, packet);
		neuesLagerungsevent("Einlagerung", artikel, anzahl, getSitzungsNr(), getAktuellerUser());
		return bst.fuegeArtikelEin(name, nummer, anzahl, preis, packet);		
	}
	
	public static String fuegeArtikelEin(Artikel artikel) throws ArtikelExistiertBereitsException, IOException {
		neuesLagerungsevent("Einlagerung", artikel, artikel.getAnzahl(), getSitzungsNr(), getAktuellerUser());
		return bst.fuegeArtikelEin(artikel);		
	}
		
	public static String erstelleArtikelImBestand(String name, int nummer, int anzahl, double preis, int packet) throws ArtikelExistiertBereitsException, IOException {
		Artikel einArtikel = new Massenartikel(name, nummer, anzahl, preis, packet);
		List<Artikel> suchErgNummer = new Vector<Artikel>();
		suchErgNummer = DurchsucheBestandNachNummer(einArtikel.getNummer());
		List<Artikel> suchErgName = new Vector<Artikel>();
		suchErgName = DurchsucheBestandNachName(einArtikel.getName());
		
		if(einArtikel.getNummer() <= 0) {
			return "Falsche Nummer";
		}		
		else if (!suchErgNummer.isEmpty() && suchErgName.isEmpty()) {
			setAktuellerArtikel(suchErgNummer.get(0));
			return "Nummer vergeben";
		}
		else if(!suchErgName.isEmpty()) {			
			setAktuellerArtikel(suchErgName.get(0));
			return "Artikel existiert bereits";
		}
		else {
			// das übernimmt der Vector:
			fuegeArtikelEin(einArtikel);
			setAktuellerArtikel(einArtikel);
			return "Erfolgreich hinzugefügt";
		}	
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
		getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb().schreibeArtikel();
		rch.schreibeRechnung();
		usr.schreibeUser();
		inp.schreibeInputevents();
		lag.schreibeLagerungsevents();
		stz.schreibeSitzungen();
	}
	
	public static List<Artikel> getArtikellisteAusWarenkorb() throws IOException {
		
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb().gibAlleArtikel();
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
	
	public static double getGesamtpreisWarenkorb() throws IOException {
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb().gibGesamtpreis();
	}
	
	public static double getGesamtpreisBestand() {
		return bst.gibGesamtpreis();
	}
	
	public static String getSitzungsNr() throws IOException {
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuelleSitzungsNr();
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
	
	public static ArtikelVektorListe getWarenkorb() throws IOException {
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerWarenkorb();
	}

	public static void setAktuellerArtikel(Artikel a) throws IOException {
		getAktuelleSitzung(aktuelleSitzungsNr).setAktuellerArtikel(a);
	}
	
	public static Artikel getAktuellerArtikel() throws IOException {
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerArtikel();
	}
	
	public static void setAktuellerUser(User u) throws IOException {
		getAktuelleSitzung(aktuelleSitzungsNr).setAktuellerUser(u);
	}
	
	public static User getAktuellerUser() throws IOException {
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuellerUser();
	}
	
	public static void setAktuelleRechnung(Rechnung r) throws IOException {
		getAktuelleSitzung(aktuelleSitzungsNr).setAktuelleRechnung(r);
	}
	
	public static Rechnung getAktuelleRechnung() throws IOException {
		return getAktuelleSitzung(aktuelleSitzungsNr).getAktuelleRechnung();
	}

	public static boolean getRun() {
		return run;
	}
	
	public static void setRun(boolean a) {
		run = a;
	}

}
