package eshop.local.valueobjects;

public class Artikel {
	
	// Attribute zur Beschreibung eines Artikels:
		private String name;
		private int nummer;
		private int anzahl;
		private double preis;
		private String beschreibung;
		
		
	
		
		public Artikel(String titel, int nr) {
			this(titel, nr, 0, 0.00, "");
		}
		
		public Artikel(String titel, int nr, int anzahl) {
			this(titel, nr, anzahl, 0.00, "");
		}
		
		public Artikel(String titel, int nr, int anzahl, double preis) {
			this(titel, nr, anzahl, preis, "");
		}

		public Artikel(String titel, int nr, int anzahl, double preis, String beschreibung) {
			nummer = nr;
			this.name = titel;
			this.anzahl = anzahl;
			this.preis = preis;
			this.beschreibung = beschreibung;
		}
		
		/**
		 * Standard-Methode von Object überschrieben.
		 * Methode wird immer automatisch aufgerufen, wenn ein Buch-Objekt als String
		 * benutzt wird (z.B. in println(buch);)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return ("Nr: " + nummer + " / Titel: " + name + " / Preis: " + preis + " / Verfuegbarkeit: " + anzahl + " / Beschreibung: " + beschreibung);
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
		
		public String getBeschreibung() {
			return beschreibung;
		}

		
		public void setAnzahl(int anzahl) {
			this.anzahl = anzahl;
			
		}

}
