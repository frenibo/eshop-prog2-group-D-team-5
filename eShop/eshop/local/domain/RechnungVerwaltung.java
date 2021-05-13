package eshop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.persistence.FilePersistenceManager;
import eshop.local.persistence.PersistenceManager;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Rechnung;

public class RechnungVerwaltung {

	// Verwaltung des Buchbestands in einem Vector
	private List<Rechnung> rechnungListe = new Vector<Rechnung>();

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

		Rechnung eineRechnung;
		do {
			// Artikel-Objekt einlesen
			eineRechnung = pm.ladeRechnung();
			if (eineRechnung != null) {
				// Buch in Liste einfügen
				try {
					einfuegen(eineRechnung);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (eineRechnung != null);

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

		for (Rechnung r : rechnungListe) {
			pm.speichereRechnung(r);
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
	public String einfuegen(Rechnung eineRechnung) throws ArtikelExistiertBereitsException {
		
		rechnungListe.add(eineRechnung);
		return "Erfolgreich hinzugefügt";
	}


	/**
	 * Methode zum Löschen eines Buchs aus dem Bestand. 
	 * 
	 * @param einBuch das löschende Buch
	 */
	public void loeschen(Rechnung eineRechnung) {
		// das übernimmt der Vector:
		rechnungListe.remove(eineRechnung);
	}
	
	public void alleRechnungenLoeschen() {
		for(Rechnung r:rechnungListe) {
			rechnungListe.remove(r);
		}
	}
	
	
	/**
	 * Methode, die anhand eines Titels nach Büchern sucht. Es wird eine Liste von Büchern
	 * zurückgegeben, die alle Bücher mit exakt übereinstimmendem Titel enthält.
	 * 
	 * @param titel Titel des gesuchten Buchs
	 * @return Liste der Bücher mit gesuchtem Titel (evtl. leer)
	 */
	public List<Rechnung> sucheRechnung(String name) {
		// auch für das Suchergebnis bietet sich
		// die Verwendung von Generics an
		List<Rechnung> suchErg = new Vector<Rechnung>();

		// Buchbestand durchlaufen und nach Titel suchen
		Iterator<Rechnung> iter = rechnungListe.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
			Rechnung r = iter.next();
			if (r.getUser().getName().equals(name))
				suchErg.add(r);
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
	
	public List<Rechnung> sucheRechnung(int nummer) {
		// auch für das Suchergebnis bietet sich
		// die Verwendung von Generics an
		List<Rechnung> suchErg = new Vector<Rechnung>();

		// Buchbestand durchlaufen und nach Titel suchen
		Iterator<Rechnung> iter = rechnungListe.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
			Rechnung r = iter.next();
			if (r.getUser().getUserNr() == nummer)
				suchErg.add(r);
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
	public List<Rechnung> getRechnungBestand() {
		return new Vector<Rechnung>(rechnungListe);
	}
	
	public int getObjektAnzahl() {
		int anzahl = 0;
		for(Rechnung r: rechnungListe) {
			anzahl++;
		}
		return anzahl;
	}

}
