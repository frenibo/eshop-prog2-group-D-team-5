package eshop.local.valueobjects;

public class User implements Valueobject{

	private int userNr;
	private String name;
	private String adresse;
	private String passwort = "";
	
	boolean istKunde = false;
	boolean istMitarbeiter = false;
	
	//benötigt vllt noch eine String-Liste als Attribut, welches jede SitzungsNr seiner bisherigen Einkäufe beinhaltet.
	
	public User() {
		this.userNr = 0;
		this.name = "";
		this.adresse = "";
		this.passwort = "";
	}
	
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
		return ("Usernummer: " + userNr + " / Name: " + name + " / Adresse: " + adresse + " / Passwort: " + passwort);
	}
    
	public boolean equals(Object andererUser) {
		if (andererUser instanceof User) 
			if (this.userNr == ((User) andererUser).getUserNr() && this.name.equals(((User) andererUser).getName())) {
				return true;
			} else return false;
			
		else
			return false;
	}
	
	public boolean copy(Object andererUser) {
		if(andererUser instanceof User) {
			this.setUserNr(((User) andererUser).getUserNr());
			this.setName(((User) andererUser).getName());
			this.setAdresse(((User) andererUser).getAdresse());
			this.setPasswort(((User) andererUser).getPasswort());
			return true;
		}
		else return false;
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
