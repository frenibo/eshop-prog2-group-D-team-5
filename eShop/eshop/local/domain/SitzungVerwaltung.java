package eshop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import eshop.local.persistence.FilePersistenceManager;
import eshop.local.persistence.PersistenceManager;
import eshop.local.valueobjects.Sitzung;

public class SitzungVerwaltung {
	// Verwaltung des Buchbestands in einem Vector
	private List<Sitzung> eventListe = new Vector<Sitzung>();

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
		
		Sitzung einEvent;
		do {
			// Artikel-Objekt einlesen
			einEvent = pm.ladeSitzungen();
			
			if (einEvent != null) {
				// Buch in Liste einfügen
				try {
					einfuegen(einEvent);
				} 
				catch (Exception e) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einEvent != null);

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

		for (Sitzung e : eventListe) {
			pm.speichereSitzungen(e);
		}

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}
	
	/**
	 * Methode, die ein Buch an das Ende der Bücherliste einfügt.
	 * 
	 * @param einBuch das einzufügende Buch
	 * @throws BuchExistiertBereitsException wenn das Buch bereits existiert
	 */
	public String einfuegen(Sitzung einEvent) {
		
			eventListe.add(einEvent);
			return "Erfolgreich hinzugefügt";			
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
	public List<Sitzung> getSitzungListe() {
		return eventListe;
	}
	
	public int getObjektAnzahl() {
		int anzahl = 0;
		for(Sitzung e: eventListe) {
			anzahl++;
		}
		return anzahl;
	}



}
