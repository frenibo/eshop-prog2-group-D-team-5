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
			System.out.println("Befehle:");
			System.out.println("(a) Alle Artikel anzeigen");
			System.out.println("(abc) Alle Artikel alphabetisch sortiert anzeigen");
			System.out.println("(a#) Alle Artikel nummerisch sortiert anzeigen");
			System.out.println("(b) Alle Artikel im Warenkorb kaufen");
			System.out.println("(u) Alle User anzeigen");
			System.out.println("(w) Warenkorb anzeigen");
			System.out.println("(k) Artikel in Warenkorb verschieben");
			System.out.println("(l) Artikel von Warenkorb zurücklegen");
			System.out.println("(wl) Warenkorb leeren");
			System.out.println("(v) Artikelanzahl ändern");
			System.out.println("(d) Artikel löschen");
			System.out.println("(e) Artikel einfügen");
			System.out.println("(f) Artikel suchen");
			System.out.println("(s) Artikel sichern");
			System.out.println("(n) Sitzungsnummer anzeigen");
			System.out.println("(rch) Alle Rechnungen anzeigen");
			System.out.println("(rch#) Userspezifische Rechnungen anzeigen");
			System.out.println("(log) Login");
			System.out.println("(reg) Registrieren");
			System.out.println("(r) Neue Sitzung starten");
			System.out.println("---------------------");
			System.out.println("(z) Zurück zum Menü");
			System.out.println("(q) Beenden");
			System.out.print("> "); // Prompt
			System.out.flush(); // ohne NL ausgeben
			break;
			
		case "speichern":
			System.out.println("\nArtikel wie angezeigt abspeichern oder rückgängig machen?");
			System.out.println("(s) speichern");
			System.out.println("(r) rückgängig machen");
			System.out.println("(w) weiter ohne zu speichern");
			System.out.print("> ");
			break;
		}
			
		
	}
		
}
