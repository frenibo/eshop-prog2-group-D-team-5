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
	
	private User user = new User();
	private List<Artikel> artikelListe = new Vector<Artikel>();
	private String sitzungsNr;
	private String datum;
	boolean buchung = false;
	boolean kauf = false;
	double gesamtpreis;
	
	public Rechnung(User user, List<Artikel> artikelListe, boolean buchungOderKauf) {
		
		this.user = user;
		this.artikelListe = artikelListe;
		this.sitzungsNr = Sitzung.getSitzungsNr();
		this.datum = defineDatum();
		if(buchungOderKauf) {
			this.kauf = true;
		} else this.buchung = true;
		this.gesamtpreis = defineGesamtpreis();
		
	}
	
	public Rechnung(User user, List<Artikel> artikelListe, boolean buchungOderKauf, String sitzungsNr, String datum, double gesamtpreis) {
		
		this.user = user;
		this.artikelListe = artikelListe;
		this.sitzungsNr = sitzungsNr;
		this.datum = datum;
		if(buchungOderKauf) {
			this.kauf = true;
		} else this.buchung = true;
		this.gesamtpreis = gesamtpreis;
		
	}
	
	public String toString() {
		//TODO: Artikel mit String.format spaltenweise ausgeben.
		//String artikelString = String.format("%5s);
		String artikelListeString = "";
		for (Artikel artikel : artikelListe) {
			artikelListeString += artikel + "\n";
		}
		String buchungOderKaufString = "";
		if(buchung) {
			buchungOderKaufString = "Buchung abgeschlossen.\n\n";
		} else buchungOderKaufString = "Kauf abgeschlossen. Hier die Rechnung:\n\n";
		
		return (buchungOderKaufString + "" + artikelListeString + "Gesamtpreis: " + getGesamtpreis() + " €\nKundendaten: " + getUser() + "\nGekauft am " + datum + "\nSitzungsnummer: " + sitzungsNr + "\n");
	}
	
	public void gibRechnungAus() {
		//Artikelliste
		System.out.println("Artikelliste: ");
		for (Artikel artikel : artikelListe) {
			System.out.println(artikel);
		}
		//Gesamtpreis
		System.out.println("Gesamtpreis: " + getGesamtpreis() + " €");
		
		System.out.println("Kundendaten: [" + getUser() + "]");
		
		System.out.println("Gekauft am " + datum);
		
		System.out.println("Sitzungsnummer: " + sitzungsNr);
	}
	
	public void gibArtikellisteAus() {
		for (Artikel artikel : artikelListe) {
			System.out.println(artikel);
		}		
	}
	
	public List<Artikel> getArtikelListe() {
		
		return artikelListe;
	}
	
	public int getArtikelAnzahl() {
		int anzahl = 0;
		for(Artikel a : artikelListe) {
			anzahl++;
		}
		return anzahl;
	}

	public User getUser() {
		return user;
	}
	
	public double defineGesamtpreis() {
		double gesamtpreis = 0.00;
		for(Artikel artikel : artikelListe) {
			double produkt = artikel.getPreis() * artikel.getAnzahl();
			gesamtpreis += produkt;
		}
		return gesamtpreis;
	}
	
	public double getGesamtpreis() {
		return gesamtpreis;
	}
	
	public String defineDatum() {
		
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String datum = formatter.format(objDate);
		return datum;
		
	}
	
	public String getDatum() {
		
		return this.datum;
		
	}
	
	public String getSitzungsNr() {
		
		return this.sitzungsNr;
	}	
	
	public boolean getKauf() {
		
		return this.kauf;
	}
	

}