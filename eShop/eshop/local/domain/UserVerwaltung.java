package eshop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.persistence.FilePersistenceManager;
import eshop.local.persistence.PersistenceManager;
import eshop.local.valueobjects.User;

public class UserVerwaltung {

	// Verwaltung des Buchbestands in einem Vector
		private List<User> userListe = new Vector<User>();

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

			User einUser;
			do {
				// Artikel-Objekt einlesen
				einUser = pm.ladeUser();
				if (einUser != null) {
					// Buch in Liste einfügen
					try {
						einfuegen(einUser);
					} catch (ArtikelExistiertBereitsException e1) {
						// Kann hier eigentlich nicht auftreten,
						// daher auch keine Fehlerbehandlung...
					}
				}
			} while (einUser != null);

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

			for (User u : userListe) {
				pm.speichereUser(u);
			}

//			// Alternative Implementierung mit Iterator:
//			Iterator<Buch> iter = buchBestand.iterator();
//			while (iter.hasNext()) {
//				Buch b = iter.next();
//				pm.speichereBuch(b);
//			}

			// Persistenz-Schnittstelle wieder schließen
			pm.close();
		}
		
		/**
		 * Methode, die ein Buch an das Ende der Bücherliste einfügt.
		 * 
		 * @param einBuch das einzufügende Buch
		 * @throws BuchExistiertBereitsException wenn das Buch bereits existiert
		 */
		public String einfuegen(User einUser) throws ArtikelExistiertBereitsException {
			
				userListe.add(einUser);
				return "Erfolgreich hinzugefügt";			
		}

		/**
		 * Methode zum Löschen eines Buchs aus dem Bestand. 
		 * 
		 * @param einBuch das löschende Buch
		 */
		public void loeschen(User einUser) {
			// das übernimmt der Vector:
			userListe.remove(einUser);
		}
				
		/**
		 * Methode, die anhand eines Titels nach Büchern sucht. Es wird eine Liste von Büchern
		 * zurückgegeben, die alle Bücher mit exakt übereinstimmendem Titel enthält.
		 * 
		 * @param titel Titel des gesuchten Buchs
		 * @return Liste der Bücher mit gesuchtem Titel (evtl. leer)
		 */
		
		public List<User> sucheUser(int userNr) {
			// auch für das Suchergebnis bietet sich
			// die Verwendung von Generics an
			List<User> suchErg = new Vector<User>();

			// Buchbestand durchlaufen und nach Titel suchen
			Iterator<User> iter = userListe.iterator();
			while (iter.hasNext()) {
				// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
				// 		    hier nicht erforderlich wegen Verwendung von Generics
				// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
				User u = iter.next();
				if (u.getUserNr() == userNr)
					suchErg.add(u);
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
		public List<User> getUserListe() {
			return userListe;
		}
		
		public int getObjektAnzahl() {
			int anzahl = 0;
			for(User u: userListe) {
				anzahl++;
			}
			return anzahl;
		}
}
