package eshop.local.persistence;

import java.util.*;

import eshop.local.valueobjects.Artikel;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author eirund
 *
 * Realisierung einer "Mockup"-Schnittstelle zur persistenten Speicherung von
 * Daten in Dateien.
 * Bedeutet: einigen wenige B�cher werden "generiert" und zur�ckgegeben. 
 * Alle Methoden des Interface PersistenceManager m�ssen implementiert werden.
 * 
 * ACHTUNG: diese Klasse wird sp�ter durch eine "richtige" Klasse ersetzt. 
 */
public class FilePersistenceManager implements PersistenceManager {

	
	
	public void openForReading(String datei) throws FileNotFoundException {
	
	}

	public void openForWriting(String datei) throws IOException {
		
	}

	public boolean close() {
		return true;
	}

	
	public Artikel ladeArtikel() throws IOException {
		// W�rfeln: bei 6 liefere "null", sonst ein Fake-Buch...
		Random r = new Random();
		int wurf = (r.nextInt() % 6) +1; // 1-6
		if (wurf == 6 ) 
			return null;
		else {
			// Titel erzeugen
			String titel = "Es "+(wurf<=0?"waren":"werden")+ " einmal "+wurf+" Kind"+(wurf==1?"":"er");
		
			int nummer = r.nextInt() % 100;
			nummer = (nummer<0)?nummer*(-1):nummer; 
			int verfuegbarkeit = 6;
			double preis = 3.50;
			String beschreibung = "Dieses Buch ist toll!";
		
			// neues Buch-Objekt anlegen und zurückgeben
			return new Artikel(titel, nummer, verfuegbarkeit, preis, beschreibung);
		}
	}

	
	public boolean speichereArtikel(Artikel a) throws IOException {
		return true;
	}


}