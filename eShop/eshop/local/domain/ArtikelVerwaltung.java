package eshop.local.domain;

import java.io.IOException;
import java.util.*;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.persistence.FilePersistenceManager;
import eshop.local.persistence.PersistenceManager;
import eshop.local.ui.eShop;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;


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
	private List<Artikel> artikelListe = new Vector<Artikel>();

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

		for (Artikel a : artikelListe) {
			pm.speichereArtikel(a);
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
	
	public String einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		
		artikelListe.add(einArtikel);
		return "Erfolgreich hinzugefügt";
	}
	
	/**
	 * Methode zum Löschen eines Buchs aus dem Bestand. 
	 * 
	 * @param einBuch das löschende Buch
	 */
	public void loeschen(Artikel einArtikel) {
		// das übernimmt der Vector:
		artikelListe.remove(einArtikel);
	}
	
	public void clean() {
		List<Artikel> list = artikelListe;
		for(Artikel a : list) {
			if (a.getAnzahl() == 0) {
				loeschen(a);
			}
		}
	}
	
	public void alleArtikelLoeschen() {
		for(Artikel a:artikelListe) {
			artikelListe.remove(a);
		}
	}
	
	public void aendereArtikelAnzahl(int nummer, int anzahl) throws IOException {
		
		int zaehler = -1;
		int anzahlUrsprung = 0;
		int difference;
		String lagerung = "Null";
		
		for(Artikel a : artikelListe) {
			zaehler++;
			if (a.getNummer() == nummer) {
				
				anzahlUrsprung = artikelListe.get(zaehler).getAnzahl();
				artikelListe.get(zaehler).setAnzahl(anzahl);
				
				if(anzahlUrsprung < anzahl) {
					lagerung = "Einlagerung";
				} else if(anzahlUrsprung > anzahl) {
					lagerung = "Auslagerung";
				}
				
				difference = Math.abs(anzahlUrsprung - anzahl);
				
				eShop.neuesLagerungsevent(lagerung, artikelListe.get(zaehler), difference, eShop.getSitzungsNr(), eShop.getAktuellerUser());
		}
		}
	}
	
	public void verschiebenArtikel(int nummer, int anzahl, ArtikelVektorListe zielListe) throws IOException {
		
		int anzahlZuvor = 0;
		boolean imBestandAbgezogen = false;
		boolean imWarenkorbHinzugefügt = false;
		
		String lagerung = "";
		if(zielListe.equals(eShop.getWarenkorb())) {
			lagerung = "Auslagerung";
		} else lagerung = "Einlagerung";
		
		//Hilfsvariablen HV
		Artikel artikelHV = new Artikel ("error", 0, 0);
		Artikel artikelHV2 = new Artikel ("error2", 0, 0);
		Artikel artikelHV3 = new Artikel ("error3", 0, 0);
		
		List<Artikel> list = zielListe.gibAlleArtikel();
		
		// Bestand durchlaufen und nach Artikelnummer suchen
		Iterator<Artikel> iter = artikelListe.iterator();
		 do {
			//TODO: throws ClassCastException:
			artikelHV = iter.next();  //add cast to Massenartikel?
			if (artikelHV.getNummer() == nummer) {
				if(anzahl % artikelHV.getPacketgroeße() != 0) {
					//TODO: system out entfernen
					System.out.println("Verschieben gescheitert.");
					System.out.println("Sie können nur Artikelmengen verschieben und kaufen, die einem Vielfachen ihrer Packetgröße entspricht.");
					System.out.println("Die Packetgröße des Artikels Nr. " + artikelHV.getNummer() + " ist " + artikelHV.getPacketgroeße() + ".");
					imBestandAbgezogen = false;
				}
				else {
					anzahlZuvor = artikelHV.getAnzahl();
					if(anzahlZuvor >= anzahl) {
						int difference = Math.abs(anzahlZuvor - anzahl);
						artikelHV.setAnzahl(difference);
						artikelHV3 = artikelHV;
						imBestandAbgezogen = true;
						if(difference == 0) {
							//throws ConcurrentModificationException
							//loeschen(artikelHV);
						}
					}
					else {
						System.out.println("Diese Menge überschreitet die Verfügbarkeit.");
					}
				}
			}	
		} while (iter.hasNext());
		if(imBestandAbgezogen == true) {
			Iterator<Artikel> iterZiel = list.iterator();
			while (iterZiel.hasNext()) {
				artikelHV2 = iterZiel.next(); //add cast to Massenartikel?
				if (artikelHV2.getNummer() == nummer) {
					anzahlZuvor = artikelHV2.getAnzahl();
					int ergebnis2 = anzahlZuvor+anzahl;
					artikelHV2.setAnzahl(ergebnis2);
					imWarenkorbHinzugefügt = true;
					eShop.neuesLagerungsevent(lagerung, artikelHV2, anzahl, eShop.getSitzungsNr(), eShop.getAktuellerUser());
				}
			}
			if(imWarenkorbHinzugefügt == false) {			
				try {
					zielListe.fuegeArtikelEin(artikelHV3.getName(), artikelHV3.getNummer(), anzahl, artikelHV3.getPreis(), artikelHV3.getPacketgroeße());
					eShop.neuesLagerungsevent(lagerung, artikelHV3, anzahl, eShop.getSitzungsNr(), eShop.getAktuellerUser());
				} catch (ArtikelExistiertBereitsException e) {
					
				}
				
								
			}
		}
		try {
			clean();
		} catch (ConcurrentModificationException c) {
			
		}
	}
	
	public void alleArtikelVerschieben(ArtikelVektorListe zielListe) throws IOException {
		
		boolean imWarenkorbHinzugefügt = false;
		
		Artikel artikelUrsprung = new Artikel ("error", 0, 0);
		Artikel artikelZiel = new Artikel ("error2", 0, 0);
		
		List<Artikel> list = zielListe.gibAlleArtikel();
		
		// Bestand durchlaufen und nach Artikelnummer suchen
		Iterator<Artikel> iterUrsprung = artikelListe.iterator();
		
		while (iterUrsprung.hasNext()) {
			
			// Zielliste durchlaufen und nach Artikelnummer suchen
			// Muss hier gesetzt werden und nicht außerhalb der while Schleife
			// um iterZiel auf null zu setzen.
			Iterator<Artikel> iterZiel = list.iterator();
			
			// artikelUrsprung scheint auf das Artikelobjekt im artikelBestand zu zeigen.
			// ich hatte angenommen es wäre 
			artikelUrsprung = iterUrsprung.next();
			
			imWarenkorbHinzugefügt = false;
						
			while (iterZiel.hasNext()) {
				
				artikelZiel = iterZiel.next();
				
				if (artikelZiel.getNummer() == artikelUrsprung.getNummer()) {
					int zielAnzahl = artikelZiel.getAnzahl();
					int ursprungAnzahl = artikelUrsprung.getAnzahl();
					int ergebnis = zielAnzahl + ursprungAnzahl; 
					artikelZiel.setAnzahl(ergebnis);
					imWarenkorbHinzugefügt = true;
				}
			}
			if(imWarenkorbHinzugefügt == false) {
				String lagerung = "";
				if(zielListe.equals(eShop.getWarenkorb())) {
					lagerung = "Einlagerung";
				} else lagerung = "Auslagerung";
				try {
					zielListe.fuegeArtikelEin(artikelUrsprung.getName(), artikelUrsprung.getNummer(), artikelUrsprung.getAnzahl(), artikelUrsprung.getPreis(), artikelUrsprung.getPacketgroeße());
					eShop.neuesLagerungsevent(lagerung, artikelUrsprung, artikelUrsprung.getAnzahl(), eShop.getSitzungsNr(), eShop.getAktuellerUser());
				} catch (ArtikelExistiertBereitsException e) {
					
				}
			}
			
			artikelUrsprung.setAnzahl(0);
			//crashes program for some reason:
			//loeschen(artikelUrsprung);
			//artikelBestand.remove(artikelUrsprung);
		}
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
		Iterator<Artikel> iter = artikelListe.iterator();
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
		Iterator<Artikel> iter = artikelListe.iterator();
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
	
	public List<Artikel> sucheArtikel(String name, int nummer) {
		// auch für das Suchergebnis bietet sich
		// die Verwendung von Generics an
		List<Artikel> suchErg = new Vector<Artikel>();

		// Buchbestand durchlaufen und nach Titel suchen
		Iterator<Artikel> iter = artikelListe.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
			Artikel a = iter.next();
			if (a.getNummer() == nummer && a.getName().equals(name))
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
		return new Vector<Artikel>(artikelListe);
	}
	
	public int getObjektAnzahl() {
		int anzahl = 0;
		for(Artikel a: artikelListe) {
			anzahl++;
		}
		return anzahl;
	}
	// TODO: Weitere Methoden, z.B. zum Auslesen und Entfernen von Büchern
	// ...

}