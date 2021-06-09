package eshop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.persistence.FilePersistenceManager;
import eshop.local.persistence.PersistenceManager;
import eshop.local.valueobjects.Inputevent;

public class InputeventVerwaltung {
	// Verwaltung des Buchbestands in einem Vector
			private List<Inputevent> eventListe = new Vector<Inputevent>();

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
				
				Inputevent einEvent;
				do {
					// Artikel-Objekt einlesen
					einEvent = pm.ladeInputevents();
					
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

				for (Inputevent e : eventListe) {
					pm.speichereInputevents(e);
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
			public String einfuegen(Inputevent einEvent) {
				
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
			public List<Inputevent> getInputeventListe() {
				return eventListe;
			}
			
			public int getObjektAnzahl() {
				int anzahl = 0;
				for(Inputevent e: eventListe) {
					anzahl++;
				}
				return anzahl;
			}

}
