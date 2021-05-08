package eshop.local.valueobjects;

public class Artikel {
	
	// Attribute zur Beschreibung eines Artikels:
		private String name;
		private int nummer;
		private int verfuegbarkeit;
		private double preis;
		private String beschreibung;
		
		
	
		
		public Artikel(String titel, int nr) {
			this(titel, nr, 0, 0.00, "");
		}
		
		public Artikel(String titel, int nr, int verfuegbarkeit) {
			this(titel, nr, verfuegbarkeit, 0.00, "");
		}
		
		public Artikel(String titel, int nr, int verfuegbarkeit, double preis) {
			this(titel, nr, verfuegbarkeit, preis, "");
		}

		public Artikel(String titel, int nr, int verfuegbarkeit, double preis, String beschreibung) {
			nummer = nr;
			this.name = titel;
			this.verfuegbarkeit = verfuegbarkeit;
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
			return ("Nr: " + nummer + " / Titel: " + name + " / Preis: " + preis + " / Verfuegbarkeit: " + verfuegbarkeit + " / Beschreibung: " + beschreibung);
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
			if(this.verfuegbarkeit > 0) {
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

		public int getVerfuegbarkeit() {
			return verfuegbarkeit;
		}
		
		public double getPreis() {
			return preis;
		}
		
		public String getBeschreibung() {
			return beschreibung;
		}

		public void setAnzahl(int anzahl) {
			this.verfuegbarkeit = anzahl;
			
		}

}
