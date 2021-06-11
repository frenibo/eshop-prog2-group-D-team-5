package eshop.local.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Inputevent;
import eshop.local.valueobjects.Lagerungsevent;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.Sitzung;
import eshop.local.valueobjects.User;

public interface eShopInterface {
	
	public static void main(String[] args) {}
	
	public static void run() {}
	
	public static Sitzung neueSitzung() {
		return null;
	}
	
	public static String neueSitzungsNr() {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sitzungsNr = formatter.format(objDate);
		return sitzungsNr;
	}
	
	public static Sitzung getAktuelleSitzung(String sitzungsNr) {
		return null;
	}
	
	public static Lagerungsevent neuesLagerungsevent(String lagerung, Artikel artikel, int anzahl, String sitzungsnummer, User user) {
		return null;
	}
	
	public static List<Lagerungsevent> getNeueLagerungen() {
		return null;
	}
	
	public static void gibArtikellisteUnsortiertAus(List<Artikel> vectorListe) {}

	public static void gibArtikellisteNummerischAus(List<Artikel> vectorListe) {}
	
	public static void gibUserlisteAus(List<User> liste) {}
	
	public static Rechnung warenkorbKaufen() {
		return null;
	}
	
	public static List<Artikel> produceAenderungsListe() {
		return null;
	}
	
	public static void verschiebeVonBestandInWarenkorb(int nummer, int anzahl) {}
	
	public static void verschiebeVonWarenkorbinBestand(int nummer, int anzahl) {}
	
	public static void warenkorbLeeren() {}
	
	public static void aendereAnzahlImBestand(int nummer, int anzahl) {}
	
	public static void loescheArtikelImBestand(Artikel a) {}
	
	public static List<Artikel> getArtikellisteAusBestand() {
		return null;
	}
	
	public static void fuegeRechnungEin(Rechnung rechnung) {}
	
	public static void fuegeUserEin(int nummer, String name) {}
	
	public static String fuegeArtikelEin(String name, int nummer, int anzahl, double preis, int packet) {
		return null;
	}
	
	public static void fuegeInputeventEin(Inputevent event) {}
	
	public static void speichereBestand() {}
	
	public static void speichereRechnungsliste() {}
	
	public static void speichereUserliste() {}
	
	public static void speichereEventliste() {}
	
	public static void speichern() {}
	
	public static List<Artikel> getArtikellisteAusWarenkorb() {
		return null;
	}
	
	public static List<Artikel> DurchsucheBestandNachName(String name) {
		return null;
	}
	
	public static List<Artikel> DurchsucheBestandNachNummer(int nummer) {
		return null;
	}
	
	public static List<Rechnung> DurchsucheRechnungslisteNachNr(int nummer) {
		return null;
	}
	
	public static void loescheArtikel(Artikel artikel) {}
	
	public static int getUserAnzahl() {
		return 0;
	}
	
	public static double getGesamtpreisWarenkorb() {
		return  0.0;
	}
	
	public static double getGesamtpreisBestand() {
		return 0.0;
	}
	
	public static String getSitzungsNr()  {
		return null;
	}
	
	public static List<Rechnung> getRechnungsliste() {
		return null;
	}
	
	public static List<User> getUserliste() {
		return null;
	}
	
	public static List<Lagerungsevent> getLagerungseventListe() {
		return null;
	}
	
	public static ArtikelVektorListe getWarenkorb() {
		return null;
	}

	public static void setAktuellerArtikel(Artikel a) {}
	
	public static Artikel getAktuellerArtikel() {
		return null;
	}
	
	public static void setAktuellerUser(User u) {}
	
	public static User getAktuellerUser() {
		return null;
	}
	
	public static void setAktuelleRechnung(Rechnung r) {}
	
	public static Rechnung getAktuelleRechnung() {
		return null;
	}

	public static boolean getRun() {
		return false;
	}
	
	public static void setRun(boolean a) {}
	
}
