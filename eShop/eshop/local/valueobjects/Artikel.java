package eshop.local.valueobjects;

public class Artikel {
	
	// Attribute zur Beschreibung eines Artikels:
		private String name;
		private int nummer;
		private int anzahl;
		private double preis;
		
		
	
		
		public Artikel(String titel, int nr) {
			nummer = nr;
			this.name = titel;
			this.anzahl = 0;
			this.preis = 0.00;
		}
		
		public Artikel(String titel, int nr, int anzahl) {
			nummer = nr;
			this.name = titel;
			this.anzahl = anzahl;
			this.preis = 0.00;
		}
		
		public Artikel(String titel, int nr, int anzahl, double preis) {
			nummer = nr;
			this.name = titel;
			this.anzahl = anzahl;
			this.preis = preis;
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
			if (andererArtikel instanceof Artikel) 
				return ((this.nummer == ((Artikel) andererArtikel).nummer) 
						&& (this.name.equals(((Artikel) andererArtikel).name)));
			else
				return false;
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
		
		public void setAnzahl(int anzahl) {
			this.anzahl = anzahl;
			
		}

}
