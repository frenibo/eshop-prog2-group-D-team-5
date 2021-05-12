package eshop.local.valueobjects;

public class User {

	private int userNr;
	private String name;
	private String adresse;
	private String passwort = "";
	
	//benötigt vllt noch eine String-Liste als Attribut, welches jede SitzungsNr seiner bisherigen Einkäufe beinhaltet.
	
	public User(int userNr, String name) {
		this.userNr = userNr;
		this.name = name;
		this.adresse = "Niemandsland";
		this.passwort = "passwort";
	}

    public User(int userNr, String name, String adresse, String passwort) {
		this.userNr = userNr;
		this.name = name;
		this.adresse = adresse;
		this.passwort = passwort;
	}
    
    /**
	 * Standard-Methode von Object überschrieben.
	 * Methode wird immer automatisch aufgerufen, wenn ein Buch-Objekt als String
	 * benutzt wird (z.B. in println(buch);)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		//TODO: Artikel mit String.format spaltenweise ausgeben.
		//String artikelString = String.format("%5s);
		return ("Nr: " + userNr + " / Name: " + name + " / Adresse: " + adresse + " / Passwort: " + passwort);
	}
    
	// Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
	// z.B. getStrasse() und setStrasse()
	
	public int getUserNr() {
		return userNr;
	}
	
	public void setUserNr(int userNr){
		this.userNr = userNr;
	}
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	public String getPasswort() {
		return passwort;
	}

	// Weitere Dienste der Kunden-Objekte
}
