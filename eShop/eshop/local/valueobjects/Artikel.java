package eshop.local.valueobjects;

public class Artikel {
	
	// Attribute zur Beschreibung eines Artikels:
		private String name;
		private int nummer;
		private int anzahl;
		private double preis;
		private int packet = 1; //Packetgröße
		
		
		public Artikel() {
			this.name = "";
			this.nummer = 0;
			this.anzahl = 0;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer) {
			this.name = name;
			this.nummer = nummer;
			this.packet = 1;
			this.anzahl = 0;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer, int packet) {
			this.name = name;
			this.nummer = nummer;
			this.packet = packet;
			this.anzahl = 0;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer, int packet, int anzahl) {
			this.name = name;
			this.nummer = nummer;
			this.packet = packet;
			this.anzahl = anzahl;
			this.preis = 0.00;
		}
		
		public Artikel(String name, int nummer, int packet, int anzahl, double preis) {
			this.name = name;
			this.nummer = nummer;
			this.packet = packet;
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
			return ("Nr: " + nummer + " / Name: " + name + " / Preis: " + preis + " / Anzahl: " + anzahl + " / Packetgröße: " + packet);
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
		
		public int getPacket() {
			return packet;
		}
		
		public void setPacket(int packet) {
			this.packet = packet;
			
		}

}
