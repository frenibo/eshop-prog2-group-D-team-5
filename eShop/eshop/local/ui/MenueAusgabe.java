package eshop.local.ui;

public class MenueAusgabe {
	
	private String menueLevel;
	
	public MenueAusgabe() {
		setMenueLevel("startmenue");
	}
	
	public MenueAusgabe(String menueLevel) {
		setMenueLevel(menueLevel);
	}
	
	public String getMenueLevel() {
		return menueLevel;
	}
	
	public void setMenueLevel(String menueLevel) {
		this.menueLevel = menueLevel;
		
	}

	public void gibMenueAus() {
		menueAuswahl(this.menueLevel);
	}
	
	public void gibMenueAus(String menueLevel) {
		menueAuswahl(menueLevel);
	}
	
	public void menueAuswahl(String menueLevel) {
		
		switch(menueLevel) {
		//Standard- und Initial-Menü
		case "startmenue":
			System.out.println("\n");
			System.out.print("Befehle: \n  (a) Alle Artikel anzeigen");
			System.out.print("         \n  (abc) Alle Artikel alphabetisch sortiert anzeigen");
			System.out.print("         \n  (a#) Alle Artikel nummerisch sortiert anzeigen");
			System.out.print("         \n  (b) Alle Artikel im Warenkorb kaufen");
			System.out.print("         \n  (u) Alle User anzeigen");
			System.out.print("         \n  (w) Warenkorb anzeigen");
			System.out.print("         \n  (k) Artikel in Warenkorb verschieben");
			System.out.print("         \n  (l) Artikel von Warenkorb zurücklegen");
			System.out.print("         \n  (wl) Warenkorb leeren");
			System.out.print("         \n  (v) Artikelanzahl ändern");
			System.out.print("         \n  (d) Artikel löschen");
			System.out.print("         \n  (e) Artikel einfügen");
			System.out.print("         \n  (f) Artikel suchen");
			System.out.print("         \n  (s) Artikel sichern");
			System.out.print("         \n  (n) Sitzungsnummer anzeigen");
			System.out.print("         \n  (rch) Alle Rechnungen anzeigen");
			System.out.print("         \n  (rch#) Userspezifische Rechnungen anzeigen");
			System.out.print("         \n  (log) Login");
			System.out.print("         \n  (reg) Registrieren");
			System.out.print("         \n  ---------------------");
			System.out.print("         \n  (r) Neue Sitzung starten");
			System.out.println("         \n  (q) Beenden");
			System.out.print("> "); // Prompt
			System.out.flush(); // ohne NL ausgeben
			break;
			
		case "speichern":
			System.out.print("\nArtikel wie angezeigt abspeichern oder rückgängig machen?\n");
			System.out.print("(s) speichern\n");
			System.out.print("(r) rückgängig machen\n");
			System.out.print("(w) weiter ohne zu speichern\n");
			break;
		}
			
		
	}
		
}
