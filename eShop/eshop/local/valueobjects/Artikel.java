package eshop.local.valueobjects;

public class Artikel implements ValueobjectInterface{
	
	// Attribute zur Beschreibung eines Artikels:
		protected String name;
		protected int nummer;
		protected int anzahl;
		protected double preis;
		
		
		public Artikel() {
			this.name = "";
			this.nummer = 0;
			this.anzahl = 0;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer) {
			this.name = name;
			this.nummer = nummer;
			this.anzahl = 0;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer, int anzahl) {
			this.name = name;
			this.nummer = nummer;
			this.anzahl = anzahl;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer, int anzahl, double preis) {
			this.name = name;
			this.nummer = nummer;
			this.anzahl = anzahl;
			this.preis = preis;
		}
		/*
		//scheint nicht zu funktionieren:
		//Man kann constructor von subclass nicht in constructor von superclass aufrufen!
		public Artikel(String name, int nummer, int anzahl, double preis, int packetgroeße) {
			if(packetgroeße != 1) {
				new Massenartikel(name, nummer, anzahl, preis, packetgroeße);
			}
			else new Artikel(name, nummer, anzahl, preis);
		}
		*/
		
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
			return ("Nr: " + nummer + " / Name: " + name + " / Preis: " + preis + " / Anzahl: " + anzahl);
		}
		
		// --- Dienste der Artikel-Objekte ---
		/**
		 * Standard-Methode von Object überschrieben.
		 * Methode dient Vergleich von zwei Buch-Objekten anhand ihrer Werte,
		 * d.h. Titel und Nummer.
		 * 
		 * @see java.lang.Object#toString()
		 */
		public boolean equals(Object andererArtikel) {
			if (andererArtikel instanceof Artikel) {
				if (this.nummer == ((Artikel) andererArtikel).getNummer() && this.name.equals(((Artikel) andererArtikel).getName())) {
					return true;
				} else return false;
				
			} else
				return false;
		}
		
		public boolean copy(Object andererArtikel) {
			if(andererArtikel instanceof Artikel) {
				this.setAnzahl(((Artikel) andererArtikel).getAnzahl());
				this.setName(((Artikel) andererArtikel).getName());
				this.setNummer(((Artikel) andererArtikel).getNummer());
				this.setPreis(((Artikel) andererArtikel).getPreis());
				return true;
			}
			else return false;
		}
		
		public boolean copy(int anzahl, String name, int nummer, double preis) {
			this.setAnzahl(anzahl);
			this.setName(name);
			this.setNummer(nummer);
			this.setPreis(preis);
			return true;
		}

		
		/*
		 * Ab hier Accessor-Methoden
		 */
		
		public boolean isVerfuegbar() {
			if(this.anzahl > 0) {
				return true;
			}
			else return false;
		}
		
		public void setNummer(int nummer) {
			this.nummer = nummer;			
		}
		
		public void setName(String name) {
			this.name = name;		
		}
		
		public void setAnzahl(int anzahl) {
			this.anzahl = anzahl;
			
		}
		
		public void setPreis(double preis) {
			this.preis = preis;			
		}
		
		
		public int getNummer() {
			return nummer;
		}
		

		public String getName() {
			return name;
		}

		public int getAnzahl() {
			return anzahl;
		}
		
		public double getPreis() {
			return preis;
		}
		
		public int getPacketgroeße() {
			return 1;
		}
		
}
