package eshop.local.domain;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.persistence.FilePersistenceManager;
import eshop.local.persistence.PersistenceManager;
import eshop.local.valueobjects.Artikel;


/**
 * Klasse zur Verwaltung von Büchern.
 * 
 * @author teschke
 * @version 3
 * - Verwaltung der Bücher in List/Vector mit Generics
 * - außerdem Einsatz von Interfaces (List): Vector ist nur eine mögliche Implementierung!
 */
public class ArtikelVerwaltung {

	// Verwaltung des Buchbestands in einem Vector
	private List<Artikel> artikelBestand = new Vector<Artikel>();

	// Persistenz-Schnittstelle, die für die Details des Dateizugriffs verantwortlich ist
	private PersistenceManager pm = new FilePersistenceManager();
	
	/**
	 * Methode zum Einlesen von Buchdaten aus einer Datei.
	 * 
	 * @param datei Datei, die einzulesenden Bücherbestand enthält
	 * @throws IOException
	 */
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager für Lesevorgänge öffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
			// Artikel-Objekt einlesen
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				// Buch in Liste einfügen
				try {
					einfuegen(einArtikel);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}

	/**
	 * Methode zum Schreiben der Buchdaten in eine Datei.
	 * 
	 * @param datei Datei, in die der Bücherbestand geschrieben werden soll
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException  {
		// PersistenzManager für Schreibvorgänge öffnen
		pm.openForWriting(datei);

		for (Artikel a : artikelBestand) {
			pm.speichereArtikel(a);
		}

//		// Alternative Implementierung mit Iterator:
//		Iterator<Buch> iter = buchBestand.iterator();
//		while (iter.hasNext()) {
//			Buch b = iter.next();
//			pm.speichereBuch(b);
//		}

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}

	/**
	 * Methode, die ein Buch an das Ende der Bücherliste einfügt.
	 * 
	 * @param einBuch das einzufügende Buch
	 * @throws BuchExistiertBereitsException wenn das Buch bereits existiert
	 */
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (artikelBestand.contains(einArtikel)) {
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}

		// das übernimmt der Vector:
		artikelBestand.add(einArtikel);
	}

	/**
	 * Methode zum Löschen eines Buchs aus dem Bestand. 
	 * 
	 * @param einBuch das löschende Buch
	 */
	public void loeschen(Artikel einArtikel) {
		// das übernimmt der Vector:
		artikelBestand.remove(einArtikel);
	}

	/**
	 * Methode, die anhand eines Titels nach Büchern sucht. Es wird eine Liste von Büchern
	 * zurückgegeben, die alle Bücher mit exakt übereinstimmendem Titel enthält.
	 * 
	 * @param titel Titel des gesuchten Buchs
	 * @return Liste der Bücher mit gesuchtem Titel (evtl. leer)
	 */
	public List<Artikel> sucheArtikel(String name) {
		// auch für das Suchergebnis bietet sich
		// die Verwendung von Generics an
		List<Artikel> suchErg = new Vector<Artikel>();

		// Buchbestand durchlaufen und nach Titel suchen
		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
			Artikel a = iter.next();
			if (a.getName().equals(name))
				suchErg.add(a);
		}
		// Alternative Implementierung mit neuer for-Schleife:
		/*
		for (Buch buch : buchBestand) {
			if ((buch).getTitel().equals(titel))
				suchErg.add(buch);
		}
		*/

		return suchErg;
	}
	
	public List<Artikel> sucheArtikel(int nummer) {
		// auch für das Suchergebnis bietet sich
		// die Verwendung von Generics an
		List<Artikel> suchErg = new Vector<Artikel>();

		// Buchbestand durchlaufen und nach Titel suchen
		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
			Artikel a = iter.next();
			if (a.getNummer() == nummer)
				suchErg.add(a);
		}
		// Alternative Implementierung mit neuer for-Schleife:
		/*
		for (Buch buch : buchBestand) {
			if ((buch).getTitel().equals(titel))
				suchErg.add(buch);
		}
		*/

		return suchErg;
	}
	
	/**
	 * Methode, die eine KOPIE des Bücherbestands zurückgibt.
	 * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger 
	 * der Daten nicht ermöglichen möchte, die Original-Daten 
	 * zu manipulieren.)
	 * ACHTUNG: die in der kopierten Bücherliste referenzierten
	 * 			sind nicht kopiert worden, d.h. ursprüngliche
	 * 			Bücherliste und ihre Kopie verweisen auf dieselben
	 * 			Buch-Objekte.
	 * 
	 * @return Liste aller Bücher im Buchbestand (Kopie)
	 */
	public List<Artikel> getArtikelBestand() {
		return new Vector<Artikel>(artikelBestand);
	}
	
	// TODO: Weitere Methoden, z.B. zum Auslesen und Entfernen von Büchern
	// ...

}