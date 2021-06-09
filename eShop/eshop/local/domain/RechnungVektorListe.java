package eshop.local.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Rechnung;

public class RechnungVektorListe {
	// Präfix für Namen der Dateien, in der die Bibliotheksdaten gespeichert sind
			private String datei = "";
			
			private RechnungVerwaltung meineRechnungen;
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
			public RechnungVektorListe(String datei) throws IOException {
				this.datei = datei;
				
				Path pfad = Paths.get(datei);
				
				if(!Files.exists(pfad)) {
					try {
						File file = new File(this.datei);
						// Buchbestand aus Datei einlesen
						meineRechnungen = new RechnungVerwaltung();
						meineRechnungen.liesDaten(file.getName());
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				} else {
					meineRechnungen = new RechnungVerwaltung();
					meineRechnungen.liesDaten(this.datei);
				}
//				// Kundenkartei aus Datei einlesen
//				meineKunden = new KundenVerwaltung();
//				meineKunden.liesDaten(datei+"_K.txt");
//				meineKunden.schreibeDaten(datei+"_K.txt");
			}
//			
			/**
			 * Methode, die eine Liste aller im Bestand befindlichen Bücher zurückgibt.
			 * 
			 * @return Liste aller Bücher im Bestand der Bibliothek
			 */
			public List<Rechnung> gibAlleRechnungen() {
				// einfach delegieren an BuecherVerwaltung meineBuecher
				return meineRechnungen.getRechnungBestand();
			}
			
			public int gibRechnungAnzahl() {
				
				return meineRechnungen.getObjektAnzahl();
			}
			

			/**
			 * Methode zum Suchen von Büchern anhand des Titels. Es wird eine Liste von Büchern
			 * zurückgegeben, die alle Bücher mit exakt übereinstimmendem Titel enthält.
			 * 
			 * @param titel Titel des gesuchten Buchs
			 * @return Liste der gefundenen Bücher (evtl. leer)
			 */
			public List<Rechnung> sucheNachName(String name) {
				// einfach delegieren an BuecherVerwaltung meineBuecher
				return meineRechnungen.sucheRechnung(name); 
			}
			
			public List<Rechnung> sucheNachNr(int nummer) {
				// einfach delegieren an BuecherVerwaltung meineBuecher
				return meineRechnungen.sucheRechnung(nummer); 
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
			public String fuegeRechnungEin(Rechnung rechnung) throws ArtikelExistiertBereitsException {
				String rueckmeldung = meineRechnungen.einfuegen(rechnung);
				return rueckmeldung;
			}
			
			
			/**
			 * Methode zum Speichern des Buchbestands in einer Datei.
			 * 
			 * @throws IOException z.B. wenn Datei nicht existiert
			 */
			
			public void schreibeRechnung() throws IOException {
				meineRechnungen.schreibeDaten(this.datei);
			}
}
