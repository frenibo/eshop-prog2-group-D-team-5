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

public class Rechnung implements Valueobject{
	
	private User user = new User();
	private List<Artikel> artikelListe = new Vector<Artikel>();
	private String sitzungsNr = "";
	private String datum = "";
	double gesamtpreis = 0.0;
	
	public Rechnung() {
		
		this.user = new User();
		this.artikelListe = new Vector<Artikel>();
		this.sitzungsNr = "";
		this.datum = "";
		this.gesamtpreis = 0.0;
		
	}
	
	public Rechnung(User user, List<Artikel> artikelListe) {
		
		this.user = user;
		this.artikelListe = artikelListe;
		this.sitzungsNr = Sitzung.getSitzungsNr();
		this.datum = defineDatum();
		this.gesamtpreis = defineGesamtpreis();
		
	}
	
	public Rechnung(User user, List<Artikel> artikelListe, String sitzungsNr, String datum, double gesamtpreis) {
		
		this.user = user;
		this.artikelListe = artikelListe;
		this.sitzungsNr = sitzungsNr;
		this.datum = datum;
		this.gesamtpreis = gesamtpreis;
		
	}
	
	public String toString() {
		//TODO: Artikel mit String.format spaltenweise ausgeben.
		//String artikelString = String.format("%5s);
		String artikelListeString = "";
		for (Artikel artikel : artikelListe) {
			artikelListeString += artikel + "\n";
		}
		
		return ("Rechnung: \n" + artikelListeString + "Gesamtpreis: " + getGesamtpreis() + " €\nKundendaten: " + getUser() + "\nGekauft am " + datum + "\nSitzungsnummer: " + sitzungsNr + "\n");
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
	
	public int getArtikelAnzahl() {
		int anzahl = 0;
		for(Artikel a : artikelListe) {
			anzahl++;
		}
		return anzahl;
	}
	
	public double defineGesamtpreis() {
		double gesamtpreis = 0.00;
		for(Artikel artikel : artikelListe) {
			double produkt = artikel.getPreis() * artikel.getAnzahl();
			gesamtpreis += produkt;
		}
		return gesamtpreis;
	}
	
	public String defineDatum() {
		
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String datum = formatter.format(objDate);
		return datum;
		
	}
	
	public int getNummer() {
		return user.getNummer();
	}

	@Override
	public void setNummer(int nummer) {
		this.user.setNummer(nummer);
	}
	
	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public void setName(String name) {
		this.user.setName(name);
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setArtikelListe(List<Artikel> liste) {
		this.artikelListe = liste;
	}
	
	public List<Artikel> getArtikelListe() {
		return artikelListe;
	}
	
	public void setSitzungsNr(String sitzungsNr) {
		this.sitzungsNr = sitzungsNr;
	}
	
	public String getSitzungsNr() {
		return sitzungsNr;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	public String getDatum() {
		return this.datum;
	}
	
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	@Override
	public boolean copy(Object anderesObject) {
		if(anderesObject instanceof Rechnung) {
			this.setUser(((Rechnung) anderesObject).getUser());
			this.setArtikelListe(((Rechnung) anderesObject).getArtikelListe());
			this.setSitzungsNr(((Rechnung) anderesObject).getSitzungsNr());
			this.setDatum(((Rechnung) anderesObject).getDatum());
			this.setGesamtpreis(((Rechnung) anderesObject).getGesamtpreis());
						
			return true;
		}
		else return false;
	}
	

}
