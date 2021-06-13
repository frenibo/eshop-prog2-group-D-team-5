package eshop.local.server;

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
	
	public static void main(String[] args) throws ArtikelExistiertBereitsException {}
	
	public static void run() throws IOException, ArtikelExistiertBereitsException {}
	
	public String echo(String s) throws Exception;
	
	public static String getInput() {
		return null;
	}
	
	public static void gibMenueAus() {}
	
	public static void einlesenUndVerarbeiten() throws IOException, ArtikelExistiertBereitsException {}
	
	public static void einlesenUndVerarbeiten(String input) throws IOException, ArtikelExistiertBereitsException {}
	
	public static Sitzung neueSitzung() throws IOException, ArtikelExistiertBereitsException {
		return null;
	}
	
	public static String neueSitzungsNr() throws IOException, ArtikelExistiertBereitsException {
		return null;
	}
	
	public static Sitzung getAktuelleSitzung(String sitzungsNr) throws IOException {
		return null;
	}
	
	public static Lagerungsevent neuesLagerungsevent(String lagerung, Artikel artikel, int anzahl, String sitzungsnummer, User user) throws IOException {
		return null;
	}
	
	public static List<Lagerungsevent> getNeueLagerungen() throws IOException {
		return null;
	}
	
	public static void gibArtikellisteUnsortiertAus(List<Artikel> vectorListe) {}

	public static void gibArtikellisteNummerischAus(List<Artikel> vectorListe) {}
	
	public static void gibUserlisteAus(List<User> liste) {}
	
	public static Rechnung warenkorbKaufen() throws IOException, ArtikelExistiertBereitsException {
		return null;
	}
	
	public static List<Artikel> produceAenderungsListe() throws IOException {
		return null;
	}
	
	public static void verschiebeVonBestandInWarenkorb(int nummer, int anzahl) throws IOException {}
	
	public static void verschiebeVonWarenkorbinBestand(int nummer, int anzahl) throws IOException {}
	
	public static void warenkorbLeeren() {}
	
	public static void aendereAnzahlImBestand(int nummer, int anzahl) throws IOException {}
	
	public static void loescheArtikelImBestand(Artikel a) throws IOException {}
	
	public static List<Artikel> getArtikellisteAusBestand() {
		return null;
	}
	
	public static void fuegeRechnungEin(Rechnung rechnung) throws ArtikelExistiertBereitsException {}
	
	public static void fuegeUserEin(int nummer, String name) throws ArtikelExistiertBereitsException {}
	
	public static String fuegeArtikelEin(String name, int nummer, int anzahl, double preis, int packet) throws ArtikelExistiertBereitsException, IOException {
		return null;
	}
	
	public static String fuegeArtikelEin(Artikel artikel) throws ArtikelExistiertBereitsException, IOException {
		return null;
	}
	
	public static String erstelleArtikelImBestand(String name, int nummer, int anzahl, double preis, int packet) throws ArtikelExistiertBereitsException, IOException {
		return null;
	}
	
	public static void fuegeInputeventEin(Inputevent event) throws ArtikelExistiertBereitsException, IOException {}
	
	public static void speichereBestand() throws IOException {}
	
	public static void speichereRechnungsliste() throws IOException {}
	
	public static void speichereUserliste() throws IOException {}
	
	public static void speichereEventliste() throws IOException {}
	
	public static void speichern() throws IOException {}
	
	public static List<Artikel> getArtikellisteAusWarenkorb() throws IOException {
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
	
	public static double getGesamtpreisWarenkorb() throws IOException {
		return  0.0;
	}
	
	public static double getGesamtpreisBestand() {
		return 0.0;
	}
	
	public static String getSitzungsNr() throws IOException {
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
	
	public static ArtikelVektorListe getWarenkorb() throws IOException {
		return null;
	}

	public static void setAktuellerArtikel(Artikel a) throws IOException {}
	
	public static Artikel getAktuellerArtikel() throws IOException {
		return null;
	}
	
	public static void setAktuellerUser(User u) throws IOException {}
	
	public static User getAktuellerUser() throws IOException {
		return null;
	}
	
	public static void setAktuelleRechnung(Rechnung r) throws IOException {}
	
	public static Rechnung getAktuelleRechnung() throws IOException {
		return null;
	}

	public static boolean getRun() {
		return false;
	}
	
	public static void setRun(boolean a) {}
	
}
