package eshop.local.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Lagerungsevent;

public class LagerungseventVektorListe {



		// Präfix für Namen der Dateien, in der die Bibliotheksdaten gespeichert sind
			private String datei = "";
			
			private LagerungseventVerwaltung meineLagerungsevents;
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
			public LagerungseventVektorListe(String datei) throws IOException {
				this.datei = datei;
				
				Path pfad = Paths.get(datei);
				
				if(!Files.exists(pfad)) {
					
					try {
						File file = new File(this.datei);
						// Buchbestand aus Datei einlesen
						meineLagerungsevents = new LagerungseventVerwaltung();
						meineLagerungsevents.liesDaten(file.getName());
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				} else {
					meineLagerungsevents = new LagerungseventVerwaltung();
					meineLagerungsevents.liesDaten(this.datei);
				}
			}
			
			public List<Lagerungsevent> getLagerungseventListe() {
				return meineLagerungsevents.getLagerungseventListe();				
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
			public void fuegeLagerungseventEin(Lagerungsevent event) throws ArtikelExistiertBereitsException {
				meineLagerungsevents.einfuegen(event);
			}

			
			/**
			 * Methode zum Speichern des Buchbestands in einer Datei.
			 * 
			 * @throws IOException z.B. wenn Datei nicht existiert
			 */
			public void schreibeLagerungsevents() throws IOException {
				meineLagerungsevents.schreibeDaten(datei);
			}
}
