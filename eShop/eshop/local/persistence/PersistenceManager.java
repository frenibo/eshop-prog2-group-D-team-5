package eshop.local.persistence;

import java.io.IOException;

import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;

/**
 * @author teschke
 *
 * Allgemeine Schnittstelle für den Zugriff auf ein Speichermedium
 * (z.B. Datei oder Datenbank) zum Ablegen von beispielsweise
 * Bücher- oder Kundendaten.
 * 
 * Das Interface muss von Klassen implementiert werden, die eine
 * Persistenz-Schnittstelle realisieren wollen.
 */
public interface PersistenceManager {

	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();

	/**
	 * Methode zum Einlesen der Buchdaten aus einer externen Datenquelle.
	 * 
	 * @return Buch-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Artikel ladeArtikel() throws IOException;

	/**
	 * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
	 * 
	 * @param b Buch-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Object a) throws IOException;


	public User ladeUser() throws IOException;

	public boolean speichereUser(User u) throws IOException;
	
	public Rechnung ladeRechnung() throws IOException;

	public boolean speichereRechnung(Rechnung r) throws IOException;
	
}