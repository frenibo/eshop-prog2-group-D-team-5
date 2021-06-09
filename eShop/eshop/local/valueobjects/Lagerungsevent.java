package eshop.local.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lagerungsevent {

	private String lagerung; //if false then einlagerung
	private Artikel artikel;
	private int anzahl;
	private String sitzungsnummer;
	private User user;
	private String datum;
	
	public Lagerungsevent(String lagerung, Artikel artikel, int anzahl, String sitzungsnummer, User user) {
		
		this.lagerung = lagerung;
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.sitzungsnummer = sitzungsnummer;
		this.user = user;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		this.datum = formatter.format(date);
	}
	
	public Lagerungsevent(String lagerung, Artikel artikel, int anzahl, String sitzungsnummer, User user, String datum) {
		
		this.lagerung = lagerung;
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.sitzungsnummer = sitzungsnummer;
		this.user = user;
		this.datum = datum;
	}
	
	public String toString() {
		//TODO: Artikel mit String.format spaltenweise ausgeben.
		//String artikelString = String.format("%5s);
		return ("[" + lagerung + "] Artikelnummer: " + this.artikel.getNummer() + " Anzahl: " + this.anzahl + " Usernummer: " + this.user.getNummer() + " Datum: " + datum);
	}
	
	public String getLagerung() {
		return this.lagerung;
	}
	
	public Artikel getArtikel() {
		return this.artikel;
	}
	
	public int getAnzahl() {
		return this.anzahl;
	}
	
	public String getSitzungsnummer() {
		return this.sitzungsnummer;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getDatum() {
		return this.datum;
	}
}
