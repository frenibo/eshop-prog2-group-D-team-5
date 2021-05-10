package eshop.local.ui;

import eshop.local.valueobjects.Artikel;

import java.util.List;

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
		
		String nummerString = "";
		int nummer = 0;
		String anzahlString = "";
		int anzahl = 0;
		String name = "";
		String preisString = "";
		double preis = 0.0;
		
		if(level.equals("startmenue")) {
			
			
			try {
				nummer = Integer.parseInt(line);
			} catch (Exception e) {}
			
			if (nummer > 0) {
				liste = Sitzung.bst.sucheNachNr(nummer);
				Sitzung.gibArtikellisteAus(liste);
			}
			
			// Eingabe bearbeiten:
			switch (line) {
			//Alle Artikel anzeigen
			case "a":
				liste = Sitzung.bst.gibAlleArtikel();
				Sitzung.gibArtikellisteAus(liste);
				System.out.println("\n   Gib die Nr eines Artikels ein um damit zu interagieren.");
				break;
			//Warenkorb anzeigen
			case "w":
				liste = Sitzung.wnk.gibAlleArtikel();
				Sitzung.gibArtikellisteAus(liste);
				System.out.println("Gesamtpreis: " + Sitzung.gibGesamtpreisAus(liste) + " €");
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
				System.out.print("Artikelnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Neue Anzahl > ");
				anzahlString = liesEingabe();
				anzahl = Integer.parseInt(anzahlString);
				Sitzung.bst.aendereAnzahl(nummer, anzahl);
				System.out.print("\nAnzahl aktualisiert.");
				break;
			//Artikel löschen
			case "d":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Buchnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Buchtitel  > ");
				name = liesEingabe();
				// Die Bibliothek das Buch löschen lassen:
				Sitzung.bst.loescheArtikel(name, nummer);
				break;
			//Artikel einfügen
			case "e":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Artikelnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Name des Artikels  > ");
				name = liesEingabe();
				System.out.print("Anzahl > ");
				anzahlString = liesEingabe();
				anzahl = Integer.parseInt(anzahlString);
				System.out.print("Preis > ");
				preisString = liesEingabe();
				preis = Double.parseDouble(preisString);

				try {
					//sollte man so wahrscheinlich nicht machen:
					String resultat = Sitzung.bst.fuegeArtikelEin(name, nummer, anzahl, preis);
					
					if(resultat.equals("Erfolgreich hinzugefügt")) {
						Sitzung.setAktuellerArtikel(new Artikel(name, nummer, anzahl, preis));
						System.out.print("\n");
						System.out.println(Sitzung.getAktuellerArtikel());
						setLevel("speichern");
					}
					else if(resultat.equals("Artikel existiert bereits")) {
						setLevel("startmenue");
					}
					else if(resultat.equals("Nummer vergeben")) {
						setLevel("startmenue");
					}
					
				} catch (ArtikelExistiertBereitsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Fehler beim Einfügen");
					e.printStackTrace();
				}
				break;
			//Artikel suchen
			case "f":
				System.out.print("Buchtitel  > ");
				name = liesEingabe();
				liste = Sitzung.bst.sucheNachName(name);
				Sitzung.gibArtikellisteAus(liste);
				break;
			//Artikel sichern
			case "s":
				Sitzung.bst.schreibeArtikel();
				Sitzung.wnk.schreibeArtikel();
				System.out.println("gespeichert.");
				break;
			//Sitzungsnummer anzeigen
			case "n":
				System.out.println("Ihre Sitzungsnummer lautet: " + Sitzung.getSitzungsNr() +"\n");
				break;
			case "r":
				System.out.println("Neue Sitzung startet.\n");
				Main.main(null);
				break;
			}
	
		
		}
		else if(level.equals("speichern")) {
			switch (line) {
			case "s":
				Sitzung.bst.schreibeArtikel();
				System.out.print("Artikel in Datenbank eingetragen.\n");
				System.out.print("\n");
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
