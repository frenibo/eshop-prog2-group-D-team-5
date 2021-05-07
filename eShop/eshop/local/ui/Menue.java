package eshop.local.ui;

public class Menue {
	
	int menueLevel;
	
	public Menue() {
		this.menueLevel = 1;
	}
	
	public Menue(int menueLevel) {
		this.menueLevel = menueLevel;
	}
	
	public int getMenueLevel() {
		return menueLevel;
	}
	
	public void setMenueLevel(int menueLevel) {
		this.menueLevel = menueLevel;
	}

	public void gibMenueAus() {
		menueAuswahl(this.menueLevel);
	}
	
	public void gibMenueAus(int menueLevel) {
		menueAuswahl(menueLevel);
	}
	
	public void menueAuswahl(int menueLevel) {
		
		switch(menueLevel) {
		
		case 1:
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
			break;
		}
			
		
		}
		
}
