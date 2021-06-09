package eshop.local.ui;

import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Inputevent;
import eshop.local.valueobjects.Lagerungsevent;
import eshop.local.valueobjects.Massenartikel;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;
import eshop.local.valueobjects.Valueobject;

import eshop.local.ui.eShop;

import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
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
	
	
	public void einlesenUndVerarbeiten() throws IOException, ArtikelExistiertBereitsException {
			
		verarbeitung(liesEingabe());
				
	}
	
	public String liesEingabe() throws IOException, ArtikelExistiertBereitsException {
		// einlesen von Konsole
		setInput(in.readLine());
		newEvent();
		liesUnterbrechung();
		return this.input;
	}
	
	public void liesUnterbrechung() throws IOException, ArtikelExistiertBereitsException {
		if(this.input.equals("q")) {
			//eShop.setRun(false);
			System.out.println("> (q) Beenden");
			System.exit(0);
		}
		else if(this.input.equals("z")) {
			System.out.println("> (z) Zurück zum Menü");
			verarbeitungsLevel("", "startmenue");
			eShop.run();
		}
	}
	
	public void newEvent() {
		Inputevent inputEvent = new Inputevent(this.input);
		try {
			eShop.fuegeInputeventEin(inputEvent);
			eShop.speichereEventliste();
		}
		catch (Exception e) {
			System.out.println("Input nicht geloggt!");
		}
	}
	
	public void verarbeitung(String line) throws IOException, ArtikelExistiertBereitsException {
		if(this.level.isEmpty()) {
			this.level = "startmenue";
		}

		verarbeitungsLevel(line, this.level);
	}
	
	public void verarbeitung(String line, String level) throws IOException, ArtikelExistiertBereitsException {
		
		verarbeitungsLevel(line, level);
	}
	
	public void verarbeitungsLevel(String input, String level) throws IOException, ArtikelExistiertBereitsException {
		
		List<Artikel> liste;
		List<User> userListe;
		List<Rechnung> rechnungListe;
		List<Lagerungsevent> lagerungseventListe;
		
		int nummer = 0;
		int anzahl = 0;
		int packet = 0;
		String name = "";
		double preis = 0.0;
		boolean bool = false;
		
		if(level.equals("startmenue")) {
			
			/*cool but not needed
			try {
				nummer = Integer.parseInt(input);
			} catch (Exception e) {}
			
			if (nummer > 0) {
				liste = eShop.bst.sucheNachNr(nummer);
				eShop.gibArtikellisteUnsortiertAus(liste);

			}
			*/
			
			// Eingabe bearbeiten:
			switch (input) {
			//Alle Artikel anzeigen
			case "a":
				gibListeAus(eShop.getArtikellisteAusBestand());
				break;
			//Alle Artikel alphabetisch sortiert anzeigen
			case "abc":
				gibListeAlphabetischAus(eShop.getArtikellisteAusBestand());
				break;
				//Alle Artikel alphabetisch sortiert anzeigen
			case "a#":
				gibListeNummerischAus(eShop.getArtikellisteAusBestand());
				break;
			//Alle Artikel im Warenkorb kaufen
			case "b":
				
				if(loginCheck() == true) {
					bool = eShop.warenkorbKaufen();
					if(bool == false) {
						System.out.println("Fehler beim einfügen");
					}
					else {
						System.out.println("Kauf abgeschlossen. Hier die Rechnung: \n");
						System.out.println(eShop.getAktuelleRechnung());
					}
				}
				break;
			//Alle User anzeigen
			case "u":
				gibListeAus(eShop.getUserliste());
				break;
			//Warenkorb anzeigen
			case "w":
				eShop.gibArtikellisteUnsortiertAus(eShop.getArtikellisteAusWarenkorb());
				System.out.println("Gesamtpreis: " + eShop.getGesamtpreisWarenkorb() + " €");
				break;
			//Artikel in Warenkorb verschieben
			case "k":
				nummer = erfahreInt("Artikelnummer");
				anzahl = erfahreInt("Anzahl");
				eShop.verschiebeVonBestandInWarenkorb(nummer, anzahl);
				break;
				//Artikel von Warenkorb zurücklegen
			case "l":
				nummer = erfahreInt("Artikelnummer");
				anzahl = erfahreInt("Anzahl");
				eShop.verschiebeVonWarenkorbinBestand(nummer, anzahl);
				break;
			//Warenkorb leeren
			case "wl":
				eShop.warenkorbLeeren();
				System.out.println("Warenkorb geleert.");
				break;
			//Artikelanzahl ändern
			case "v":
				if(loginCheck() == true) {
					nummer = erfahreInt("Artikelnummer");
					anzahl = erfahreInt("Neue Anzahl");
					eShop.aendereAnzahlImBestand(nummer, anzahl);
					System.out.print("\nAnzahl aktualisiert.");
				}
				break;
				
			//Artikel löschen
			case "d":
				if(loginCheck() == true) {
					nummer = erfahreInt("Artikelnummer");
					liste = eShop.DurchsucheBestandNachNummer(nummer);
					for(Artikel a : liste) {
						eShop.loescheArtikelImBestand(a);
					}
					
				}
				break;
			//Artikel einfügen
			case "e":
				if(loginCheck() == true) {
					// lies die notwendigen Parameter, einzeln pro Zeile
					nummer = erfahreInt("Artikelnummer");
					name = erfahreString("Name des Artikels");
					packet = erfahreInt("Packetgröße");
					anzahl = erfahreInt("Anzahl");
					preis = erfahreDouble("Preis");
					}

					try {
						//sollte man so wahrscheinlich nicht machen:
						String resultat = eShop.fuegeArtikelEin(name, nummer, anzahl, preis, packet);
					
						if(resultat.equals("Erfolgreich hinzugefügt")) {
							eShop.setAktuellerArtikel(new Massenartikel(name, nummer, anzahl, preis, packet));
							System.out.print("\n");
							System.out.println(eShop.getAktuellerArtikel());
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
				break;
			//Artikel suchen
			case "f":
				name = erfahreString("Name des Artikels");
				liste = eShop.DurchsucheBestandNachName(name);
				eShop.gibArtikellisteUnsortiertAus(liste);
				break;
			//Artikel sichern
			case "s":
				eShop.warenkorbLeeren();
				
				for(Lagerungsevent l : eShop.getNeueLagerungen()) {
					System.out.println(l);
				}
				
				eShop.speichern();
				System.out.println("gespeichert.");
				break;
			//Sitzungsnummer anzeigen
			case "n":
				System.out.println("Ihre Sitzungsnummer lautet: " + eShop.getSitzungsNr() +"\n");
				break;
			case "lag":
				lagerungseventListe = eShop.getLagerungseventListe();
				gibListeAus(lagerungseventListe);
				break;
			//Alle Rechnungen anzeigen
			case "rch":
				rechnungListe = eShop.getRechnungsliste();
				gibListeAus(rechnungListe);
				break;
			//Userspezifische Rechnungen anzeigen
			case "rch#":
				nummer = erfahreInt("Kundenummer");
				rechnungListe = eShop.DurchsucheRechnungslisteNachNr(nummer);
				gibListeAus(rechnungListe);
				break;
			//Login
			case "log":
				name = erfahreString("Name");
				boolean erfolgreich = false;
				for(User u : eShop.getUserliste()) {
					if(u.getName().equals(name)) {
						eShop.setAktuellerUser(u);
						System.out.println("Willkommen " + u.getName() + "!");
						System.out.println("Userdaten: " + eShop.getAktuellerUser());
						erfolgreich = true;
					}
				}
				if(!erfolgreich) {
					System.out.println("User mit dem Namen \"" + name + "\" existiert nicht in der Datenbank. Bitte registrieren Sie sich.");
				}
				break;
			//Registrieren
			case "reg":
				name = erfahreString("Name");
				nummer = 1 + eShop.getUserAnzahl();
				
				try {
					//sollte man so wahrscheinlich nicht machen:
					eShop.fuegeUserEin(nummer, name);
					
					eShop.speichereUserliste();
					
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
			//Zurück zum Startmenue
			case "z":
				//handled in Sitzung.run();
				break;
		
			}
		}
		
		else if(level.equals("speichern")) {
			switch (input) {
			case "s":
				eShop.speichern();
				System.out.println("gespeichert.");
				
				setLevel("startmenue");
				break;
			case "r":
				eShop.loescheArtikel(eShop.getAktuellerArtikel());
				System.out.println("Artikel nicht gespeichert.\n");
				setLevel("startmenue");
				break;
			case "w":
				setLevel("startmenue");
				eShop.run();
				break;
			}
		}
	}
	
	public int erfahreInt(String output) throws IOException, ArtikelExistiertBereitsException {
		
		boolean valid = false;
		int input = 0;
		String inputString = "";
		
		while(!valid) {
			
			System.out.print(output + " > ");
			
			inputString = liesEingabe();
			
			try {
				input = Integer.parseInt(inputString);
				valid = true;
			}
			catch(NumberFormatException n) {
				System.out.println("invalid input");
				valid = false;
			}
		}
		return input;
	}
	
	public double erfahreDouble(String output) throws IOException, ArtikelExistiertBereitsException {
		
		boolean valid = false;
		double input = 0.0;
		String inputString = "";
		
		while(!valid) {
			
			System.out.print(output + " > ");
			
			inputString = liesEingabe();
			
			try {
				input = Double.parseDouble(inputString);
				valid = true;
			}
			catch(NumberFormatException n) {
				System.out.println("invalid input");
				valid = false;
			}
		}
		return input;
	}
	
	public String erfahreString(String output) throws IOException, ArtikelExistiertBereitsException {
		
		boolean valid = false;
		String input = "";
		String inputString = "";
		
		while(!valid) {
			
			System.out.print(output + " > ");
			
			inputString = liesEingabe();
			
			try {
				input = inputString;
				valid = true;
			}
			catch(NumberFormatException n) {
				System.out.println("invalid input");
				valid = false;
			}
		}
		return input;
	}
	
	public int parseStringToInteger(String string) throws IOException, ArtikelExistiertBereitsException {
		
		int integer = 0;
		
		try {
			integer = Integer.parseInt(string);
		}
		catch(NumberFormatException n) {
			System.out.println("invalid input");
			verarbeitungsLevel("", "startmenue");
		}
		return integer;
	}
	
	public double parseStringToDouble(String string) throws IOException, ArtikelExistiertBereitsException {
		
		double doubleV = 0;
		
		try {
			doubleV = Double.parseDouble(string);
		}
		catch(NumberFormatException n) {
			System.out.println("invalid input");
			verarbeitungsLevel("", "startmenue");
		}
		return doubleV;
	}
	
	public boolean loginCheck() {
		if(eShop.getAktuellerUser().getUserNr() == 0) {
			System.out.println("Sie sind nicht eingeloggt.");
			return false;
		} else return true;
		
	}
	
	public boolean loginDialog() {
		return true;
		//TODO:loginDialog()
	}
	
	///eShop.getAktuellerUser().getUserNr() == 0
	
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
	
	public <Thing extends Valueobject> void gibListeNummerischAus(List<Thing> vectorListe) {

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
