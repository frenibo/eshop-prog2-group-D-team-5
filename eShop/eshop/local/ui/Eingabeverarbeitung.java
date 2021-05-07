package eshop.local.ui;

import eshop.local.valueobjects.Artikel;

import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.cui.EshopClientCUI;

public class Eingabeverarbeitung {

	private String line;
	private int level;
	private BufferedReader in;
	
	
	public Eingabeverarbeitung() {
		
		setLine("");
		setLevel(1);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public Eingabeverarbeitung(String line) {
		
		setLine(line);
		setLevel(1);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public Eingabeverarbeitung(String line, int level) {
		
		setLine(line);
		setLevel(level);
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void setLevel(int level) {
		this.level = level;
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
	
	public String liesEingabe() throws IOException {
		// einlesen von Konsole
		setLine(in.readLine());
		return this.line;
	}
	
	public void verarbeitung(String line) throws IOException {
		
		int level = this.level;
		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitung(String line, int level) throws IOException {
		
		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitungsLevel(String line, int level) throws IOException {
		
		String nummerString;
		int nummer;
		String name;
		List<Artikel> liste;
		int artikelNr = 0;
		
		if(level == 1) {
			
			
			try {
				artikelNr = Integer.parseInt(line);
			} catch (Exception e) {}
			
			if (artikelNr > 0) {
				liste = EshopClientCUI.bst.sucheNachNr(artikelNr);
				EshopClientCUI.gibArtikellisteAus(liste);
				System.out.print("\n");
			}
			
			// Eingabe bearbeiten:
			switch (line) {
			//Alle Artikel anzeigen
			case "a":
				liste = EshopClientCUI.bst.gibAlleArtikel();
				EshopClientCUI.gibArtikellisteAus(liste);
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
				EshopClientCUI.bst.loescheArtikel(name, nummer);
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
					EshopClientCUI.bst.fuegeArtikelEin(name, nummer);
					liste = EshopClientCUI.bst.sucheNachNr(nummer);
					System.out.print("\n");
					EshopClientCUI.gibArtikellisteAus(liste);
					System.out.print("\n");
					EshopClientCUI.setLevel(2);
					
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
				liste = EshopClientCUI.bst.sucheNachName(name);
				EshopClientCUI.gibArtikellisteAus(liste);
				break;
			//Artikel sichern
			case "s":
				EshopClientCUI.bst.schreibeArtikel();
				break;
			//Sitzungsnummer anzeigen
			case "n":
				System.out.println("Ihre Sitzungsnummer lautet: " + EshopClientCUI.user.getSitzungsNr() +"\n");
				break;
			case "r":
				System.out.println("Neue Sitzung startet.\n");
				EshopClientCUI.main(null);
				break;
			}
	
		
		}
		else if(level == 2) {
			switch (line) {
			case "s":
				EshopClientCUI.bst.schreibeArtikel();
				System.out.print("Artikel in Datenbank eingetragen.\n");
				System.out.print("\n");
				EshopClientCUI.setLevel(1);
				break;
			case "r":
				break;
			}
		}
	}
}
