package eshop.local.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sitzungsnummer {
	
	String sitzungsNr;
	
	public Sitzungsnummer() {
		
		Date objDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		this.sitzungsNr = formatter.format(objDate);
		
	}
	
	public String toString() {
		return (sitzungsNr);
	}
	
	public boolean equals(Object andereSitzungsNummer) {
		if (andereSitzungsNummer instanceof Sitzungsnummer) {
			if (this.sitzungsNr.equals(((Sitzungsnummer) andereSitzungsNummer).getSitzungsNummer())) {
				return true;
			} else return false;
			
		} else return false;
	}
	
	public boolean copy(Object andereSitzungsNummer) {
		if(andereSitzungsNummer instanceof Sitzungsnummer) {
			this.setSitzungsNummer(((Sitzungsnummer) andereSitzungsNummer).getSitzungsNummer());
			return true;
		}
		else return false;
	}

	public String getSitzungsNummer() {
		return sitzungsNr;		
	}
	
	public void setSitzungsNummer(String sitzungsNr) {
		this.sitzungsNr = sitzungsNr;		
	}
	
}
