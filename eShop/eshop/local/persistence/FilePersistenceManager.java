package eshop.local.persistence;

import java.util.*;

import eshop.local.valueobjects.Artikel;

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
		String verfuegbarkeitString = liesZeile();
		// String in int umwandeln
		int verfuegbarkeit = Integer.parseInt(verfuegbarkeitString);
		
		String preisString = liesZeile();
		double preis = Double.parseDouble(preisString);
		
		String beschreibung = liesZeile();
		
		
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Artikel(name, nummer, verfuegbarkeit, preis, beschreibung);
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
		String verfuegbarkeitString = String.valueOf(a.getVerfuegbarkeit());
		schreibeZeile(verfuegbarkeitString);
		
		//hier das gleiche nochmal, nur ist der int-Wert diesmal ein double-Wert. Ich hoffe valueOf() kann das.
		String preisString = String.valueOf(a.getPreis());
		schreibeZeile(preisString);
		
		//getBeschreibung() returned einen String also ist keine umwandlung nötig, um ihn in die Zeile zu schreiben.
		schreibeZeile(a.getBeschreibung());
		

		return true;
	}

	/*
	 *  Wenn später mal eine Kundenverwaltung ergänzt wird:

	public Kunde ladeKunde() throws IOException {
		// TODO: Implementieren
		return null;
	}

	public boolean speichereKunde(Kunde k) throws IOException {
		// TODO: Implementieren
		return false;
	}

	*/
	
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