package eshop.local.valueobjects;

public class User {

	private String sitzungsNr;
	
	private int userNr;

	private String name;
	private String strasse = "";
	private String plz = "";
	private String wohnort = "";
	
	public User(String sitzungsNr) {
		this.sitzungsNr = sitzungsNr;
		
	}

    public User(String sitzungsNr, int nr, String name) {
    	this.sitzungsNr = sitzungsNr;
		userNr = nr;
		this.name = name;
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

	public int getUserNr() {
		return userNr;
	}
	
	public void setSitzungsNr(String sitzungsNr) {
		this.sitzungsNr = sitzungsNr;
	}
	
	public String getSitzungsNr() {
		return sitzungsNr;
	}

	// Weitere Dienste der Kunden-Objekte
}
