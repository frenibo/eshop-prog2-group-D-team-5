package eshop.local.persistence;

import java.util.*;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.LagerungseventVektorListe;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Inputevent;
import eshop.local.valueobjects.Lagerungsevent;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;
import eshop.local.valueobjects.Sitzung;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author teschke
 *
 * Realisierung einer Schnittstelle zur persistenten Speicherung von
 * Daten in Dateien.
 * @see bib.local.persistence.PersistenceManager
 */
public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

	public boolean close() {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}
		}

		return true;
	}

	/**
	 * Methode zum Einlesen der Buchdaten aus einer externen Datenquelle.
	 * Das Verfügbarkeitsattribut ist in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @return Buch-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Artikel ladeArtikel() throws IOException {
		// Titel einlesen
		String name = liesZeile();
		if (name == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		// Nummer einlesen ...
		String nummerString = liesZeile();
		// ... und von String in int konvertieren
		int nummer = Integer.parseInt(nummerString);
		
		// Viele viele Artikel sind im Bestand vorhanden?
		String anzahlString = liesZeile();
		// String in int umwandeln
		int anzahl = Integer.parseInt(anzahlString);
		
		String preisString = liesZeile();
		double preis = Double.parseDouble(preisString);
		
		zeileMarkieren();
		
		String checkMassenartikel = liesZeile();
		int packet = 1;
		if(checkMassenartikel.equals("Packetgroeße:")) {
			String packetString = liesZeile();
			packet = Integer.parseInt(packetString);
		} else zurMarkierungZurueck();
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Massenartikel(name, nummer, anzahl, preis, packet);
	}
	
	public User ladeUser() throws IOException {
		
		String userNrString = liesZeile();
		if (userNrString == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		// String in int umwandeln
		int userNr = Integer.parseInt(userNrString);
		
		String name = liesZeile();
					
		String adresse = liesZeile();
			
		String passwort = liesZeile();
		
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new User(userNr, name, adresse, passwort);
	}
	
	public Rechnung ladeRechnung() throws IOException {
		
		String artikelAnzahlString = liesZeile();
		if (artikelAnzahlString == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		int artikelAnzahl = Integer.parseInt(artikelAnzahlString);
		
		List<Artikel> artikelListe = new Vector<Artikel>();
		
		for(int i = 0; i < artikelAnzahl; i++) {
			Artikel a = ladeArtikel();
			artikelListe.add(a);
		}
		
		String gesamtpreisString = liesZeile();
		double gesamtpreis = Double.parseDouble(gesamtpreisString);
		
		User user = ladeUser();
		
		String datum = liesZeile();
		
		String sitzungsNr = liesZeile();	
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Rechnung(user, artikelListe, sitzungsNr, datum, gesamtpreis);
	}
	
	public Inputevent ladeInputevents() throws IOException {
		
		String input = liesZeile();
		if (input == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		
		String sitzungsNr = liesZeile();
		
		String zeitstempel = liesZeile();
		
		User user = ladeUser();
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Inputevent(input, user, sitzungsNr, zeitstempel);
	}
	
	public Lagerungsevent ladeLagerungsevents() throws IOException {
		
		String lagerung = liesZeile();
		if (lagerung == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		
		Artikel artikel = ladeArtikel();
		
		String anzahlString = liesZeile();
		int anzahl = Integer.parseInt(anzahlString);
		
		String sitzungsNr = liesZeile();
		
		User user = ladeUser();
		
		String datum = liesZeile();
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Lagerungsevent(lagerung, artikel, anzahl, sitzungsNr, user, datum);
	}
	
	public Sitzung ladeSitzungen() throws IOException {
		// TODO Auto-generated method stub
		String sitzungsNr = liesZeile();
		if (sitzungsNr == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		
		User user = ladeUser();
		
		return new Sitzung(sitzungsNr, user);
		
	}

	/**
	 * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
	 * Das Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @param b Buch-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Object a) throws IOException {
		
		if(a instanceof Artikel) {
		
			// Titel, Nummer, Verfügbarkeit , Preis, Beschreibung schreiben
			schreibeZeile(((Artikel) a).getName());
//			schreibeZeile(Integer.valueOf(b.getNummer()).toString());
			schreibeZeile(((Artikel) a).getNummer() + "");
		
			//der int-Wert, der von getVerfuegbarkeit() ausgegeben wird, 
			//wird in einen String konvertiert und in der Hilfsvariable verfuegbarkeitString zwischengespeichert.
			//dann wird der String über schreibeZeile() in die Zeile geschrieben.
			String anzahlString = String.valueOf(((Artikel) a).getAnzahl());
			schreibeZeile(anzahlString);
		
			//hier das gleiche nochmal, nur ist der int-Wert diesmal ein double-Wert. Ich hoffe valueOf() kann das.
			String preisString = String.valueOf(((Artikel) a).getPreis());
			schreibeZeile(preisString);		
		}
		
		if(a instanceof Massenartikel) {
			schreibeZeile("Packetgroeße:");
			String packetString = String.valueOf(((Massenartikel) a).getPacketgroeße());
			schreibeZeile(packetString);
		}

		return true;
	}

	public boolean speichereUser(User u) throws IOException {
			
		String userNrString = String.valueOf(u.getUserNr() + "");
		schreibeZeile(userNrString);

		schreibeZeile(u.getName());

		schreibeZeile(u.getAdresse());
			
		schreibeZeile(u.getPasswort());

		return true;
	}
	
	public boolean speichereRechnung(Rechnung r) throws IOException {
		
		String artikelAnzahl = String.valueOf(r.getArtikelAnzahl() + "");
		schreibeZeile(artikelAnzahl);
		
		for(Artikel a : r.getArtikelListe()) {
			speichereArtikel(a);
		}
		
		String gesamtPreis = String.valueOf(r.getGesamtpreis());
		schreibeZeile(gesamtPreis);
		
		speichereUser(r.getUser());
		
		schreibeZeile(r.getDatum());
		
		schreibeZeile(r.getSitzungsNr());
		
		return true;
	}
	
	public boolean speichereInputevents(Inputevent e) throws IOException {
		
		String eventInput = String.valueOf(e.getInput() + "");
		schreibeZeile(eventInput);
		
		String eventSitzungsNr = String.valueOf(e.getSitzungsNr() + "");
		schreibeZeile(eventSitzungsNr);
		
		String eventZeitstempel = String.valueOf(e.getZeitstempel() + "");
		schreibeZeile(eventZeitstempel);
		
		speichereUser(e.getUser());

		return true;
		
	}
	
	public boolean speichereLagerungsevents(Lagerungsevent e) throws IOException {
		
		schreibeZeile(e.getLagerung());
		
		speichereArtikel(e.getArtikel());
		
		String eventAnzahl = String.valueOf(e.getAnzahl() + "");
		schreibeZeile(eventAnzahl);
		
		schreibeZeile(e.getSitzungsnummer());
		
		speichereUser(e.getUser());
		
		schreibeZeile(e.getDatum());

		return true;
	}
	
	public boolean speichereSitzungen(Sitzung s) throws IOException {
		// TODO Auto-generated method stub
		schreibeZeile(s.getAktuelleSitzungsNr());
				
		speichereUser(s.getAktuellerUser());
				
		return true;
	}

	
	/*
	 * Private Hilfsmethoden
	 */
	
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
	
	private void zeileMarkieren() throws IOException {
		if (reader != null)
			reader.mark(500);
	}
	
	private void zurMarkierungZurueck() throws IOException {
		if (reader != null)
			reader.reset();
	}

}