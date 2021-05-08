package eshop.local.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.List;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.Sitzung;
import eshop.local.valueobjects.Artikel;

public class Bestand {
	
	// Präfix für Namen der Dateien, in der die Bibliotheksdaten gespeichert sind
		private String datei = "";
		private Path pfad;
		
		private ArtikelVerwaltung meineArtikel;
		// private KundenVerwaltung meineKunden;
		// hier weitere Verwaltungsklassen, z.B. für Autoren oder Angestellte
		
		/**
		 * Konstruktor, der die Basisdaten (Bücher, Kunden, Autoren) aus Dateien einliest
		 * (Initialisierung der Bibliothek).
		 * 
		 * Namensmuster für Dateien:
		 *   datei+"_B.txt" ist die Datei der Bücher
		 *   datei+"_K.txt" ist die Datei der Kunden
		 * 
		 * @param datei Präfix für Dateien mit Basisdaten (Bücher, Kunden, Autoren)
		 * @throws IOException z.B. wenn eine der Dateien nicht existiert.
		 */
		public Bestand(String datei) throws IOException {
			this.datei = datei;
			
			pfad = Paths.get(datei);
			
			if(!Files.exists(pfad)) {
				try {
					File file = new File(this.datei);
					// Buchbestand aus Datei einlesen
					meineArtikel = new ArtikelVerwaltung();
					meineArtikel.liesDaten(file.getName());
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			} else {
				meineArtikel = new ArtikelVerwaltung();
				meineArtikel.liesDaten(this.datei);
			}
//			// Kundenkartei aus Datei einlesen
//			meineKunden = new KundenVerwaltung();
//			meineKunden.liesDaten(datei+"_K.txt");
//			meineKunden.schreibeDaten(datei+"_K.txt");
		}
//			Wird der Bestand ohne Eingabe-Datei initialisiert, 
//			so wird eine "lokale", völlig leere Datei mit Namen LOCAL_B.txt erstellt
//			existiert diese Datei bereits, so wird sie nicht erstellt und 
//			es wird mit der bereits existierenden Datei gearbeitet.
		public Bestand() throws IOException {
			
			this.datei = "LOCAL.txt";
			pfad = Paths.get("LOCAL_B.txt");
			
			if(!Files.exists(pfad)) {
				try {
					File file = new File("LOCAL_B.txt");
					// Buchbestand aus Datei einlesen
					meineArtikel = new ArtikelVerwaltung();
					meineArtikel.liesDaten(file.getName());
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			} else {
				meineArtikel = new ArtikelVerwaltung();
				meineArtikel.liesDaten("LOCAL_B.txt");
			}
//			// Kundenkartei aus Datei einlesen
//			meineKunden = new KundenVerwaltung();
//			meineKunden.liesDaten(datei+"_K.txt");
//			meineKunden.schreibeDaten(datei+"_K.txt");
		}


		/**
		 * Methode, die eine Liste aller im Bestand befindlichen Bücher zurückgibt.
		 * 
		 * @return Liste aller Bücher im Bestand der Bibliothek
		 */
		public List<Artikel> gibAlleArtikel() {
			// einfach delegieren an BuecherVerwaltung meineBuecher
			return meineArtikel.getArtikelBestand();
		}

		/**
		 * Methode zum Suchen von Büchern anhand des Titels. Es wird eine Liste von Büchern
		 * zurückgegeben, die alle Bücher mit exakt übereinstimmendem Titel enthält.
		 * 
		 * @param titel Titel des gesuchten Buchs
		 * @return Liste der gefundenen Bücher (evtl. leer)
		 */
		public List<Artikel> sucheNachName(String name) {
			// einfach delegieren an BuecherVerwaltung meineBuecher
			return meineArtikel.sucheArtikel(name); 
		}
		
		public List<Artikel> sucheNachNr(int nummer) {
			// einfach delegieren an BuecherVerwaltung meineBuecher
			return meineArtikel.sucheArtikel(nummer); 
		}


		/**
		 * Methode zum Einfügen eines neuen Buchs in den Bestand. 
		 * Wenn das Buch bereits im Bestand ist, wird der Bestand nicht geändert.
		 * 
		 * @param titel Titel des Buchs
		 * @param nummer Nummer des Buchs
		 * @return Buch-Objekt, das im Erfolgsfall eingefügt wurde
		 * @throws BuchExistiertBereitsException wenn das Buch bereits existiert
		 */
		public String fuegeArtikelEin(String name, int nummer) throws ArtikelExistiertBereitsException {
			Artikel a = new Artikel(name, nummer);
			String rueckmeldung = meineArtikel.einfuegen(a);
			return rueckmeldung;
		}
		
		public String fuegeArtikelEin(String name, int nummer, int anzahl) throws ArtikelExistiertBereitsException {
			Artikel a = new Artikel(name, nummer, anzahl);
			String rueckmeldung = meineArtikel.einfuegen(a);
			return rueckmeldung;
		}

		public void aendereAnzahl(int nummer, int anzahl) {
			
			meineArtikel.aendereArtikelAnzahl(nummer, anzahl);
			
		}
		
		public void verschiebeWarenkorb(int nummer, int anzahl) {
			
			String sitzungsNr = Sitzung.getSitzungsNr();			
			meineArtikel.verschieben(nummer, anzahl, sitzungsNr);
			
		}
		
		/**
		 * Methode zum Löschen eines Buchs aus dem Bestand. 
		 * Es wird nur das erste Vorkommen des Buchs gelöscht.
		 * 
		 * @param titel Titel des Buchs
		 * @param nummer Nummer des Buchs
		 */
		public void loescheArtikel(String name, int nummer) {
			Artikel a = new Artikel(name, nummer);
			meineArtikel.loeschen(a);
		}
		
		public void loescheArtikel(Artikel a) {
			meineArtikel.loeschen(a);
		}
		
		/**
		 * Methode zum Speichern des Buchbestands in einer Datei.
		 * 
		 * @throws IOException z.B. wenn Datei nicht existiert
		 */
		public void schreibeArtikel() throws IOException {
			meineArtikel.schreibeDaten(datei);
		}

		// TODO: Weitere Funktionen der Bibliotheksverwaltung, z.B. ausleihen, zurückgeben etc.
		// ...

}

