package eshop.local.ui;

import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;
import eshop.local.valueobjects.Valueobject;

import eshop.local.ui.Sitzung;

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

	private String input;
	private String level;
	private BufferedReader in;
	private MenueAusgabe menue;
	
	public Eingabeverarbeitung() {
		
		setInput("");
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new MenueAusgabe();
	}
	
	public Eingabeverarbeitung(String input) {
		
		setInput(input);
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.menue = new MenueAusgabe();
	}
	
	public Eingabeverarbeitung(String input, String level) {
		
		setInput(input);
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
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public String getInput() {
		return this.input;
	}
	
	
	public void einlesenUndVerarbeiten() throws IOException {
			
		verarbeitung(liesEingabe());
		
	}
	
	public String liesEingabe() throws IOException {
		// einlesen von Konsole
		setInput(in.readLine());
		return this.input;
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
	
	public void verarbeitungsLevel(String input, String level) throws IOException {
		
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
			
			/*cool but not needed
			try {
				nummer = Integer.parseInt(input);
			} catch (Exception e) {}
			
			if (nummer > 0) {
				liste = Sitzung.bst.sucheNachNr(nummer);
				Sitzung.gibArtikellisteUnsortiertAus(liste);

			}
			*/
			
			// Eingabe bearbeiten:
			switch (input) {
			//Alle Artikel anzeigen
			case "a":
				gibListeAus(Sitzung.getArtikellisteAusBestand());
				break;
			//Alle Artikel alphabetisch sortiert anzeigen
			case "abc":
				gibListeAlphabetischAus(Sitzung.getArtikellisteAusBestand());
				break;
				//Alle Artikel alphabetisch sortiert anzeigen
			case "a#":
				gibArtikellisteNummerischAus(Sitzung.getArtikellisteAusBestand());
				break;
			//Alle Artikel im Warenkorb kaufen
			case "b":
				
				if(loginCheck() == false) {
					loginDialog();
				}
				else{
					Rechnung rechnung = Sitzung.warenkorbKaufen();
					if(rechnung.getSitzungsNr().equals("")) {
						System.out.println("Fehler beim einfügen");
					}
					else {
						System.out.println("Kauf abgeschlossen. Hier die Rechnung: \n");
						System.out.println(rechnung);
						System.out.println("\nNeue Sitzung startet.");
						Main.main(null);
					}
				}
				break;
			//Alle User anzeigen
			case "u":
				gibListeAus(Sitzung.getUserliste());
				break;
			//Warenkorb anzeigen
			case "w":
				Sitzung.gibArtikellisteUnsortiertAus(Sitzung.getArtikellisteAusWarenkorb());
				System.out.println("Gesamtpreis: " + Sitzung.getGesamtpreisWarenkorb() + " €");
				break;
			//Artikel in Warenkorb verschieben
			case "k":
				System.out.print("Artikelnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Anzahl > ");
				anzahlString = liesEingabe();
				anzahl = Integer.parseInt(anzahlString);
				Sitzung.verschiebeVonBestandInWarenkorb(nummer, anzahl);
				break;
				//Artikel von Warenkorb zurücklegen
			case "l":
				System.out.print("Artikelnummer > ");
				nummerString = liesEingabe();
				nummer = Integer.parseInt(nummerString);
				System.out.print("Anzahl > ");
				anzahlString = liesEingabe();
				anzahl = Integer.parseInt(anzahlString);
				Sitzung.verschiebeVonWarenkorbinBestand(nummer, anzahl);
				break;
			//Warenkorb leeren
			case "wl":
				Sitzung.warenkorbLeeren();
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
					Sitzung.aendereAnzahlImBestand(nummer, anzahl);
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
					Sitzung.loescheArtikelImBestand(name, nummer);
				}
				break;
			//Artikel einfügen
			case "e":
				if(loginCheck() == true) {
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
					boolean a = false;
					while(a == false) {
						System.out.print("Preis > ");
						preisString = liesEingabe();
						try {
							preis = Double.parseDouble(preisString);
							a = true;
						} catch (NumberFormatException e) {
							System.out.println("Falsches Format! \n Format für Preis ist integer.integer \n Try again...");
						}
					}

					try {
						//sollte man so wahrscheinlich nicht machen:
						String resultat = Sitzung.bst.fuegeArtikelEin(name, nummer, anzahl, preis, packet);
					
						if(resultat.equals("Erfolgreich hinzugefügt")) {
							Sitzung.setAktuellerArtikel(new Massenartikel(name, nummer, anzahl, preis, packet));
							System.out.print("\n");
							System.out.println(Sitzung.getAktuellerArtikel());
							setLevel("startmenue");
						}
						else if(resultat.equals("Artikel existiert bereits") || resultat.equals("Nummer vergeben") || resultat.equals("Falsche Nummer")) {
							setLevel("startmenu"); //vorher "speichern"
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
				Sitzung.warenkorbLeeren();
				
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
				Sitzung.usr.schreibeUser();
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
			switch (input) {
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
	
	public boolean loginCheck() {
		if(Sitzung.getAktuellerUser().getUserNr() == 0) {
			System.out.println("Sie sind nicht eingeloggt.");
			return false;
		} else return true;
		
	}
	
	public boolean loginDialog() {
		return true;
	}
	
	///Sitzung.getAktuellerUser().getUserNr() == 0
	
	public <Thing> void gibListeAus(List<Thing> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Thing thing : liste) {
				System.out.println(thing);
			}
		}
		
	}
	
	public <Thing extends Valueobject> void gibListeAlphabetischAus(List<Thing> vectorListe){
		
		int anzahl = vectorListe.size();
		List<Thing> tempListe = new Vector<Thing>();

		//Sortieren
		for(int loop = 0; loop <= anzahl; loop++) {

			Thing tempObject = (Thing) new Artikel(); //Ob Rechnung, Artikel oder User ist hoffentlich egal.
						
			try {
				Class<?> cls = Class.forName(vectorListe.get(0).getClass().getName());
				Thing tempClass = (Thing) cls.getConstructor().newInstance();
				tempObject = tempClass;
				tempObject.copy(vectorListe.get(0));
				tempObject.setName("");
			} catch (Exception e) {}
			
			for (Thing thing : vectorListe) {
				if(tempObject.getName().isEmpty()) {
					tempObject.copy(thing);
				}
				//deep copy, not just pointer:
				int vergleichen = thing.getName().toLowerCase().compareTo(tempObject.getName().toLowerCase());
				if(vergleichen <= 0){
					tempObject.copy(thing);
				}
			}
			if(!tempObject.getName().isEmpty()) {
				tempListe.add(tempObject);
			}
			vectorListe.remove(tempObject);
		}
		//Sortierte Liste ausgeben
		for (Thing thing : tempListe) {
			System.out.println(thing);
		}
	}
	
	public <Thing extends Valueobject> void gibArtikellisteNummerischAus(List<Thing> vectorListe) {

		int anzahl = vectorListe.size();
		List<Thing> tempListe = new Vector<Thing>();

		//Sortieren
		for(int loop = 0; loop <= anzahl; loop++) {
			
			Thing tempObject = (Thing) new Artikel(); //Ob Rechnung, Artikel oder User ist hoffentlich egal.
			
			try {
				Class<?> cls = Class.forName(vectorListe.get(0).getClass().getName());
				Thing tempClass = (Thing) cls.getConstructor().newInstance();
				tempObject = tempClass;
				tempObject.copy(vectorListe.get(0));
				tempObject.setName("");
			} catch (Exception e) {}
			
			for (Thing thing : vectorListe) {
				if(tempObject.getNummer() == 0) {
					//pointer sufficient but why not make a deep copy
					tempObject.copy(thing);
				}
				if(thing.getNummer() <= tempObject.getNummer()){
					//deep copy necessary
					tempObject.copy(thing);
				}
			}
			if(!tempObject.getName().isEmpty()) {
				tempListe.add(tempObject);
			}
			vectorListe.remove(tempObject);
		}
		//Sortierte Liste ausgeben
		for (Thing thing : tempListe) {
			System.out.println(thing);
		}

	}
}
