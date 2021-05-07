package eshop.local.ui;

public class Menue {
	
	private int menueLevel;
	
	public Menue() {
		setMenueLevel(1);
	}
	
	public Menue(int menueLevel) {
		setMenueLevel(menueLevel);
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
		//Standard- und Initial-Menü
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
			
		case 2:
			System.out.print("Artikel wie angezeigt abspeichern oder rückgängig machen?\n");
			System.out.print("(s) speichern\n");
			System.out.print("(r) rückgängig machen\n");
			break;
		}
			
		
	}
		
}
