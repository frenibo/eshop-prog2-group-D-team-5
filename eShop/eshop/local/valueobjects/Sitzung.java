package eshop.local.valueobjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.LagerungseventVektorListe;

public class Sitzung implements SitzungInterface {
	
	private Artikel aktuellerArtikel;
	private String aktuelleSitzungsNr;
	private User aktuellerUser = new User();
	private Inputevent aktuellesInputevent = null;
	private Lagerungsevent aktuellesLagerungsevent;
	private Rechnung aktuelleRechnung;
	private ArtikelVektorListe aktuellerWarenkorb;
	private LagerungseventVektorListe aktuelleLagerungsevents;
	
	public Sitzung(String sitzungsNr) throws IOException {
		this.aktuelleSitzungsNr = sitzungsNr;
		this.aktuellerWarenkorb = new ArtikelVektorListe(sitzungsNr + "_Warenkorb.txt");
		this.aktuelleLagerungsevents = new LagerungseventVektorListe(sitzungsNr + "_LAG_TEMP.txt");
	}
	
	public Sitzung(String sitzungsNr, User user) throws IOException {
		this.aktuelleSitzungsNr = sitzungsNr;
		this.aktuellerUser = user;		
		this.aktuellerWarenkorb = new ArtikelVektorListe(sitzungsNr + "_Warenkorb.txt");
		this.aktuelleLagerungsevents = new LagerungseventVektorListe(sitzungsNr + "_LAG_TEMP.txt");
	}

	public Artikel getAktuellerArtikel() {
		return this.aktuellerArtikel;
	}
	
	public String getAktuelleSitzungsNr() {
		return this.aktuelleSitzungsNr;
	}
	
	public User getAktuellerUser() {
		return this.aktuellerUser;
	}
	
	public Inputevent getAktuellesInputevent() {
		return this.aktuellesInputevent;
	}
	
	public Lagerungsevent getAktuellesLagerungsevent() {
		return this.aktuellesLagerungsevent;
	}
	
	public Rechnung getAktuelleRechnung() {
		return this.aktuelleRechnung;
	}
	
	public ArtikelVektorListe getAktuellerWarenkorb() {
		return this.aktuellerWarenkorb;
	}
	
	public LagerungseventVektorListe getAktuellerLagerungsevents() {
		return this.aktuelleLagerungsevents;
	}
	
	public void setAktuellerArtikel(Artikel artikel) {
		this.aktuellerArtikel = artikel;
	}
	
	public void setAktuelleSitzungsNr (String nummer) {
		this.aktuelleSitzungsNr = nummer;
	}
	
	public void setAktuellerUser(User user) {
		this.aktuellerUser = user;
	}
	
	public void setAktuellesInputevent(Inputevent event) {
		this.aktuellesInputevent = event;
	}
	
	public void setAktuellesLagerungsevent(Lagerungsevent event) {
		this.aktuellesLagerungsevent = event;
	}
	
	public void setAktuelleRechnung(Rechnung rechnung) {
		this.aktuelleRechnung = rechnung;
	}
	
	public void setAktuellerWarenkorb(ArtikelVektorListe warenkorb) {
		this.aktuellerWarenkorb = warenkorb;
	}
	
	public void setAktuelleLagerungsveents(LagerungseventVektorListe event) {
		this.aktuelleLagerungsevents = event;
	}
}
