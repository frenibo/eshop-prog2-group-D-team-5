package eshop.local.valueobjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eshop.local.ui.eShop;

/*
 * Beschreibung: 
 */

public class Inputevent {
	
	private String input;
	private String sitzungsNr;
	private Date datum;
	private String zeitstempel;
	private User user;
	
	
	public Inputevent (String input) throws IOException {
		this.input = input;
		this.user = eShop.getAktuellerUser();
		this.sitzungsNr = eShop.getSitzungsNr();
		this.datum = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		this.zeitstempel = formatter.format(datum);
				
	}
	
	public Inputevent (String input, User user, String sitzungsNr, String zeitstempel) {
		this.input = input;
		this.user = user;
		this.datum = new Date();
		this.sitzungsNr = sitzungsNr;
		this.zeitstempel = zeitstempel;
				
	}
	

	public String toString() {
		//TODO: Artikel mit String.format spaltenweise ausgeben.
		//String artikelString = String.format("%5s);
			
		return (zeitstempel + " | Input: " + input + " | Usernummer: " + user.getNummer() + " | Sitzungsnummer: " + sitzungsNr);
	}
	
	public String getInput() {
		return this.input;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getSitzungsNr() {
		return this.sitzungsNr;
	}
	
	public String getZeitstempel() {
		return this.zeitstempel;
	}
	

}
