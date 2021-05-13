package eshop.local.persistence;

import java.util.*;

import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;

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
		
		
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Artikel(name, nummer, anzahl, preis);
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
					
		String buchungOderKaufString = liesZeile();
		
		boolean buchungOderKauf = Boolean.parseBoolean(buchungOderKaufString);		
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Rechnung(user, artikelListe, buchungOderKauf, sitzungsNr, datum, gesamtpreis);
	}

	/**
	 * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
	 * Das Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @param b Buch-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Artikel a) throws IOException {
		// Titel, Nummer, Verfügbarkeit , Preis, Beschreibung schreiben
		schreibeZeile(a.getName());
//		schreibeZeile(Integer.valueOf(b.getNummer()).toString());
		schreibeZeile(a.getNummer() + "");

		//der int-Wert, der von getVerfuegbarkeit() ausgegeben wird, 
		//wird in einen String konvertiert und in der Hilfsvariable verfuegbarkeitString zwischengespeichert.
		//dann wird der String über schreibeZeile() in die Zeile geschrieben.
		String anzahlString = String.valueOf(a.getAnzahl());
		schreibeZeile(anzahlString);
		
		//hier das gleiche nochmal, nur ist der int-Wert diesmal ein double-Wert. Ich hoffe valueOf() kann das.
		String preisString = String.valueOf(a.getPreis());
		schreibeZeile(preisString);		

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
		
		String kauf = String.valueOf(r.getKauf() + "");
		schreibeZeile(kauf);
		
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
}