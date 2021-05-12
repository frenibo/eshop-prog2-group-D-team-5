package eshop.local.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import eshop.local.ui.Sitzung;
import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.User;

/*
 * Das Rechnungsobjekt beinhaltet die gekauften Artikel und den Kunden und es kann 
 * im CUI ausgegeben werden.
 */

public class Rechnung {
	
	private List<User> kunde = new Vector<User>();
	private List<Artikel> warenkorb = new Vector<Artikel>();
	//private date Datum;
	private String sitzungsNr;
	private String datum;
	
	public Rechnung(List<User> kunde, List<Artikel> warenkorb) {
		
		this.kunde = kunde;
		this.warenkorb = warenkorb;
		this.sitzungsNr = Sitzung.getSitzungsNr();
		this.datum = getDatum();
		
	}
	
	public void gibRechnungAus() {
		//Artikelliste
		gibArtikellisteAus();
		//Gesamtpreis
		System.out.println("Gesamtpreis: " + getGesamtpreis() + " €");
		
		gibKundeAus();
		
		System.out.println("Gekauft am " + datum);
	}
	
	public void gibArtikellisteAus() {
		for (Artikel artikel : warenkorb) {
			System.out.println(artikel);
		}		
	}
	
	public void gibKundeAus() {
		for (User kunde : kunde) {
			System.out.println("Kunde: " + kunde);
		}	
	}
	
	public double getGesamtpreis() {
		double gesamtpreis = 0.00;
		for(Artikel artikel : warenkorb) {
			double produkt = artikel.getPreis() * artikel.getAnzahl();
			gesamtpreis += produkt;
		}
		return gesamtpreis;
	}
	
	public String getDatum() {
		
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String datum = formatter.format(objDate);
		return datum;
		
	}

	/*
	public List<User> getKunde {
		return kunde;
	}
	
	public List<Artikel> getWarenkorb {
		return warenkorb;
	}
	*/
}
