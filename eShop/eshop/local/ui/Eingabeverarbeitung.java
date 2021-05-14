package eshop.local.ui;

import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;

import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
//import eshop.local.ui.cui.EshopClientCUI;
import eshop.local.ui.cui.Main;
import eshop.local.ui.MenueAusgabe;

public class Eingabeverarbeitung {

	private String line;
	private String level;
	private BufferedReader in;
	private MenueAusgabe menue;
	
	public Eingabeverarbeitung() {
		
		setLine("");
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new MenueAusgabe();
	}
	
	public Eingabeverarbeitung(String line) {
		
		setLine(line);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new MenueAusgabe();
	}
	
	public Eingabeverarbeitung(String line, String level) {
		
		setLine(line);
		setLevel(level);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new MenueAusgabe();
	}
	
	public void setLevel(String level) {
		this.level = level;
		this.menue.setMenueLevel(level);
	}
	
	public void gibMenueAus() {
		this.menue.gibMenueAus();
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public void setLine(String line) {
		this.line = line;
	}
	
	public String getLine() {
		return this.line;
	}
	
	
	public void einlesenUndVerarbeiten() throws IOException {
			
		verarbeitung(liesEingabe());
		
	}
	
	public String liesEingabe() throws IOException {
		// einlesen von Konsole
		setLine(in.readLine());
		return this.line;
	}
	
	public void verarbeitung(String line) throws IOException {
		
		if(this.level.isEmpty()) {
			this.level = "startmenue";
		}

		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitung(String line, String level) throws IOException {
		
		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitungsLevel(String line, String level) throws IOException {
		
		List<Artikel> liste;
		List<User> userListe;
		List<Rechnung> rechnungListe;
		
		String nummerString = "";
		int nummer = 0;
		String anzahlString = "";
		int anzahl = 0;
		String packetString = "";
		int packet = 0;
		String name = "";
		String preisString = "";
		double preis = 0.0;
		String passwort = "";
		
		if(level.equals("startmenue")) {
			
			
			try {
				nummer = Integer.parseInt(line);
			} catch (Exception e) {}
			
			if (nummer > 0) {
				liste = Sitzung.bst.sucheNachNr(nummer);
				Sitzung.gibArtikellisteUnsortiertAus(liste);

			}
			
			// Eingabe bearbeiten:
			switch (line) {
			//Alle Artikel anzeigen
			case "a":
				liste = Sitzung.bst.gibAlleArtikel();
				Sitzung.gibArtikellisteUnsortiertAus(liste);
				System.out.println("\n   Gib die Nr eines Artikels ein um damit zu interagieren.");
				break;
			//Alle Artikel alphabetisch sortiert anzeigen
			case "abc":
				liste = Sitzung.bst.gibAlleArtikel();
				Sitzung.gibArtikellisteAlphabetischAus(liste);
				System.out.println("\n   Gib die Nr eines Artikels ein um damit zu interagieren.");
				break;
				//Alle Artikel alphabetisch sortiert anzeigen
			case "a#":
				liste = Sitzung.bst.gibAlleArtikel();
				Sitzung.gibArtikellisteNummerischAus(liste);
				System.out.println("\n   Gib die Nr eines Artikels ein um damit zu interagieren.");
				break;
			//Alle Artikel im Warenkorb kaufen
			case "b":
				System.out.println("Kundennummer >");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				if(nummer != Sitzung.getAktuellerUser().getUserNr() || nummer == 0) {
					System.out.println("Bitte loggen Sie sich zunächst ein.");
				}
				else {
					Sitzung.bst.schreibeArtikel();
					Sitzung.wnk.schreibeArtikel();
					liste = Sitzung.wnk.gibAlleArtikel();
					Rechnung rechnung = new Rechnung(Sitzung.getAktuellerUser(), liste, true);
					try {
						//sollte man so wahrscheinlich nicht machen:
						Sitzung.rch.fuegeRechnungEin(rechnung);
						
					} catch (ArtikelExistiertBereitsException e) {
						// Hier Fehlerbehandlung...
						System.out.println("Fehler beim einfügen");
						e.printStackTrace();
					}
					Sitzung.rch.schreibeRechnung();
					System.out.println("Kauf abgeschlossen. Hier die Rechnung: \n");
					System.out.println(rechnung);
					System.out.println("\nNeue Sitzung startet.");
					Main.main(null);
				}
				
				break;
			//Alle User anzeigen
			case "u":
				userListe = Sitzung.usr.gibAlleUser();
				Sitzung.gibUserlisteAus(userListe);
				break;
			//Warenkorb anzeigen
			case "w":
				liste = Sitzung.wnk.gibAlleArtikel();
				Sitzung.gibArtikellisteUnsortiertAus(liste);
				System.out.println("Gesamtpreis: " + Sitzung.wnk.gibGesamtpreis() + " €");
				break;
			//Artikel in Warenkorb verschieben
			case "k":
				System.out.print("Artikelnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Anzahl > ");
				anzahlString = liesEingabe();
				anzahl = Integer.parseInt(anzahlString);
				Sitzung.bst.verschiebeInWarenkorb(nummer, anzahl);
				break;
				//Artikel von Warenkorb zurücklegen
			case "l":
				System.out.print("Artikelnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Anzahl > ");
				anzahlString = liesEingabe();
				anzahl = Integer.parseInt(anzahlString);
				Sitzung.wnk.verschiebeInBestand(nummer, anzahl);
				break;
			//Warenkorb leeren
			case "wl":
				Sitzung.wnk.warenkorbLeeren();
				System.out.println("Warenkorb geleert.");
				break;
			//Artikelanzahl ändern
			case "v":
				if(Sitzung.getAktuellerUser().getUserNr() == 0) {
					System.out.println("Bitte loggen Sie sich zunächst ein.");
				}
				else {
					System.out.print("Artikelnummer > ");
					nummerString = liesEingabe();
					nummer = Integer.parseInt(nummerString);
					System.out.print("Neue Anzahl > ");
					anzahlString = liesEingabe();
					anzahl = Integer.parseInt(anzahlString);
					Sitzung.bst.aendereAnzahl(nummer, anzahl);
					System.out.print("\nAnzahl aktualisiert.");
				}
				break;
			//Artikel löschen
			case "d":
				if(Sitzung.getAktuellerUser().getUserNr() == 0) {
					System.out.println("Bitte loggen Sie sich zunächst ein.");
				}
				else {
					// lies die notwendigen Parameter, einzeln pro Zeile
					System.out.print("Artikelnummer > ");
					nummerString = liesEingabe();
					nummer = Integer.parseInt(nummerString);
					System.out.print("Artikelname  > ");
					name = liesEingabe();
					// Die Bibliothek das Buch löschen lassen:
					Sitzung.bst.loescheArtikel(name, nummer);
				}
				break;
			//Artikel einfügen
			case "e":
				if(Sitzung.getAktuellerUser().getUserNr() == 0) {
					System.out.println("Bitte loggen Sie sich zunächst ein.");
				}
				else {
					// lies die notwendigen Parameter, einzeln pro Zeile
					System.out.print("Artikelnummer > ");
					nummerString = liesEingabe();
					nummer = Integer.parseInt(nummerString);
					System.out.print("Name des Artikels  > ");
					name = liesEingabe();
					System.out.print("Packetgröße > ");
					packetString = liesEingabe();
					packet = Integer.parseInt(packetString);
					System.out.print("Anzahl > ");
					anzahlString = liesEingabe();
					anzahl = Integer.parseInt(anzahlString);
					System.out.print("Preis > ");
					preisString = liesEingabe();
					preis = Double.parseDouble(preisString);

					try {
						//sollte man so wahrscheinlich nicht machen:
						String resultat = Sitzung.bst.fuegeArtikelEin(name, nummer, packet, anzahl, preis);
					
						if(resultat.equals("Erfolgreich hinzugefügt")) {
							Sitzung.setAktuellerArtikel(new Artikel(name, nummer, packet, anzahl, preis));
							System.out.print("\n");
							System.out.println(Sitzung.getAktuellerArtikel());
							setLevel("speichern");
						}
						else if(resultat.equals("Artikel existiert bereits") || resultat.equals("Nummer vergeben") || resultat.equals("Falsche Nummer")) {
							setLevel("startmenue");
						}
					
					} catch (ArtikelExistiertBereitsException e) {
						// Hier Fehlerbehandlung...
						System.out.println("Fehler beim einfügen");
						e.printStackTrace();
					}
				}
				break;
			//Artikel suchen
			case "f":
				System.out.print("Buchtitel  > ");
				name = liesEingabe();
				liste = Sitzung.bst.sucheNachName(name);
				Sitzung.gibArtikellisteUnsortiertAus(liste);
				break;
			//Artikel sichern
			case "s":
				Sitzung.wnk.warenkorbLeeren();
				
				Rechnung rechnung = new Rechnung(Sitzung.getAktuellerUser(), Sitzung.produceAenderungsListe(), false);
				System.out.println(rechnung);
				
				try {
					//sollte man so wahrscheinlich nicht machen:
					Sitzung.rch.fuegeRechnungEin(rechnung);
					
				} catch (ArtikelExistiertBereitsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Fehler beim einfügen");
					e.printStackTrace();
				}
				
				Sitzung.bst.schreibeArtikel();
				Sitzung.rch.schreibeRechnung();
				System.out.println("gespeichert.");
				break;
			//Sitzungsnummer anzeigen
			case "n":
				System.out.println("Ihre Sitzungsnummer lautet: " + Sitzung.getSitzungsNr() +"\n");
				break;
			//Alle Rechnungen anzeigen
			case "rch":
				rechnungListe = Sitzung.rch.gibAlleRechnungen();
				Sitzung.gibRechnungslisteAus(rechnungListe);
				break;
			//Userspezifische Rechnungen anzeigen
			case "rch#":
				System.out.print("Kundenummer  > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				rechnungListe = Sitzung.rch.sucheNachNr(nummer);
				Sitzung.gibRechnungslisteAus(rechnungListe);
				break;
			//Login
			case "log":
				System.out.print("Name  > ");
				name = liesEingabe();
				boolean erfolgreich = false;
				for(User u : Sitzung.usr.gibAlleUser()) {
					if(u.getName().equals(name)) {
						Sitzung.setAktuellerUser(u);
						System.out.println("Willkommen " + u.getName() + "!");
						System.out.println("Userdaten: " + Sitzung.getAktuellerUser());
						erfolgreich = true;
					}
				}
				if(!erfolgreich) {
					System.out.println("User mit dem Namen \"" + name + "\" existiert nicht in der Datenbank. Bitte registrieren Sie sich.");
				}
				break;
			//Registrieren
			case "reg":
				System.out.print("Name  > ");
				name = liesEingabe();
				nummer = 1 + Sitzung.usr.gibUserAnzahl();
				
				try {
					//sollte man so wahrscheinlich nicht machen:
					Sitzung.usr.fuegeUserEin(nummer, name);
					
					Sitzung.usr.schreibeUser();
					
					System.out.println("\n Wir freuen uns Sie als neuen Kunden begrüßen zu dürfen.");
					
					setLevel("startmenue");
					
				} catch (ArtikelExistiertBereitsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Fehler beim registrieren");
					e.printStackTrace();
					break;
				}
				break;
			//Neue Sitzung starten
			case "r":
				System.out.println("Neue Sitzung startet.\n");
				Main.main(null);
				break;
			
				}
			}
		
		else if(level.equals("speichern")) {
			switch (line) {
			case "s":
				Sitzung.wnk.warenkorbLeeren();
				
				Rechnung rechnung = new Rechnung(Sitzung.getAktuellerUser(), Sitzung.produceAenderungsListe(), false);
				System.out.println(rechnung);
				
				try {
					//sollte man so wahrscheinlich nicht machen:
					Sitzung.rch.fuegeRechnungEin(rechnung);
					
				} catch (ArtikelExistiertBereitsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Fehler beim einfügen");
					e.printStackTrace();
				}
				
				Sitzung.bst.schreibeArtikel();
				Sitzung.rch.schreibeRechnung();
				System.out.println("gespeichert.");
				
				
				setLevel("startmenue");
				break;
			case "r":
				Sitzung.bst.loescheArtikel(Sitzung.getAktuellerArtikel());
				System.out.println("Artikel nicht gespeichert.\n");
				setLevel("startmenue");
				break;
			case "w":
				setLevel("startmenue");
				Sitzung.run();
				break;
			}
		}
	}
}
