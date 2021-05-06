package eshop.local.ui.cui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import eshop.local.domain.Bestand;
import eshop.local.valueobjects.Kunde;
import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.valueobjects.Artikel;

public class EshopClientCUI {
	
	private Bestand bst;
	private BufferedReader in;
	private Kunde kunde;
	
	public EshopClientCUI(String datei) throws IOException {
		// die Bst-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		bst = new Bestand(datei);

		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		in = new BufferedReader(new InputStreamReader(System.in));
		
		// Jede "Sitzung", ob angemeldet oder nicht, erzeugt ein Kunden-Objekt. Zuerst aber nur mit "sitzungsNr" und wenigen Rechten.
		kunde = new Kunde();
	}
	
	public static void main(String[] args) {
		EshopClientCUI cui;
		try {
			cui = new EshopClientCUI("BST");
			cui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


public void run() {
		
	// Variable für Eingaben von der Konsole
	String input = ""; 

	// Hauptschleife der Benutzungsschnittstelle
	do {
		gibMenueAus();
		try {
			input = liesEingabe();
			verarbeiteEingabe(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} while (!input.equals("q"));
}

private String liesEingabe() throws IOException {
	// einlesen von Konsole
	return in.readLine();
}


private void verarbeiteEingabe(String line) throws IOException {
	
	String nummer;
	int nr;
	String name;
	List<Artikel> liste;
	int artikelNr = 0;
	
	try {
		artikelNr = Integer.parseInt(line);
	} catch (Exception e) {}
	
	if (artikelNr > 0) {
		liste = bst.sucheNachNr(artikelNr);
		gibArtikellisteAus(liste);
		System.out.print("\n");
	}
	
	// Eingabe bearbeiten:
	switch (line) {
	case "a":
		liste = bst.gibAlleArtikel();
		gibArtikellisteAus(liste);
		System.out.println("\n   Gib die Nr eines Artikels ein um damit zu interagieren.\n");
		break;
	case "d":
		// lies die notwendigen Parameter, einzeln pro Zeile
		System.out.print("Buchnummer > ");
		nummer = liesEingabe();
		nr = Integer.parseInt(nummer);
		System.out.print("Buchtitel  > ");
		name = liesEingabe();
		// Die Bibliothek das Buch löschen lassen:
		bst.loescheArtikel(name, nr);
		break;
	case "e":
		// lies die notwendigen Parameter, einzeln pro Zeile
		System.out.print("Buchnummer > ");
		nummer = liesEingabe();
		nr = Integer.parseInt(nummer);
		System.out.print("Buchtitel  > ");
		name = liesEingabe();

		try {
			bst.fuegeArtikelEin(name, nr);
			System.out.println("Einfügen ok");
		} catch (ArtikelExistiertBereitsException e) {
			// Hier Fehlerbehandlung...
			System.out.println("Fehler beim Einfügen");
			e.printStackTrace();
		}
		break;
	case "f":
		System.out.print("Buchtitel  > ");
		name = liesEingabe();
		liste = bst.sucheNachName(name);
		gibArtikellisteAus(liste);
		break;
	case "s":
		bst.schreibeArtikel();
		break;
	case "n":
		System.out.println("Ihre Sitzungsnummer lautet: " + kunde.getSitzungsNr() +"\n");
		break;
	case "r":
		System.out.println("Neue Sitzung startet.\n");
		main(null);
		break;
	}
}
	
	private void gibMenueAus() {
		System.out.print("Befehle: \n  (a) Alle Artikel anzeigen");
		System.out.print("         \n  (d) Artikel löschen");
		System.out.print("         \n  (e) Artikel einfügen");
		System.out.print("         \n  (f) Artikel suchen");
		System.out.print("         \n  (s) Artikel sichern");
		System.out.print("         \n  (n) Sitzungsnummer anzeigen");
		System.out.print("         \n  ---------------------");
		System.out.print("         \n  (r) Neue Sitzung starten");
		System.out.println("         \n  (q) Beenden");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	private void gibArtikellisteAus(List<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Artikel artikel : liste) {
				System.out.println(artikel);
			}
		}
	}
}