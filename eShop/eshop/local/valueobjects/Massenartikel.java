package eshop.local.valueobjects;

public class Massenartikel extends Artikel {
	
	private int packetgroeße = 1; //Standard-Packetgröße ist immer 1.
	
	public Massenartikel() {
		super();
	}
	
	public Massenartikel(String name, int nummer) {
		super(name, nummer);
	}
	
	public Massenartikel(String name, int nummer, int anzahl) {
		super(name, nummer, anzahl);
	}
	
	public Massenartikel(String name, int nummer, int anzahl, double preis) {
		super(name, nummer, anzahl, preis);
	}
	
	public Massenartikel (String name, int nummer, int anzahl, double preis, int packetgroeße) {
		super(name, nummer, anzahl, preis);
		this.packetgroeße = packetgroeße;
	}
	
	public String toString() {
		//TODO: Artikel mit String.format spaltenweise ausgeben.
		//String artikelString = String.format("%5s);
		return ("Nr: " + nummer + " / Name: " + name + " / Preis: " + preis + " / Anzahl: " + anzahl + " / Packetgröße: " + packetgroeße);
	}
	
	public boolean copy(Object andererArtikel) {
		if(andererArtikel instanceof Massenartikel) {
			super.copy(((Massenartikel) andererArtikel).getAnzahl(), ((Massenartikel) andererArtikel).getName(), ((Massenartikel) andererArtikel).getNummer(), ((Massenartikel) andererArtikel).getPreis());
			this.setPacketgroeße(((Massenartikel) andererArtikel).getPacketgroeße());
			return true;
		}
		else return false;
	}
	
	public void setPacketgroeße(int packetgroeße) {
		this.packetgroeße = packetgroeße;
		
	}
	
	public int getPacketgroeße() {
		return packetgroeße;
	}
}
