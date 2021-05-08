package eshop.local.ui;

import eshop.local.valueobjects.Artikel;

import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
//import eshop.local.ui.cui.EshopClientCUI;
import eshop.local.ui.cui.Main;
import eshop.local.ui.Menue;

public class Eingabeverarbeitung {

	private String line;
	private int level;
	private BufferedReader in;
	private Menue menue;
	
	public Eingabeverarbeitung() {
		
		setLine("");
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new Menue();
	}
	
	public Eingabeverarbeitung(String line) {
		
		setLine(line);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new Menue();
	}
	
	public Eingabeverarbeitung(String line, int level) {
		
		setLine(line);
		setLevel(level);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new Menue();
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.menue.setMenueLevel(level);
	}
	
	public void gibMenueAus() {
		this.menue.gibMenueAus();
	}
	
	public int getLevel() {
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
		
	
		if(this.level < 0) {
			this.level = 1;
		}

		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitung(String line, int level) throws IOException {
		
		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitungsLevel(String line, int level) throws IOException {
		
		String nummerString;
		int nummer = 0;
		String name;
		List<Artikel> liste;
		Artikel artikel;
		
		if(level == 1) {
			
			
			try {
				nummer = Integer.parseInt(line);
			} catch (Exception e) {}
			
			if (nummer > 0) {
				liste = Sitzung.bst.sucheNachNr(nummer);
				Sitzung.gibArtikellisteAus(liste);
				System.out.print("\n");
			}
			
			// Eingabe bearbeiten:
			switch (line) {
			//Alle Artikel anzeigen
			case "a":
				liste = Sitzung.bst.gibAlleArtikel();
				Sitzung.gibArtikellisteAus(liste);
				System.out.println("\n   Gib die Nr eines Artikels ein um damit zu interagieren.\n");
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

				try {
					Sitzung.setAktuellerArtikel(Sitzung.bst.fuegeArtikelEin(name, nummer));
					liste = Sitzung.bst.sucheNachNr(nummer);
					System.out.print("\n");
					Sitzung.gibArtikellisteAus(liste);
					System.out.print("\n");
					setLevel(2);
					
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
		else if(level == 2) {
			switch (line) {
			case "s":
				Sitzung.bst.schreibeArtikel();
				System.out.print("Artikel in Datenbank eingetragen.\n");
				System.out.print("\n");
				setLevel(1);
				break;
			case "r":
				
				Sitzung.bst.loescheArtikel(Sitzung.getAktuellerArtikel());
				System.out.println("Artikel nicht gespeichert.\n");
				setLevel(1);
				break;
			}
		}
	}
}
