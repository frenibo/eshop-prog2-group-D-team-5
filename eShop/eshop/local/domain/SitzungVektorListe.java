package eshop.local.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Sitzung;

public class SitzungVektorListe {
	
				// Präfix für Namen der Dateien, in der die Bibliotheksdaten gespeichert sind
				private String datei = "";
				
				private SitzungVerwaltung meineSitzungen;
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
				public SitzungVektorListe(String datei) throws IOException {
					this.datei = datei;
					
					Path pfad = Paths.get(datei);
					
					if(!Files.exists(pfad)) {
						
						try {
							File file = new File(this.datei);
							// Buchbestand aus Datei einlesen
							meineSitzungen = new SitzungVerwaltung();
							meineSitzungen.liesDaten(file.getName());
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					} else {
						meineSitzungen = new SitzungVerwaltung();
						meineSitzungen.liesDaten(this.datei);
					}
				}
				
				public List<Sitzung> getSitzungListe() {
					return meineSitzungen.getSitzungListe();				
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
				public void fuegeSitzungEin(Sitzung sitzung) throws ArtikelExistiertBereitsException {
					meineSitzungen.einfuegen(sitzung);
				}

				
				/**
				 * Methode zum Speichern des Buchbestands in einer Datei.
				 * 
				 * @throws IOException z.B. wenn Datei nicht existiert
				 */
				public void schreibeSitzungen() throws IOException {
					meineSitzungen.schreibeDaten(datei);
				}

}
