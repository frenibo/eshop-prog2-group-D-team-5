package eshop.local.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

import eshop.local.domain.ArtikelVektorListe;
import eshop.local.domain.LagerungseventVektorListe;

public interface SitzungInterface {
	
	public Artikel getAktuellerArtikel();
	
	public String getAktuelleSitzungsNr();
	
	public User getAktuellerUser();
	
	public Inputevent getAktuellesInputevent();
	
	public Lagerungsevent getAktuellesLagerungsevent();
	
	public Rechnung getAktuelleRechnung();
	
	public ArtikelVektorListe getAktuellerWarenkorb();
	
	public LagerungseventVektorListe getAktuellerLagerungsevents();
	
	public void setAktuellerArtikel(Artikel artikel);
	
	public void setAktuelleSitzungsNr (String nummer);
	
	public void setAktuellerUser(User user);
	
	public void setAktuellesInputevent(Inputevent event);
	
	public void setAktuellesLagerungsevent(Lagerungsevent event);
	
	public void setAktuelleRechnung(Rechnung rechnung);
	
	public void setAktuellerWarenkorb(ArtikelVektorListe warenkorb);
	
	public void setAktuelleLagerungsveents(LagerungseventVektorListe event);

}
