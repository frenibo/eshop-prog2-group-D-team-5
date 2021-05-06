package eshop.local.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Kunde {

	private String sitzungsNr;
	
	private int kundenNr;

	private String name;
	private String strasse = "";
	private String plz = "";
	private String wohnort = "";
	
	public Kunde() {
		this.sitzungsNr = neueSitzungsNr();
		
	}

    public Kunde(int nr, String name) {
		kundenNr = nr;
		this.name = name;
		this.sitzungsNr = neueSitzungsNr();
	}
    
    
	public String neueSitzungsNr() {
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String sessionID = formatter.format(objDate);
		return sessionID;
	}
    
	// Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
	// z.B. getStrasse() und setStrasse()
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getWohnort() {
		return wohnort;
	}

	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}

	public int getKundenNr() {
		return kundenNr;
	}
	
	public void setSitzungsNr(String sitzungsNr) {
		this.sitzungsNr = sitzungsNr;
	}
	
	public String getSitzungsNr() {
		return sitzungsNr;
	}

	// Weitere Dienste der Kunden-Objekte
}
